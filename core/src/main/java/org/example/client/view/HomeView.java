package org.example.client.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.server.controller.HomeController;
import org.example.server.models.App;
import org.example.server.models.GameAssetManager;
import org.example.server.models.Result;
import org.example.server.models.enums.Menu;
import org.example.server.models.enums.commands.CheatCommands;
import org.example.server.models.enums.commands.GameMenuCommands;
import org.example.server.models.enums.commands.HomeCommands;
import org.example.server.models.enums.commands.MainMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class HomeView extends AppMenu {

    private final HomeController controller;
    private Stage stage;

    private Image clockImage;
    private final Image clockArrowImage;

    private final TextButton advanceTimeButton;

    private final TextField textInputField;

    private boolean isInputFieldVisible;
    private boolean tJustPressed;


    public HomeView() {

        controller = new HomeController(this);
        advanceTimeButton = new TextButton("advance",skin);
        clockArrowImage = new Image(GameAssetManager.getGameAssetManager().getArrowTexture());
        textInputField = new TextField("",skin);
        isInputFieldVisible = false;
        tJustPressed = false;

    }




    private void displayClock(){

        controller.updateClockImage();

        clockImage.setPosition(1920-clockImage.getWidth(),1080-clockImage.getHeight());


        clockArrowImage.setPosition(
                clockImage.getX() + (0.255f * clockImage.getWidth()),
                clockImage.getY() + (0.37f * clockImage.getHeight())
        );
        clockArrowImage.setOrigin(
                clockArrowImage.getWidth() / 2,
                clockArrowImage.getHeight()
        );
        clockArrowImage.setRotation(controller.getClockArrowDegree());



        stage.addActor(clockImage);
        stage.addActor(clockArrowImage);
        clockArrowImage.toFront();



    }

    private void displayInputField(){

        if ( tJustPressed ){
            tJustPressed = false;
            textInputField.setText("");
        }

        textInputField.setVisible(isInputFieldVisible);

        TextField.TextFieldStyle style = new TextField.TextFieldStyle(textInputField.getStyle());
        style.fontColor = new Color(1,1,1,1);
        textInputField.setStyle(style);

        textInputField.setColor(0,0,0,0.5f);

        textInputField.setWidth(Gdx.graphics.getWidth());

        stage.addActor(textInputField);

    }




    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        stage.addActor(menuBackground);

        stage.addActor(advanceTimeButton);

        setListeners();


    }

    @Override
    public void render(float v) {

        Main.getBatch().begin();
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        displayClock();
        displayInputField();


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



    private void setListeners(){

        advanceTimeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                App.getCurrentGame().getTime().cheatAdvanceTime(1);
            }
        });

        stage.addListener(new InputListener() {

            @Override
            public boolean keyDown(InputEvent event, int keycode) {

                if ( !isInputFieldVisible ){

                    if ( keycode == Input.Keys.T ) {
                        playClickSound();
                        isInputFieldVisible = true;
                        textInputField.setText("");
                        tJustPressed = true;
                        return true;
                    }

                }

                else {

                    if ( keycode == Input.Keys.ENTER ) {
                        playClickSound();
                        controller.handleTextInput();
                        return true;
                    }
                    else if ( keycode == Input.Keys.ESCAPE ) {
                        playClickSound();
                        controller.closeTextInputField();
                        return true;
                    }

                }
                return false;
            }
        });



    }

    public void setClockImage(Image clockImage) {
        this.clockImage = clockImage;
    }

    public TextField getTextInputField() {
        return textInputField;
    }

    public void setInputFieldVisible(boolean inputFieldVisible) {
        isInputFieldVisible = inputFieldVisible;
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
