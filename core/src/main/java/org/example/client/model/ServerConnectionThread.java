package org.example.client.model;

import org.example.client.controller.ServerConnectionController;
import org.example.client.controller.ShopController;
import org.example.client.view.menu.PregameMenuView;
import org.example.common.models.ConnectionThread;
import org.example.common.models.Message;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class ServerConnectionThread extends ConnectionThread {

    protected ServerConnectionThread(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    public boolean initialHandshake() {
        try {
            socket.setSoTimeout(TIMEOUT_MILLIS);

            dataInputStream.readUTF();
            sendMessage(ServerConnectionController.getAddress());

            socket.setSoTimeout(0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected boolean handleMessage(Message message) {
        if (message.getType() == Message.Type.start_game) {
            if (ClientApp.getCurrentMenu() instanceof PregameMenuView pregameMenuView) {
                pregameMenuView.getController().startGame(message);
                return true;
            }
            return false;
        }
        else if (message.getType() == Message.Type.update_shop) {
            if (ClientApp.getCurrentGame() != null) {
                ShopController.updateShopStock(message);
                return true;
            }
            return false;
        }
        else if (message.getType() == Message.Type.pass_an_hour) {
            if (ClientApp.getCurrentGame() != null) {
                ClientApp.getCurrentGame().getTime().passAnHour();
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public void run() {
        super.run();
        ClientApp.end();
    }
}
