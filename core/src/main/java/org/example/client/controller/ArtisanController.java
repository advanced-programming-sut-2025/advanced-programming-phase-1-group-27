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
import org.example.client.model.ClientApp;
import org.example.client.view.HUDView;
import org.example.client.model.GameAssetManager;
import org.example.common.models.Artisan;
import org.example.common.models.Ingredient;
import org.example.common.models.Item;
import org.example.common.models.Stacks;
import org.example.client.model.enums.InGameMenuType;
import org.example.common.models.StackLevel;
import org.example.common.models.items.Recipe;
import org.example.common.models.items.products.ProcessedProductType;

import java.awt.*;
import java.util.ArrayList;

public class ArtisanController {

    private final HUDView hudView;
    private final Stage stage;

    private final Skin skin = GameAssetManager.getGameAssetManager().getSkin();

    //MINI MENU:
    private ArrayList<Label> artisanRecipe;
    private final Image artisanMiniBackground;

    private Artisan artisan;

    private final TextButton artisanCancelButton;
    private final TextButton artisanCheatButton;

    //MAIN MENU:

    private final ArrayList<Stacks> inventoryCopy;
    private final ArrayList<Image> itemImageButtons;
    private final ArrayList<Label> quantityLabels;

    private final ArrayList<Stacks> selectedItems;
    private final ArrayList<Image> selectedItemImages;
    private final ArrayList<Label> selectedQuantityLabels;


    private final Image artisanBackgrond;

    private final TextButton artisanProcessButton, artisanCollectButton;




    public ArtisanController(HUDView hudView, Stage stage) {
        this.hudView = hudView;
        this.stage = stage;

        //Mini Menu:
        artisanCancelButton = new TextButton("Cancel", skin);
        artisanCancelButton.setVisible(false);

        artisanCheatButton = new TextButton("Cheat", skin);
        artisanCheatButton.setVisible(false);

        artisanMiniBackground = new Image(GameAssetManager.getGameAssetManager().getPlayerSocialBackGroundTexture());
        artisanMiniBackground.setVisible(false);

        artisanRecipe = new ArrayList<>();

        stage.addActor(artisanMiniBackground);
        stage.addActor(artisanCancelButton);
        stage.addActor(artisanCheatButton);

        //Main Menu:
        inventoryCopy = new ArrayList<>();
        itemImageButtons = new ArrayList<>();
        quantityLabels = new ArrayList<>();

        selectedItems = new ArrayList<>();
        selectedItemImages = new ArrayList<>();
        selectedQuantityLabels = new ArrayList<>();

        artisanBackgrond = new Image(GameAssetManager.getGameAssetManager().getArtisanBackGroundTexture());
        artisanBackgrond.setHeight(700);
        artisanBackgrond.setWidth(700f / artisanBackgrond.getPrefHeight() * artisanBackgrond.getPrefWidth());
        artisanBackgrond.setPosition((Gdx.graphics.getWidth() - artisanBackgrond.getWidth()) / 2f,
                (Gdx.graphics.getHeight() - artisanBackgrond.getHeight()) / 2f);
        artisanBackgrond.setVisible(false);


        artisanProcessButton = new TextButton("Process", skin);
        artisanProcessButton.setPosition((Gdx.graphics.getWidth() - artisanBackgrond.getWidth()) / 2f,
                (Gdx.graphics.getHeight() + artisanBackgrond.getHeight()) / 2f + 10);
        artisanProcessButton.setWidth(artisanBackgrond.getWidth() / 2f - 10);
        artisanProcessButton.setVisible(false);

        artisanCollectButton = new TextButton("Collect", skin);
        artisanCollectButton.setPosition(Gdx.graphics.getWidth() / 2f + 10,
                (Gdx.graphics.getHeight() + artisanBackgrond.getHeight()) / 2f + 10);
        artisanCollectButton.setWidth(artisanBackgrond.getWidth() / 2f - 10);
        artisanCollectButton.setVisible(false);


        stage.addActor(artisanBackgrond);
        stage.addActor(artisanProcessButton);
        stage.addActor(artisanCollectButton);

        addListeners();
//        stage.setDebugAll(true);
    }



