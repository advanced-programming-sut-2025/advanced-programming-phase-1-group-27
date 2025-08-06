package org.example.common.models;

import com.badlogic.gdx.math.Interpolation;
import org.example.common.utils.JSONUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

abstract public class ConnectionThread extends Thread {
    private static final byte PACKET_TYPE_JSON = 1;
    private static final byte PACKET_TYPE_BINARY = 2;

    protected final DataInputStream dataInputStream;
    protected final DataOutputStream dataOutputStream;
    protected final BlockingQueue<Message> receivedMessagesQueue;
    protected String otherSideIP;
    protected int otherSidePort;
    protected Socket socket;
    protected AtomicBoolean end;
    protected boolean initialized = false;

    protected ConnectionThread(Socket socket) throws IOException {
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.receivedMessagesQueue = new LinkedBlockingQueue<>();
        this.end = new AtomicBoolean(false);
    }

    public synchronized Message sendAndWaitForResponse(Message message, int timeoutMilli) {
        sendMessage(message);
        try {
            if (initialized) return receivedMessagesQueue.poll(timeoutMilli, TimeUnit.MILLISECONDS);
            socket.setSoTimeout(timeoutMilli);
            Message result = readJsonPacket();
            socket.setSoTimeout(0);
            return result;
        } catch (Exception e) {
            System.err.println("Request Timed out.");
            return null;
        }
    }

    abstract public boolean initialHandshake();

    abstract protected boolean handleMessage(Message message);

    abstract protected void handleBinaryPacket(byte[] packet);

    private synchronized void sendPacket(byte packetType, byte[] packet) throws IOException {
        dataOutputStream.writeByte(packetType);
        dataOutputStream.writeInt(packet.length);
        dataOutputStream.write(packet);
        dataOutputStream.flush();
    }

    public synchronized void sendMessage(Message message) {
        String JSONString = JSONUtils.toJson(message);
        try {
            sendPacket(PACKET_TYPE_JSON, JSONString.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendMessageAndPrint(Message message) {
        String JSONString = JSONUtils.toJson(message);
        System.out.println("Json: " + JSONString);
        try {
            sendPacket(PACKET_TYPE_JSON, JSONString.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendBinaryPacket(byte[] packet) {
        try {
            sendPacket(PACKET_TYPE_BINARY, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        initialized = false;
        if (!initialHandshake()) {
            System.err.println("Inital HandShake failed with remote device.");
            end();
            return;
        }

        initialized = true;
        while (!end.get()) {
            try {
                byte packetType = dataInputStream.readByte();
                int packetSize = dataInputStream.readInt();
                byte[] packet = new byte[packetSize];
                dataInputStream.readFully(packet);

                if (packetType == PACKET_TYPE_JSON) {
                    String receivedStr = new String(packet, StandardCharsets.UTF_8);
                    Message message = JSONUtils.fromJson(receivedStr);
                    boolean handled = handleMessage(message);
                    if (!handled) try {
                        receivedMessagesQueue.put(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else if (packetType == PACKET_TYPE_BINARY) {
                    handleBinaryPacket(packet);
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        end();
    }

    public String getOtherSideIP() {
        return otherSideIP;
    }

    public void setOtherSideIP(String otherSideIP) {
        this.otherSideIP = otherSideIP;
    }

    public int getOtherSidePort() {
        return otherSidePort;
    }

    public void setOtherSidePort(int otherSidePort) {
        this.otherSidePort = otherSidePort;
    }

    public void end() {
        end.set(true);
        try {
            socket.close();
        } catch (IOException e) {
        }
    }

    protected synchronized Message readJsonPacket() throws IOException {
        byte packetType = dataInputStream.readByte();
        int packetSize = dataInputStream.readInt();

        if (packetType != PACKET_TYPE_JSON)
            throw new IOException("Expected JSON packet");

        byte[] packet = new byte[packetSize];
        dataInputStream.readFully(packet);
        String json = new String(packet, StandardCharsets.UTF_8);
        return JSONUtils.fromJson(json);
    }
}
