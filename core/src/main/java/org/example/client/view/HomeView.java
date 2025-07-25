package org.example.client.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.server.controller.HomeController;
import org.example.server.controller.HomePlayerController;
import org.example.server.models.App;
import org.example.server.models.GameAssetManager;
import org.example.server.models.Player;
import org.example.server.models.Result;
import org.example.server.models.enums.Menu;
import org.example.server.models.enums.commands.CheatCommands;
import org.example.server.models.enums.commands.GameMenuCommands;
import org.example.server.models.enums.commands.HomeCommands;
import org.example.server.models.enums.commands.MainMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class HomeView extends AppMenu {

    private final HUDView hudView;

    private final HomeController controller;
    private final HomePlayerController playerController;

    private final Stage stage;

    private final TextButton advanceTimeButton;


    private final Sprite homeSprite = new Sprite(GameAssetManager.getGameAssetManager().getHomeTexture());

    public HomeView() {

        stage = new Stage(new ScreenViewport());
        hudView = new HUDView(stage);
        controller = new HomeController(this);
        playerController = new HomePlayerController(this);
        advanceTimeButton = new TextButton("advance",skin);

    }

    private void preProcess() {
        Player player = App.getCurrentGame().getCurrentPlayer();
        player.setCurrentMap(player.getFarmMap());
        player.setCurrentCell(player.getFarmMap().getCell(8, 71));
    }

    @Override
    public void show() {
        //TODO Movaghati ast in
        preProcess();

        System.out.println("SALAM");

        Gdx.input.setInputProcessor(stage);

        stage.addActor(advanceTimeButton);

        setListeners();

        homeSprite.setCenter(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
        homeSprite.setScale(0.2f);

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);


        Main.getBatch().begin();
        homeSprite.draw(Main.getBatch());
        playerController.update();
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        Main.getBatch().begin();
        hudView.sobhanAllah(delta);
        Main.getBatch().end();




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

    public HUDView getHudView() {
        return hudView;
    }

    private void setListeners(){

        advanceTimeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                App.getCurrentGame().getTime().cheatAdvanceTime(1);
            }
        });

    }


    public void executeCommands(Scanner scanner) {
        if (controller.playerPassedOut()) {
            System.out.println(App.getCurrentGame().getCurrentPlayer().getUsername() + " has passed out!");
            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().nextTurn(scanner));
            return;
        }
        String input = scanner.nextLine();
        Matcher matcher;
        if (MainMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
        } else if ((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(
                    matcher.group("menuName").trim()
            ));
        } else if (MainMenuCommands.ExitMenu.getMatcher(input) != null) {
            System.out.println(controller.exitMenu());
        } else if (GameMenuCommands.TerminateGame.getMatcher(input) != null) {
            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().terminateGame(scanner));
        } else if (GameMenuCommands.NextTurn.getMatcher(input) != null) {
            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().nextTurn(scanner));
        } else if (HomeCommands.ShowCraftingRecipes.getMatcher(input) != null) {
            System.out.print(controller.showCraftingRecipes());
        } else if ((matcher = HomeCommands.CraftItem.getMatcher(input)) != null) {
            System.out.println(controller.craft(
                    matcher.group("itemName").trim()
            ));
        } else if ((matcher = HomeCommands.PutPickRefrigerator.getMatcher(input)) != null) {
            System.out.println(controller.putOrPickFromRefrigerator(
                    matcher.group("itemName").trim(),
                    matcher.group("function").trim()
            ));
        } else if (HomeCommands.ShowCookingRecipes.getMatcher(input) != null) {
            System.out.println(controller.showCookingRecipes());
        } else if ((matcher = HomeCommands.CookItem.getMatcher(input)) != null) {
            System.out.println(controller.cook(
                    matcher.group("itemName").trim()
            ));
        } else if ((matcher = GameMenuCommands.EatFood.getMatcher(input)) != null) {
            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().eatFood(
                    matcher.group("itemName").trim()
            ));
        } else if (GameMenuCommands.InventoryShow.getMatcher(input) != null) {
            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().inventoryShow());
        } else if ((matcher = GameMenuCommands.InventoryTrash.getMatcher(input)) != null) {
            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().inventoryTrash(
                    matcher.group("itemName").trim(),
                    Integer.parseInt(matcher.group("number").trim())
            ));
        } else if ((matcher = CheatCommands.CheatAddItem.getMatcher(input)) != null) {
            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().cheatAddItem(
                    matcher.group("itemName").trim(),
                    Integer.parseInt(matcher.group("count"))
            ));
        } else if (GameMenuCommands.ShowEnergy.getMatcher(input) != null) {
            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().showEnergy());
        } else if ((matcher = GameMenuCommands.EatFood.getMatcher(input)) != null) {
            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().eatFood(
                    matcher.group("itemName").trim()
            ));
        } else if ((matcher = CheatCommands.CheatSetEnergy.getMatcher(input)) != null) {
            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().cheatSetEnergy(
                    matcher.group("value").trim()
            ));
        } else if ((matcher = CheatCommands.CheatSetAbility.getMatcher(input)) != null) {
            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().cheatSetAbility(
                    matcher.group("abilityName").trim(),
                    Integer.parseInt(matcher.group("level").trim())
            ));
        } else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }
}