    private void displayArtisanMini() {
        artisanMiniBackground.setVisible(hudView.getCurrentMenu() == InGameMenuType.ARTISAN_MINI);
        artisanRecipe.forEach(label -> {
            label.setVisible(hudView.getCurrentMenu() == InGameMenuType.ARTISAN_MINI);
        });
        artisanCancelButton.setVisible(hudView.getCurrentMenu() == InGameMenuType.ARTISAN_MINI);
        artisanCheatButton.setVisible(hudView.getCurrentMenu() == InGameMenuType.ARTISAN_MINI);
    }

    private void setupArtisanMini(float x, float y) {
        artisanRecipe.forEach(Actor::remove);
        artisanRecipe.clear();

        float x2 = x + 30, y2 = y - 30;
        Label label = new Label("Recipes: ", skin);
        label.setColor(Color.BLACK);
        label.setPosition(x2, y2 - label.getHeight());
        label.setVisible(false);
        y2 -= label.getHeight();
        stage.addActor(label);
        artisanRecipe.add(label);
        int i = 1;
        for (ProcessedProductType product : artisan.getType().getProducts()) {
            label = new Label(i + ". " + product.getName(), skin);
            label.setColor(Color.BLACK);
            label.setFontScale(0.9f);
            label.setPosition(x2 + 5, y2 - label.getHeight());
            label.setVisible(false);
            y2 -= label.getHeight();
            stage.addActor(label);
            artisanRecipe.add(label);
            for (Ingredient ingredient : product.getRecipe().getIngredients()) {
                label = new Label(ingredient.getQuantity() + "*" + ingredient.getPossibleIngredients().get(0).getName(),
                        skin);
                label.setColor(Color.BLACK);
                label.setFontScale(0.7f);
                label.setPosition(x2 + 15, y2 - label.getHeight());
                label.setVisible(false);
                y2 -= label.getHeight();
                stage.addActor(label);
                artisanRecipe.add(label);
            }
            i++;
        }

        artisanCheatButton.setSize(210, 90);
        artisanCheatButton.setPosition(x2, y2 - 100);
        artisanCheatButton.toFront();

        artisanCancelButton.setSize(210, 90);
        artisanCancelButton.setPosition(x2 + 220 + 20, y2 - 100);
        artisanCancelButton.toFront();

        artisanMiniBackground.setWidth(500);
        artisanMiniBackground.setHeight(y - y2 + 120);
        artisanMiniBackground.setPosition(x, y2 - 120);
        artisanMiniBackground.setVisible(false);

    }

    public void setArtisanMini(Artisan artisan , float x, float y) {
        this.artisan = artisan;
        setupArtisanMini(x, y);
        hudView.setCurrentMenu(InGameMenuType.ARTISAN_MINI);
        hudView.makeOnScreenItemsInvisible();
    }


