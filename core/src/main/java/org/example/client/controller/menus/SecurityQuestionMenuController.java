package org.example.client.controller.menus;

import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.menu.LobbyMenuView;
import org.example.client.view.menu.LoginMenuView;
import org.example.client.view.menu.RegisterMenuView;
import org.example.client.view.menu.SecurityQuestionMenuView;
import org.example.common.models.Message;
import org.example.server.models.Result;
import org.example.server.models.SecurityQuestion;
import org.example.server.models.enums.Questions;

import java.util.HashMap;

public class SecurityQuestionMenuController extends MenuController {

    private SecurityQuestionMenuView view;

    public SecurityQuestionMenuController(SecurityQuestionMenuView view) {
        this.view = view;
    }

    public void submitSecurityQuestion() {
        if (!view.getAnswerTextField().getText().isEmpty()) {
            view.getUser().setRecoveryQuestion(new SecurityQuestion(Questions.values()[view.getSecurityQuestionsBox().getSelectedIndex()].getQuestion(),
                    view.getAnswerTextField().getText()));
            ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
                put("userInfo", view.getUser().getInfo());
            }}, Message.Type.add_user));
            Main.getMain().getScreen().dispose();
            ClientApp.setCurrentMenu(new LobbyMenuView());
            Main.getMain().setScreen(ClientApp.getCurrentMenu());
        }
        else {
            view.setErrorPhase(true);
        }
    }

    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {
        playClickSound();
        Main.getMain().getScreen().dispose();
        ClientApp.setCurrentMenu(new RegisterMenuView());
        Main.getMain().setScreen(ClientApp.getCurrentMenu());
        return new Result(true, "Redirecting to registerViaGraphics menu ...");
    }
}
