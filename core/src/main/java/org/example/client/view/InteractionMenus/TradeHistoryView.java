package org.example.client.view.InteractionMenus;

import org.example.client.controller.InteractionsWithOthers.TradeController;
import org.example.client.view.AppMenu;
import org.example.server.models.Relations.Trade;
import org.example.server.models.Stacks;

import java.util.ArrayList;
import java.util.Scanner;

public class TradeHistoryView extends AppMenu {
    private final TradeController controller;

    private final String username;
//    private final ArrayList<Trade> trades;

    public TradeHistoryView(String username) {
        controller = new TradeController();
        this.username = username;


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void executeCommands(Scanner scanner) {

    }
}
