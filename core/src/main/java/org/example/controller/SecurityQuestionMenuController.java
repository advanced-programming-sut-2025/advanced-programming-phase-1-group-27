package org.example.controller;

import org.example.Main;
import org.example.models.App;
import org.example.models.Result;
import org.example.models.SecurityQuestion;
import org.example.models.enums.Menu;
import org.example.models.enums.Questions;
import org.example.view.menu.LoginMenuView;
import org.example.view.menu.SecurityQuestionMenuView;
import org.example.view.menu.RegisterMenuView;

public class SecurityQuestionMenuController extends MenuController {

    private SecurityQuestionMenuView view;

    public SecurityQuestionMenuController(SecurityQuestionMenuView view) {
        this.view = view;
    }

    public void submitSecurityQuestion() {

        if (!view.getAnswerTextField().getText().isEmpty()){
            view.getUser().setRecoveryQuestion(new SecurityQuestion(Questions.values()[view.getSecurityQuestionsBox().getSelectedIndex()].getQuestion(),
                    view.getAnswerTextField().getText()));

            App.addUser(view.getUser());

            App.setCurrentMenu(Menu.LoginMenu);
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new LoginMenuView());
        }
        else{
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
        App.setCurrentMenu(Menu.RegisterMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new RegisterMenuView());
        return new Result(true, "Redirecting to register menu ...");

    }

}
