package org.example.client.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.example.client.controller.ServerConnectionController;
import org.example.client.controller.ServerUpdatesController;
import org.example.client.view.menu.PregameMenuView;
import org.example.common.models.ConnectionThread;
import org.example.common.models.Message;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.function.Consumer;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class ServerConnectionThread extends ConnectionThread {
    private OutputStream fileOutputStream = null;
    private FileHandle fileHandle = null;
    private Consumer<FileHandle> onDownloadComplete = null; // music file handle

    protected ServerConnectionThread(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    public boolean initialHandshake() {
        try {
            socket.setSoTimeout(TIMEOUT_MILLIS);

            readJsonPacket();
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
        if (message.getType() == Message.Type.terminating_lobby) {
            if (ClientApp.getCurrentMenu() instanceof PregameMenuView pregameMenuView)
                pregameMenuView.getController().goToLobbyMenu();
            return true;
        }
        else if (message.getType() == Message.Type.start_game) {
            if (ClientApp.getCurrentMenu() instanceof PregameMenuView pregameMenuView) {
                pregameMenuView.getController().startGame(message);
                return true;
            }
            return false;
        }
        else if (message.getType() == Message.Type.update_shop) {
            assert ClientApp.getCurrentGame() != null;
            ServerUpdatesController.updateShopStock(message);
            return true;
        }
        else if (message.getType() == Message.Type.pass_an_hour) {
            assert ClientApp.getCurrentGame() != null;
            ServerUpdatesController.passAnHour();
            return true;
        }
        else if (message.getType() == Message.Type.advance_time) {
            assert ClientApp.getCurrentGame() != null;
            ServerUpdatesController.cheatAdvanceTime(message);
            return true;
        }
        else if (message.getType() == Message.Type.set_weather) {
            assert ClientApp.getCurrentGame() != null;
            ServerUpdatesController.setWeather(message);
            return true;
        }
        else if (message.getType() == Message.Type.crows_attack) {
            assert ClientApp.getCurrentGame() != null;
            ServerUpdatesController.crowsAttack(message);
            return true;
        }
        else if (message.getType() == Message.Type.foraging_updates) {
            assert ClientApp.getCurrentGame() != null;
            ServerUpdatesController.updateForaging(message);
            return true;
        }
        else if (message.getType() == Message.Type.enter_npc) {
            ServerUpdatesController.otherPlayerEnteredNpcMap(message);
            return true;
        }
        else if (message.getType() == Message.Type.leave_npc) {
            ServerUpdatesController.otherPlayerLeftNpcMap(message);
            return true;
        }
        else if (message.getType() == Message.Type.walk_update) {
            ServerUpdatesController.otherPlayerWalking(message);
            return true;
        }
        else if (message.getType() == Message.Type.get_player_inventory) {
            sendMessage(ServerUpdatesController.getInventory());
            return true;
        }
        else if (message.getType() == Message.Type.interaction_p2p) {
            ServerUpdatesController.handleP2P(message);
            return true;
        }
        else if (message.getType() == Message.Type.start_download) {
            handleStartDownload(message);
            return true;
        }
        else if (message.getType() == Message.Type.download_complete) {
            completeDownload();
            return true;
        }
        else if (message.getType() == Message.Type.get_music_offset) {
            sendMessage(ServerUpdatesController.getMusicOffset());
            return true;
        }
        else if (message.getType() == Message.Type.save_and_exit_game) {
            ClientApp.saveGame();
            return true;
        }
        else if (message.getType() == Message.Type.voting) {
            ServerUpdatesController.handleVote(message);
            return true;
        }
        else if (message.getType() == Message.Type.chat) {
            ServerUpdatesController.handleChat(message);
            return true;
        }
        else if (message.getType() == Message.Type.get_player_position) {
            sendMessage(ClientApp.getCurrentGame().getPlayerPosition());
            return true;
        }
        else if (message.getType() == Message.Type.marriage_request) {
            ServerUpdatesController.handleMarriageRequest(message);
            return true;
        }
        else if (message.getType() == Message.Type.marriage_response) {
            ServerUpdatesController.handleMarriageResponse(message);
            return true;
        }
        else if (message.getType() == Message.Type.reaction) {
            ServerUpdatesController.handleReaction(message);
            return true;
        }
        return false;
    }

    @Override
    protected void handleBinaryPacket(byte[] packet) {
        if (fileOutputStream == null) {
            System.err.println("no active file output stream. packet ignored ...");
            return;
        }
        try {
            fileOutputStream.write(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        System.out.println("TAMAM SHOD");
        ClientApp.end();
    }

    private void handleStartDownload(Message message) {
        String songId = message.getFromBody("songId");
        try {
            fileHandle = Gdx.files.local("musics/" + songId + ".ogg");
            fileHandle.parent().mkdirs();
            fileOutputStream = new FileOutputStream(fileHandle.file(), false);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("failed to start download!");
        }
    }

    private void completeDownload() {
        if (fileOutputStream == null)
            return;
        try {
            fileOutputStream.close();
            if (onDownloadComplete != null)
                Gdx.app.postRunnable(() -> {
                    onDownloadComplete.accept(fileHandle);
                });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fileOutputStream = null;
        }
    }

    public void setOnDownloadComplete(Consumer<FileHandle> onDownloadComplete) {
        this.onDownloadComplete = onDownloadComplete;
    }
}
