package org.example.client.view;

import org.example.client.controller.GameMenuController;
import org.example.client.controller.InteractionsWithOthers.InteractionsWithNPCController;
import org.example.client.controller.InteractionsWithOthers.InteractionsWithUserController;
import org.example.client.controller.InteractionsWithOthers.MarriageController;
import org.example.common.models.Result;
import org.example.common.models.Menu;
import org.example.common.models.commands.*;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameView extends AppMenu {
    private final GameMenuController controller;
    private final InteractionsWithNPCController interactionsWithNPCController;
    private final InteractionsWithUserController interactionsWithUserController;
    private final MarriageController marriageController;

    public GameView() {
        controller = new GameMenuController(this);
        interactionsWithNPCController = new InteractionsWithNPCController();
        interactionsWithUserController = new InteractionsWithUserController();
        marriageController = new MarriageController();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

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

    public GameMenuController getController() {
        return controller;
    }

    public void executeCommands(Scanner scanner) {

    }

    public Result askToVote(Scanner scanner) {
        String input = scanner.nextLine().trim();
        if (!input.equals("accept") && !input.equals("reject")) {
            System.out.println("Invalid response!");
            return new Result(false, "invalid command!");
        }
        System.out.println("You have voted successfully!");
        return new Result(true, input);
    }
}
