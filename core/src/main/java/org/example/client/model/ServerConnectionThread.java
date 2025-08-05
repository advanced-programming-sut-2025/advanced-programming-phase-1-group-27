package org.example.client.model;

import org.example.client.controller.ServerConnectionController;
import org.example.client.controller.ServerUpdatesController;
import org.example.client.view.menu.PregameMenuView;
import org.example.common.models.ConnectionThread;
import org.example.common.models.Message;

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
        return false;
    }

    @Override
    public void run() {
        super.run();
        System.out.println("TAMAM SHOD");
        ClientApp.end();
    }
}
