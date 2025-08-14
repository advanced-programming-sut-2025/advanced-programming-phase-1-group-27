package org.example.server.controller;

import org.example.client.Main;
import org.example.client.controller.menus.MenuController;
import org.example.client.view.menu.RegisterMenuView;
import org.example.client.view.menu.SecurityQuestionMenuView;
import org.example.common.models.Result;

public class SecurityQuestionMenuController extends MenuController {

    private SecurityQuestionMenuView view;

    public SecurityQuestionMenuController(SecurityQuestionMenuView view) {
        this.view = view;
    }

    public void submitSecurityQuestion() {

//        if (!view.getAnswerTextField().getText().isEmpty()) {
//            view.getUser().setRecoveryQuestion(new SecurityQuestion(Questions.values()[view.getSecurityQuestionsBox().getSelectedIndex()].getQuestion(),
//                    view.getAnswerTextField().getText()));
//
//            App.addUser(view.getUser());
//
//            App.setCurrentMenu(Menu.LoginMenu);
//            Main.getMain().getScreen().dispose();
//            Main.getMain().setScreen(new LoginMenuView());
//        } else {
//            view.setErrorPhase(true);
//        }
//

    }


    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {

        playClickSound();
        //App.setCurrentMenu(Menu.RegisterMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new RegisterMenuView());
        return new Result(true, "Redirecting to registerViaGraphics menu ...");

    }

}
