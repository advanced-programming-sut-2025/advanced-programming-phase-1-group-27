package org.example.client.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.example.client.model.ClientApp;
import org.example.client.model.GameAssetManager;
import org.example.client.model.enums.InGameMenuType;
import org.example.client.view.HUDView;
import org.example.client.view.HomeView;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Player;
import org.example.common.models.Stacks;

import java.awt.*;
import java.util.ArrayList;

public class FridgeController {

    private final HUDView hudView;
    private final Stage stage;
    private final Player player = ClientApp.getCurrentGame().getCurrentPlayer();

    private final Skin skin = GameAssetManager.getGameAssetManager().getSkin();

    //MAIN MENU:
    private final ArrayList<Image> itemImages;
    private final ArrayList<Label> quantityLabels;
    private final ArrayList<Image> selectedItemImages;
    private final ArrayList<Label> selectedQuantityLabels;
    private final Image artisanBackgrond;
    private final ImageButton fridgeButton;
    private final TextButton okButton;
    private ArrayList<Stacks> inventoryCopy;
    private ArrayList<Stacks> selectedItems;
    private GraphicalResult error = new GraphicalResult();


    public FridgeController(HUDView hudView, Stage stage) {
        this.hudView = hudView;
        this.stage = stage;

        //Main Menu:
        inventoryCopy = new ArrayList<>();
        itemImages = new ArrayList<>();
        quantityLabels = new ArrayList<>();

        selectedItems = new ArrayList<>();
        selectedItemImages = new ArrayList<>();
        selectedQuantityLabels = new ArrayList<>();

        fridgeButton = new ImageButton(new TextureRegionDrawable(
                GameAssetManager.getGameAssetManager().getFridgeTexture()
        ));
        fridgeButton.setPosition(245, 95);

        artisanBackgrond = new Image(GameAssetManager.getGameAssetManager().getArtisanBackGroundTexture());
        artisanBackgrond.setHeight(700);
        artisanBackgrond.setWidth(700f / artisanBackgrond.getPrefHeight() * artisanBackgrond.getPrefWidth());
        artisanBackgrond.setPosition((Gdx.graphics.getWidth() - artisanBackgrond.getWidth()) / 2f,
                (Gdx.graphics.getHeight() - artisanBackgrond.getHeight()) / 2f);
        artisanBackgrond.setVisible(hudView.getCurrentMenu() == InGameMenuType.FRIDGE);


        okButton = new TextButton("OK", skin);
        okButton.setPosition((Gdx.graphics.getWidth() + artisanBackgrond.getWidth()) / 2f - 105,
                (Gdx.graphics.getHeight() + artisanBackgrond.getHeight()) / 2f + 7);
        okButton.setWidth(105);
        okButton.setVisible(hudView.getCurrentMenu() == InGameMenuType.FRIDGE);


        stage.addActor(fridgeButton);
        stage.addActor(artisanBackgrond);
        stage.addActor(okButton);

        addListeners();
//        stage.setDebugAll(true);
    }

    public void displayFridge() {
        artisanBackgrond.setVisible(hudView.getCurrentMenu() == InGameMenuType.FRIDGE);
        okButton.setVisible(hudView.getCurrentMenu() == InGameMenuType.FRIDGE);

        itemImages.forEach(imageButton -> {
            imageButton.setVisible(hudView.getCurrentMenu() == InGameMenuType.FRIDGE);
        });
        quantityLabels.forEach(quantityLabel -> {
            quantityLabel.setVisible(hudView.getCurrentMenu() == InGameMenuType.FRIDGE);
        });
        selectedItemImages.forEach(selectedItemImage -> {
            selectedItemImage.setVisible(hudView.getCurrentMenu() == InGameMenuType.FRIDGE);
        });
        selectedQuantityLabels.forEach(selectedQuantityLabel -> {
            selectedQuantityLabel.setVisible(hudView.getCurrentMenu() == InGameMenuType.FRIDGE);
        });

        if (hudView.getCurrentMenu() == InGameMenuType.FRIDGE) {
            for (int i = 0; i < inventoryCopy.size(); i++) {
                Stacks stacks = inventoryCopy.get(i);
                Image image = itemImages.get(i);
                Label label = quantityLabels.get(i);

                Rectangle rectangle = new Rectangle((int) image.getX(), (int) image.getY(),
                        (int) image.getWidth(), (int) image.getHeight());

                int count = Integer.parseInt(String.valueOf(label.getText()));
                if (Gdx.input.justTouched() && rectangle.contains(
                        Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()
                ) && selectedItems.size() <= 12) {
                    if (!stacks.getItem().isEdible()) {
                        error.getMessage().remove();
                        error = new GraphicalResult("The Selected Item cannot be placed in the fridge!", true);
                        error.setPosition((Gdx.graphics.getWidth() - error.getMessage().getWidth()) / 2f,
                                Gdx.graphics.getHeight() - 70);
                        stage.addActor(error.getMessage());
                    } else {
                        player.getBackpack().reduceItems(stacks.getItem(), 1);
                        player.getFarmMap().getHut().getRefrigerator().addItems(stacks.getItem(), stacks.getStackLevel(),
                                1);
                        setupFridge();
                    }
                }
            }
            for (int i = 0; i < selectedItems.size(); i++) {
                Stacks stacks = selectedItems.get(i);
                Image image = selectedItemImages.get(i);
                Label label = selectedQuantityLabels.get(i);

                Rectangle rectangle = new Rectangle((int) image.getX(), (int) image.getY(),
                        (int) image.getWidth(), (int) image.getHeight());

                int count = Integer.parseInt(String.valueOf(label.getText()));
                if (Gdx.input.justTouched() && rectangle.contains(
                        Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()
                ) && !player.getBackpack().isFull()) {
                    player.getBackpack().addItems(stacks.getItem(), stacks.getStackLevel(), 1);
                    player.getFarmMap().getHut().getRefrigerator().reduceItems(stacks.getItem(), 1);
                    setupFridge();
                }
            }
        }
    }

