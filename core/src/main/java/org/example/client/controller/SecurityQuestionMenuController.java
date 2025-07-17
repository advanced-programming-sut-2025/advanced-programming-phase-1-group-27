package org.example.client.controller;

import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.menu.LoginMenuView;
import org.example.client.view.menu.RegisterMenuView;
import org.example.client.view.menu.SecurityQuestionMenuView;
import org.example.common.models.Message;
import org.example.server.models.App;
import org.example.server.models.Result;
import org.example.server.models.SecurityQuestion;
import org.example.server.models.enums.Menu;
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
                put("username", view.getUser().getUsername());
                put("password", view.getUser().getPassword());
                put("email", view.getUser().getEmail());
                put("nickname", view.getUser().getNickname());
                put("gender", view.getUser().getGender().name());
                put("questionId", view.getSecurityQuestionsBox().getSelectedIndex());
                put("answer", view.getAnswerTextField().getText());
            }}, Message.Type.add_user));
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new LoginMenuView());
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
        Main.getMain().setScreen(new RegisterMenuView());
        return new Result(true, "Redirecting to registerViaGraphics menu ...");

    }
}