    public void displayArtisan() {
        artisanBackgrond.setVisible(hudView.getCurrentMenu() == InGameMenuType.ARTISAN);
        artisanCollectButton.setVisible(hudView.getCurrentMenu() == InGameMenuType.ARTISAN &&
                artisan.getFinalProduct() != null && artisan.getTimeLeft() == 0);
        artisanProcessButton.setVisible(hudView.getCurrentMenu() == InGameMenuType.ARTISAN);

        itemImageButtons.forEach(imageButton -> {
            imageButton.setVisible(hudView.getCurrentMenu() == InGameMenuType.ARTISAN);
        });
        quantityLabels.forEach(quantityLabel -> {
            quantityLabel.setVisible(hudView.getCurrentMenu() == InGameMenuType.ARTISAN);
        });
        selectedItemImages.forEach(selectedItemImage -> {
            selectedItemImage.setVisible(hudView.getCurrentMenu() == InGameMenuType.ARTISAN);
        });
        selectedQuantityLabels.forEach(selectedQuantityLabel -> {
            selectedQuantityLabel.setVisible(hudView.getCurrentMenu() == InGameMenuType.ARTISAN);
        });


        for (int i = 0; i < inventoryCopy.size(); i++) {
            Stacks stacks = inventoryCopy.get(i);
            Image image = itemImageButtons.get(i);
            Label label = quantityLabels.get(i);

            Rectangle rectangle = new Rectangle((int) image.getX(), (int) image.getY(),
                    (int) image.getWidth(), (int) image.getHeight());

            int count = Integer.parseInt(String.valueOf(label.getText()));
            if (Gdx.input.justTouched() && rectangle.contains(
                    Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()
            ) && count > 0) {
                if (addOne(stacks.getItem()))
                    label.setText(String.valueOf(count - 1));
            }
        }
        int temp = -1;
        for (int i = 0; i < selectedItems.size(); i++) {
            Stacks stacks = selectedItems.get(i);
            Image image = selectedItemImages.get(i);
            Label label = selectedQuantityLabels.get(i);

            Rectangle rectangle = new Rectangle((int) image.getX(), (int) image.getY(),
                    (int) image.getWidth(), (int) image.getHeight());

            int count = Integer.parseInt(String.valueOf(label.getText()));
            if (Gdx.input.justTouched() && rectangle.contains(
                    Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()
            ) && count > 0) {
                if (count > 1) {
                    label.setText(String.valueOf(count - 1));
                    stacks.setQuantity(count - 1);
                }
                else {
                    temp = i;
                }

                removeOne(stacks.getItem());
            }
        }
        if (temp != -1) {
            selectedItemImages.get(temp).remove();
            selectedQuantityLabels.get(temp).remove();
            selectedItems.remove(temp);
            selectedItemImages.remove(temp);
            selectedQuantityLabels.remove(temp);
        }

    }

    private void removeOne(Item item) {
        for (int i = 0; i < inventoryCopy.size(); i++) {
            Stacks stacks = inventoryCopy.get(i);
            Label label = quantityLabels.get(i);
            int count = Integer.parseInt(String.valueOf(label.getText()));
            if (stacks.getItem().equals(item)) {
                label.setText(String.valueOf(count + 1));
            }
        }
        updateSelected();
    }

    private boolean addOne(Item item) {
        boolean flag = false;
        for (Stacks selectedItem : selectedItems) {
            if (selectedItem.getItem() == item) {
                selectedItem.setQuantity(selectedItem.getQuantity() + 1);
                flag = true;
            }
        }
        if (!flag) {
            if (selectedItems.size() == 12)
                return false;
            selectedItems.add(new Stacks(item, 1));
        }
        updateSelected();
        return true;
    }