    public void setupFridge() {
        okButton.toFront();

        itemImages.forEach(Actor::remove);
        quantityLabels.forEach(Actor::remove);
        itemImages.clear();
        quantityLabels.clear();

        selectedItemImages.forEach(Actor::remove);
        selectedQuantityLabels.forEach(Actor::remove);
        selectedItemImages.clear();
        selectedQuantityLabels.clear();


        float leftEdge = (Gdx.graphics.getWidth() - artisanBackgrond.getWidth()) / 2f + 57,
                upEdge = 693;
        float distX = 63.7f,
                distY = 67.5f;
        float x = leftEdge,
                y = upEdge;
        int index = 0;

//        for (Stacks item : ClientApp.getCurrentGame().getCurrentPlayer().getBackpack().getItems())
//            inventoryCopy.add(new Stacks(item.getItem(), item.getStackLevel(), item.getQuantity()));
        inventoryCopy = ClientApp.getCurrentGame().getCurrentPlayer().getBackpack().getItems();

        for (Stacks stacks : inventoryCopy) {
            // ----------Image----------
            Image image = new Image(
                    GameAssetManager.getGameAssetManager().getItemTexture(stacks.getItem())
            );
            image.setSize(60, 60);
            image.setPosition(x, y);

            image.setVisible(hudView.getCurrentMenu() == InGameMenuType.FRIDGE);
            itemImages.add(image);
            stage.addActor(image);

            // ---------Count----------
            Label label = new Label(
                    stacks.getQuantity() + "", skin
            );
            label.setColor(Color.RED);
            label.setFontScale(0.75f);
            label.setPosition(x + 40, y + 40);

            label.setVisible(hudView.getCurrentMenu() == InGameMenuType.FRIDGE);
            quantityLabels.add(label);
            stage.addActor(label);


            // ----------Modification----------
            index++;
            if (index == 12) {
                index -= 12;
                y -= distY;
                x = leftEdge;
            } else {
                x += distX;
            }
        }

        selectedItems = ClientApp.getCurrentGame().getCurrentPlayer().getFarmMap().getHut().getRefrigerator().getItems();

        leftEdge = (Gdx.graphics.getWidth() - artisanBackgrond.getWidth()) / 2f + 57;
        upEdge = 800;
        distX = 63.7f;
        distY = 67.5f;
        x = leftEdge;
        y = upEdge;
        index = 0;

        for (Stacks stacks : selectedItems) {
            // ----------Image----------
            Image image = new Image(
                    GameAssetManager.getGameAssetManager().getItemTexture(stacks.getItem())
            );
            image.setSize(60, 60);
            image.setPosition(x, y);

            image.setVisible(hudView.getCurrentMenu() == InGameMenuType.FRIDGE);
            selectedItemImages.add(image);
            stage.addActor(image);

            // ---------Count----------
            Label label = new Label(
                    stacks.getQuantity() + "", skin
            );
            label.setColor(Color.RED);
            label.setFontScale(0.75f);
            label.setPosition(x + 40, y + 40);

            label.setVisible(hudView.getCurrentMenu() == InGameMenuType.FRIDGE);
            selectedQuantityLabels.add(label);
            stage.addActor(label);


            // ----------Modification----------
            index++;
            if (index == 12) {
                index -= 12;
                y -= distY;
                x = leftEdge;
            } else {
                x += distX;
            }
        }

    }

    public void openFridge() {
        setupFridge();
        hudView.setCurrentMenu(InGameMenuType.FRIDGE);
        hudView.makeOnScreenItemsInvisible();
    }

    public void update() {
        displayFridge();
        fridgeButton.setVisible(hudView.getCurrentMenu() == InGameMenuType.NONE &&
                ClientApp.getCurrentMenu() instanceof HomeView);
        fridgeButton.toFront();
        error.update(Gdx.graphics.getDeltaTime());
    }

    private void addListeners() {
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hudView.setCurrentMenu(InGameMenuType.NONE);
            }
        });
        fridgeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openFridge();
            }
        });
    }
}
