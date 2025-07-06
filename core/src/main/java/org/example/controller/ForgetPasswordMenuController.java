package org.example.controller;

import org.example.Main;
import org.example.models.App;
import org.example.models.Result;
import org.example.models.User;
import org.example.models.enums.Menu;
import org.example.view.menu.ForgetPasswordMenuView;
import org.example.view.menu.LoginMenuView;
import org.example.view.menu.WelcomeMenuView;

public class ForgetPasswordMenuController extends MenuController{


    private ForgetPasswordMenuView view;
    private User attemptingUser;

    public ForgetPasswordMenuController(ForgetPasswordMenuView view){
        this.view = view;
    }

    public void submitUsername(){

        attemptingUser = App.getUserByUsername(view.getUsernameField().getText());
        if ( attemptingUser == null ){
            view.setErrorLabel("Username not found!");
            view.setErrorTimer(5f);
            return;
        }

        view.setErrorLabel("");
        view.setErrorTimer(5f);
        view.setUsernameSubmitted(true);

    }

    public void attemptToChangePassword(){

        String answer = view.getAnswerField().getText();
        String newPassword = view.getNewPasswordField().getText();

        if ( answer.isEmpty() || newPassword.isEmpty() ){
            view.setErrorLabel("Please fill all the required fields");
            view.setErrorTimer(5f);
            return;
        }

        if ( ! answer.equals(attemptingUser.getRecoveryQuestion().getAnswer()) ){
            view.setErrorLabel("Wrong answer Try again");
            view.getAnswerField().setText("");
            view.getNewPasswordField().setText("");
            view.setErrorTimer(5f);
            return;
        }

        Result passwordCheck = User.checkPassword(newPassword);
        if (!passwordCheck.success()){
            view.setErrorLabel(passwordCheck.message());
            view.setErrorTimer(5f);
            return;
        }

        attemptingUser.setPassword(User.hashPassword(view.getNewPasswordField().getText()));
        exitMenu();


    }

    public void setRandomPassword(){

        view.getNewPasswordField().setText(App.generatePassword());

    }


    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {
        App.setCurrentMenu(Menu.LoginMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenuView());
        return new Result(true, "Redirecting to login menu ...");
    }

    public User getAttemptingUser() {
        return attemptingUser;
    }

}