    private void updateSelected() {

        selectedItemImages.forEach(Actor::remove);
        selectedQuantityLabels.forEach(Actor::remove);
        selectedItemImages.clear();
        selectedQuantityLabels.clear();

        float leftEdge = (Gdx.graphics.getWidth() - artisanBackgrond.getWidth()) / 2f + 57,
                upEdge = 800;
        float distX = 63.7f,
                distY = 67.5f;
        float x = leftEdge,
                y = upEdge;
        int index = 0;

        for (Stacks stacks : selectedItems) {
            // ----------Image----------
            Image image = new Image(
                    GameAssetManager.getGameAssetManager().getItemTexture(stacks.getItem())
            );
            image.setSize(60, 60);
            image.setPosition(x, y);

            image.setVisible(false);
            selectedItemImages.add(image);
            stage.addActor(image);

            // ---------Count----------
            Label label = new Label(
                    stacks.getQuantity() + "", skin
            );
            label.setColor(Color.RED);
            label.setFontScale(0.75f);
            label.setPosition(x + 40, y + 40);

            label.setVisible(false);
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

    public void setupArtisan() {
        artisanProcessButton.toFront();
        artisanCollectButton.toFront();

        inventoryCopy.clear();
        itemImageButtons.forEach(Actor::remove);
        quantityLabels.forEach(Actor::remove);
        itemImageButtons.clear();
        quantityLabels.clear();

        selectedItems.clear();
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

        for (Stacks item : ClientApp.getCurrentGame().getCurrentPlayer().getBackpack().getItems())
            inventoryCopy.add(new Stacks(item.getItem(), item.getStackLevel(), item.getQuantity()));

        for (Stacks stacks : inventoryCopy) {
            // ----------Image----------
            Image image = new Image(
                    GameAssetManager.getGameAssetManager().getItemTexture(stacks.getItem())
            );
            image.setSize(60, 60);
            image.setPosition(x, y);

            image.setVisible(false);
            itemImageButtons.add(image);
            stage.addActor(image);

            // ---------Count----------
            Label label = new Label(
                    stacks.getQuantity() + "", skin
            );
            label.setColor(Color.RED);
            label.setFontScale(0.75f);
            label.setPosition(x + 40, y + 40);

            label.setVisible(false);
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

    }

    public void setArtisan(Artisan artisan) {
        this.artisan = artisan;
        setupArtisan();
        hudView.setCurrentMenu(InGameMenuType.ARTISAN);
        hudView.makeOnScreenItemsInvisible();
    }

    public void update() {
        displayArtisanMini();
        displayArtisan();
    }

    private void addListeners() {

        artisanCancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hudView.playClickSound();
                hudView.setCurrentMenu(InGameMenuType.NONE);


                if (artisan.getTimeLeft() > 0) {
                    artisan.free();
                    ResultController.addSuccess("The Process was Canceled!");
                    return;
                }
                ResultController.addError("There is no ongoing process!");
            }
        });

        artisanCheatButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hudView.playClickSound();
                hudView.setCurrentMenu(InGameMenuType.NONE);


                if (artisan.getTimeLeft() > 0) {
                    ClientApp.getCurrentGame().getCurrentPlayer().getBackpack().addItems(
                            artisan.getFinalProduct(), StackLevel.Basic, 1
                    );

                    artisan.free();
                    ResultController.addSuccess("The Product was added!");
                    return;
                }
                ResultController.addError("There is no ongoing process!");

            }
        });

        artisanProcessButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hudView.playClickSound();
                if (artisan.getFinalProduct() != null) {
                    ResultController.addError("There is another ongoing process");
                    hudView.setCurrentMenu(InGameMenuType.NONE);
                    return;
                }
                for (ProcessedProductType product : artisan.getType().getProducts()) {
                    if (hasEnoughIngredients(product.getRecipe())) {
                        ClientApp.getCurrentGame().getCurrentPlayer().useRecipeWithoutAdd(product.getRecipe());
                        artisan.setFinalProduct(product);
                        artisan.setTimeLeft(product.getProcessingTime());
                        ResultController.addSuccess("The Process was Started!");
                        hudView.setCurrentMenu(InGameMenuType.NONE);
                        return;
                    }
                }
                ResultController.addError("No Valid Recipe Found");
                hudView.setCurrentMenu(InGameMenuType.NONE);
            }
        });

        artisanCollectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hudView.playClickSound();
                hudView.setCurrentMenu(InGameMenuType.NONE);

                if (artisan.getTimeLeft() == 0 && artisan.getFinalProduct() != null) {
                    ClientApp.getCurrentGame().getCurrentPlayer().getBackpack().addItems(
                            artisan.getFinalProduct(), StackLevel.Basic, 1
                    );
                    artisan.free();
                    ResultController.addSuccess("The Product was added!");
                } else if (artisan.getFinalProduct() != null) {
                    ResultController.addError("You have to wait more!");
                } else {
                    ResultController.addError("There is no ongoing process!");
                }
            }
        });

    }



    private boolean hasEnoughIngredients(Recipe recipe) {
        for (Ingredient ingredient : recipe.getIngredients()) {
            if (getAvailableIngredient(ingredient) == null)
                return false;
        }
        return true;
    }

    private Item getAvailableIngredient(Ingredient ingredient) {
        for (Item item : ingredient.getPossibleIngredients()) {
            if (hasEnoughItem(item, ingredient.getQuantity()))
                return item;
        }
        return null;
    }

    private boolean hasEnoughItem(Item item, int quantity) {
        int counter = 0;
        for (Stacks slot : selectedItems) {
            if (slot.getItem().getName().equalsIgnoreCase(item.getName()))
                counter += slot.getQuantity();
        }
        return counter >= quantity;
    }
}
