package org.example.client.view;

import org.example.client.Main;
import org.example.client.controller.menus.PreLoadGameMenuController;
import org.example.client.model.ClientApp;
import org.example.client.view.menu.*;
import org.example.common.models.Message;
import org.example.server.models.*;
import org.example.server.models.enums.Gender;

import java.util.HashMap;

public class AppView {

//    public void runViaTerminal() {
//        Scanner scanner = new Scanner(System.in);
//        do {
//            App.getCurrentMenu().executeCommands(scanner);
//        } while (App.getCurrentMenu() != Menu.ExitMenu);
//    }

    private void cheat(){
        User admin = new User("admin" , "admin" , "God" , "test@gmail.com" , Gender.Male);
        admin.setRecoveryQuestion(new SecurityQuestion("Are you gay?", "yes"));
        ClientApp.setLoggedInUser(admin);
        Lobby lobby = new Lobby(admin, true , "" , true , 2222 , "test");
        lobby.setMap(admin.getUsername(), 0);

        ClientApp.setCurrentMenu(new PregameMenuView(lobby));

        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("userInfo", ClientApp.getLoggedInUser().getInfo());
        }}, Message.Type.set_online_user));

        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("lobbyInfo", lobby.getInfo());
        }}, Message.Type.create_game));
    }


    public void runViaGraphics() {
//         ------REGULAR------
        if (ClientApp.loadSavedUser()) {
            ClientApp.setCurrentMenu(new MainMenuView());
            Main.getMain().setScreen(ClientApp.getCurrentMenu());
        }
        else {
            ClientApp.setCurrentMenu(new WelcomeMenuView());
            Main.getMain().setScreen(ClientApp.getCurrentMenu());
        }


        // ------CHEAT------

//        Main.getMain().setScreen(new WelcomeMenuView());
//        cheat();

    }

}
