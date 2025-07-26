package org.example.client.view;

import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.menu.*;
import org.example.common.models.Message;
import org.example.server.controller.PregameMenuController;
import org.example.server.models.*;
import org.example.server.models.enums.Gender;
import org.example.server.models.enums.Menu;

import java.util.HashMap;
import java.util.Scanner;

public class AppView {

    public void runViaTerminal() {
        Scanner scanner = new Scanner(System.in);
        do {
            App.getCurrentMenu().executeCommands(scanner);
        } while (App.getCurrentMenu() != Menu.ExitMenu);
    }

    private void cheat(){
        User admin = new User("admin" , "admin" , "God" , "test@gmail.com" , Gender.Male);
        admin.setRecoveryQuestion(new SecurityQuestion("Are you gay?", "yes"));
        ClientApp.setLoggedInUser(admin);
        Lobby lobby = new Lobby(admin, true , "" , true , 2222 , "test");
        lobby.getUsernameToMap().put(admin.getUsername() , 0);

        ClientApp.setCurrentMenu(new PregameMenuView(lobby));

        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("lobbyInfo", lobby.getInfo());
        }}, Message.Type.create_game));
    }


    public void runViaGraphics() {
//        if (ClientApp.loadSavedUser()) {
//            Main.getMain().setScreen(new MainMenuView());
//            Main.getMain().getScreen().dispose();
//            Main.getMain().setScreen(new HomeView());
//            return;
//        }

        Main.getMain().setScreen(new WelcomeMenuView());
        // For Graphics team
        cheat();

        // For GigaChads
//        Main.getMain().getScreen().dispose();
//        Main.getMain().setScreen(new HomeView());

//        Main.getMain().getScreen().dispose();
//        Main.getMain().setScreen(new LobbyMenuView());
    }

}
