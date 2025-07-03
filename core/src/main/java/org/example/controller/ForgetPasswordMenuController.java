package org.example.controller;

import org.example.models.Result;
import org.example.view.menu.ForgetPasswordMenuView;

public class ForgetPasswordMenuController extends MenuController{


    private ForgetPasswordMenuView view;

    public ForgetPasswordMenuController(ForgetPasswordMenuView view){
        this.view = view;
    }

    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {
        return null;
    }


}
