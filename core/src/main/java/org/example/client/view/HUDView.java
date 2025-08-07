package org.example.client.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import org.example.client.controller.InteractionsWithOthers.InteractionsWithUserController;
import org.example.client.model.ClientApp;
import org.example.client.model.MiniPlayer;
import org.example.common.models.Game;
import org.example.common.models.GameAssetManager;
import org.example.common.models.GraphicalResult;
import org.example.client.controller.HUDController;
import org.example.common.utils.JSONUtils;
import org.example.server.models.Item;
import org.example.server.models.Player;
import org.example.server.models.Relations.Relation;
import org.example.server.models.Stacks;
import org.example.server.models.enums.AbilityType;
import org.example.server.models.enums.InGameMenuType;
import org.example.server.models.enums.NPCType;
import org.example.server.models.enums.items.Recipe;
import org.example.server.models.enums.items.products.CookingProduct;
import org.example.server.models.enums.items.products.CraftingProduct;
import org.example.server.models.tools.Backpack;

import java.awt.*;
import java.rmi.ConnectIOException;
import java.util.*;
import java.util.List;

public class HUDView extends AppMenu {

    private final HUDController controller;

    private final Stage stage;

    private final Player player;

    private final Label dayInfo;
    private final Label moneyInfo;
    private final Label timeInfo;
    private final Label craftingProductNameLabel;
    private final Label craftingProductIngredientsLabel;
    private final Label messageLabel;

    private final Image blackImage;
    private final Image hoveringInfoWindow;
    private final Image clockArrowImage;
    private final Image inventoryHotBarImage;
    private final Image inventorySelectSlotImage;
    private final Image craftingMenuBackground;
    private final Image inventoryMenuBackground;
    private final Image skillMenuBackground;
    private final Image exitMenuBackground;
    private final Image cookingMenuBackground;
    private final Image farmingHoverImage;
    private final Image fishingHoverImage;
    private final Image miningHoverImage;
    private final Image foragingHoverImage;
    private final Image socialMenuBackground;
    private final Image playerSocialMenuBackground;
    private final Image mapMenuBackground;
    private final Image energyBar;
    private final Image boostBar;
    private final Image greenBar;
    private final Image redBar;
    private final Image messageBackgroundImage;
    private final Image messageAlertImage;
    private Image clockImage;

    private final ImageButton socialButton;

    private final ArrayList<Image> skillPoints;
    private final ArrayList<Image> npcAvatars;
    private final ArrayList<Image> checkBoxes;

    private final HashMap<CraftingProduct, ImageButton> craftingProducts;
    private final HashMap<CookingProduct, ImageButton> cookingProducts;
    private HashMap<Stacks,Label> onScreenItemsQuantity;

    private final TextField textInputField;

    private final GraphicalResult errorLabel;

    private boolean isInputFieldVisible;
    private boolean tJustPressed;
    private boolean ctrlPressed;

    private Backpack inventoryItems;

    private ArrayList<Stacks> onScreenItems;

    private Integer rowCoEfficient;
    private Integer currentSlotInInventory;

    private InGameMenuType currentMenu;

    private Item currentStacksHover;

    private final TextButton exitGameButton;
    private final TextButton exitToMainButton;
    private final TextButton kickPlayerButton;

    private final SelectBox<String> playerSelectBox;

    private AbilityType currentAbilityTypeHovering;

    private final ArrayList<Label> npcLabels;
    private final ArrayList<Label> npcInfos;

    private final ArrayList<Label> friendsLabels;
    private final ArrayList<Label> friendshipInfos;
    private final ArrayList<TextButton> friendButtons;

//    private final ArrayList<String>

    public HUDView(Stage stage) {


        controller = new HUDController(this);

        this.stage = stage;

        skillPoints = new ArrayList<>();
        for( int i = 0 ; i < 16; i++ ){
            skillPoints.add(new Image(GameAssetManager.getGameAssetManager().getSkillPointImage()));
        }

        checkBoxes = new ArrayList<>();
        for( int i = 0 ; i < 10; i++ ){
            checkBoxes.add(new Image(GameAssetManager.getGameAssetManager().getCheckBox()));
        }

        npcAvatars = new ArrayList<>();
        npcAvatars.add(GameAssetManager.getGameAssetManager().getNpc1Avatar());
        npcAvatars.add(GameAssetManager.getGameAssetManager().getNpc2Avatar());
        npcAvatars.add(GameAssetManager.getGameAssetManager().getNpc3Avatar());
        npcAvatars.add(GameAssetManager.getGameAssetManager().getNpc4Avatar());
        npcAvatars.add(GameAssetManager.getGameAssetManager().getNpc5Avatar());

        npcLabels = new ArrayList<>();
        npcLabels.add(new Label(NPCType.Abigail.getName(),skin));
        npcLabels.add(new Label(NPCType.Harvey.getName(),skin));
        npcLabels.add(new Label(NPCType.Lia.getName(),skin));
        npcLabels.add(new Label(NPCType.Robbin.getName(),skin));
        npcLabels.add(new Label(NPCType.Sebastian.getName(),skin));

        npcInfos = new ArrayList<>();

        player = ClientApp.getCurrentGame().getCurrentPlayer();

        currentAbilityTypeHovering = null;

        craftingProductNameLabel = new Label("",skin);
        craftingProductIngredientsLabel = new Label("", skin);
        dayInfo = new Label("", skin);
        moneyInfo = new Label("", skin);
        timeInfo = new Label("", skin);
        messageLabel = new Label("",skin);



        playerSelectBox = new SelectBox<>(skin);

        Array<String> playersUsername = new Array<>();
        ///  TODO: esm player haye game ezafe shan
        playersUsername.add("ali");
        playersUsername.add("mohsen");
        playersUsername.add("majid");
        playerSelectBox.setItems(playersUsername);
        playerSelectBox.setWidth(300);
        playerSelectBox.setPosition(600,380);
        playerSelectBox.setVisible(false);

        exitGameButton = new TextButton("Exit Game",skin);
        exitToMainButton = new TextButton("Exit to main",skin);
        kickPlayerButton = new TextButton("Kick",skin);

        kickPlayerButton.setPosition(950,380-(kickPlayerButton.getHeight()-playerSelectBox.getHeight())/2f);
        kickPlayerButton.setVisible(false);


        rowCoEfficient = 1;
        currentSlotInInventory = null;

        currentStacksHover = null;

        hoveringInfoWindow = GameAssetManager.getGameAssetManager().getHoveringInfoWindow();
        blackImage = GameAssetManager.getGameAssetManager().getBlackImage();
        cookingMenuBackground = GameAssetManager.getGameAssetManager().getCookingMenuBackground();
        craftingMenuBackground = GameAssetManager.getGameAssetManager().getCraftingMenuBackground();
        inventoryMenuBackground = GameAssetManager.getGameAssetManager().getInventoryMenuBackground();
        skillMenuBackground = GameAssetManager.getGameAssetManager().getSkillMenuBackground();
        exitMenuBackground = GameAssetManager.getGameAssetManager().getExitMenuBackground();
        clockArrowImage = new Image(GameAssetManager.getGameAssetManager().getArrowTexture());
        inventoryHotBarImage = new Image(GameAssetManager.getGameAssetManager().getInventoryHotBar());
        inventorySelectSlotImage = new Image(GameAssetManager.getGameAssetManager().getInventorySelectSlot());
        farmingHoverImage = GameAssetManager.getGameAssetManager().getHoveringFarmingWindow();
        miningHoverImage = GameAssetManager.getGameAssetManager().getHoveringMiningWindow();
        foragingHoverImage = GameAssetManager.getGameAssetManager().getHoveringForagingWindow();
        fishingHoverImage = GameAssetManager.getGameAssetManager().getHoveringFishingWindow();
        socialMenuBackground = GameAssetManager.getGameAssetManager().getSocialMenuBackgroundImage();
        playerSocialMenuBackground = GameAssetManager.getGameAssetManager().getPlayerSocialBackground();
        mapMenuBackground = GameAssetManager.getGameAssetManager().getMapMenuBackground();
        energyBar = GameAssetManager.getGameAssetManager().getEnergyBar();
        boostBar = GameAssetManager.getGameAssetManager().getBoostBar();
        greenBar = GameAssetManager.getGameAssetManager().getGreenBar();
        redBar = GameAssetManager.getGameAssetManager().getRedBar();
        messageAlertImage = GameAssetManager.getGameAssetManager().getMessageAlertImage();
        messageBackgroundImage = GameAssetManager.getGameAssetManager().getMessageBackgroundImage();

        socialButton = new ImageButton(new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getSocialButton()));
        socialButton.setPosition(100,100);

        textInputField = new TextField("", skin);

        currentMenu = InGameMenuType.NONE;

        isInputFieldVisible = false;
        tJustPressed = false;

        errorLabel = new GraphicalResult();

        inventoryItems = player.getBackpack();

        onScreenItems = new ArrayList<>();
        onScreenItemsQuantity = new HashMap<>();
        craftingProducts = new HashMap<>();
        cookingProducts = new HashMap<>();

        controller.setDayInfo(dayInfo);
        controller.setMoneyInfo(moneyInfo);
        controller.setTimeInfo(timeInfo);

        for ( Stacks stack : inventoryItems.getItems() ) {
            addToScreen(Stacks.copy(stack));
        }


        int i = 0;
        for (CraftingProduct craftingProduct : CraftingProduct.values()) {
            ImageButton productButton = new ImageButton(new TextureRegionDrawable(craftingProduct.getTexture()));
            productButton.setSize(96, 96);
            productButton.setPosition(520 + 110 * (i % 7), (float) (680 - (i / 7) * 100));
            productButton.setColor(productButton.getColor().r, productButton.getColor().g, productButton.getColor().b, 0.3f + ((ClientApp.getCurrentGame().getCurrentPlayer().hasEnoughIngredients(craftingProduct.getRecipe())) ? 0.7f : 0f));
            craftingProducts.put(craftingProduct, productButton);
            i++;
        }


        i = 0;
        for (CookingProduct cookingProduct : CookingProduct.values()) {
            ImageButton productButton = new ImageButton(new TextureRegionDrawable(cookingProduct.getTexture()));
            productButton.setSize(36, 36);
            productButton.setPosition(520 + 110 * (i % 7), (float) (750 - (i / 7) * 80));
            productButton.setColor(productButton.getColor().r, productButton.getColor().g, productButton.getColor().b, 0.3f + ((ClientApp.getCurrentGame().getCurrentPlayer().hasEnoughIngredients(cookingProduct.getRecipe())) ? 0.7f : 0f));
            cookingProducts.put(cookingProduct, productButton);
            i++;
        }



        mapMenuBackground.setPosition((Gdx.graphics.getWidth()-mapMenuBackground.getWidth())/2f,(Gdx.graphics.getHeight()-mapMenuBackground.getHeight())/2f);
        mapMenuBackground.setVisible(false);

        playerSocialMenuBackground.setPosition((Gdx.graphics.getWidth()-playerSocialMenuBackground.getWidth())/2f,(Gdx.graphics.getHeight()-playerSocialMenuBackground.getHeight())/2f);
        playerSocialMenuBackground.setVisible(false);

        cookingMenuBackground.setPosition((Gdx.graphics.getWidth()-cookingMenuBackground.getWidth())/2f,(Gdx.graphics.getHeight()-cookingMenuBackground.getHeight())/2f);
        cookingMenuBackground.setVisible(false);

        hoveringInfoWindow.setPosition(Gdx.graphics.getWidth()-hoveringInfoWindow.getWidth()-80,
                20);


        craftingProductNameLabel.setPosition(hoveringInfoWindow.getX()+20,hoveringInfoWindow.getHeight()-20);
        craftingProductNameLabel.setVisible(false);
        craftingProductNameLabel.setColor(Color.BLACK);
        craftingProductNameLabel.setFontScale(1f);

        craftingProductIngredientsLabel.setPosition(hoveringInfoWindow.getX()+20,
                hoveringInfoWindow.getHeight()/2f);
        craftingProductIngredientsLabel.setVisible(false);
        craftingProductIngredientsLabel.setColor(Color.BLACK);
        craftingProductIngredientsLabel.setFontScale(0.7f);

        exitGameButton.setPosition((Gdx.graphics.getWidth()-exitGameButton.getWidth())/2f,
                620);
        exitToMainButton.setPosition((Gdx.graphics.getWidth()-exitToMainButton.getWidth())/2f,
                500);

        exitToMainButton.setVisible(false);
        exitGameButton.setVisible(false);

        controller.updateClockImage();

        farmingHoverImage.setVisible(false);
        fishingHoverImage.setVisible(false);
        miningHoverImage.setVisible(false);
        foragingHoverImage.setVisible(false);

        // STAGE
        stage.addActor(blackImage);
        stage.addActor(cookingMenuBackground);
        stage.addActor(hoveringInfoWindow);
        stage.addActor(craftingProductNameLabel);
        stage.addActor(craftingProductIngredientsLabel);
        stage.addActor(exitGameButton);
        stage.addActor(kickPlayerButton);
        stage.addActor(exitToMainButton);
        stage.addActor(playerSelectBox);
        stage.addActor(exitMenuBackground);
        stage.addActor(clockImage);
        stage.addActor(clockArrowImage);
        stage.addActor(inventoryHotBarImage);
        stage.addActor(inventorySelectSlotImage);
        stage.addActor(errorLabel.getMessage());
        stage.addActor(dayInfo);
        stage.addActor(moneyInfo);
        stage.addActor(timeInfo);
        stage.addActor(inventoryMenuBackground);
        stage.addActor(skillMenuBackground);
        stage.addActor(craftingMenuBackground);
        stage.addActor(socialMenuBackground);
        stage.addActor(farmingHoverImage);
        stage.addActor(fishingHoverImage);
        stage.addActor(miningHoverImage);
        stage.addActor(foragingHoverImage);
        stage.addActor(socialButton);
        stage.addActor(playerSocialMenuBackground);
        stage.addActor(mapMenuBackground);
        stage.addActor(energyBar);
        stage.addActor(boostBar);
        stage.addActor(greenBar);
        stage.addActor(redBar);


        friendsLabels = new ArrayList<>();
        friendshipInfos = new ArrayList<>();
        friendButtons = new ArrayList<>();

        int counter = 0;

        for ( int z = 0; z < Math.min(4,ClientApp.getCurrentGame().getPlayers().size()); z++ ){

            if (!Objects.equals(ClientApp.getCurrentGame().getPlayers().get(z).getUsername(),
                    player.getUsername())){

                Relation relation = InteractionsWithUserController.getRelation(ClientApp.getCurrentGame().getPlayers().get(z).getUsername());

                Label nameLabel = new Label(ClientApp.getCurrentGame().getPlayers().get(z).getUsername(),skin);
                Label friendshipInfo = new Label("Level: "+relation.getLevel()+"    XP: "+relation.getXp(),skin); /// TODO: PARSA XP/LVL por kon pls
                TextButton giftButton = new TextButton("Gift",skin);
                TextButton tradeMenuButton = new TextButton("Trade",skin);

                nameLabel.setColor(Color.BLACK);
                friendshipInfo.setColor(Color.BLACK);

                nameLabel.setVisible(false);
                friendshipInfo.setVisible(false);
                giftButton.setVisible(false);
                tradeMenuButton.setVisible(false);

                friendsLabels.add(nameLabel);
                friendshipInfos.add(friendshipInfo);
                friendButtons.add(giftButton);
                friendButtons.add(tradeMenuButton);

                nameLabel.setPosition(520,660 - 180 * counter);
                friendshipInfo.setPosition(670,660 - 180 * counter);
                giftButton.setPosition(890,660 - 180 * counter - (giftButton.getHeight()-nameLabel.getHeight())/2f);
                tradeMenuButton.setPosition(1070,660 - 180 * counter -  (tradeMenuButton.getHeight()-nameLabel.getHeight())/2f);


                stage.addActor(nameLabel);
                stage.addActor(friendshipInfo);
                stage.addActor(giftButton);
                stage.addActor(tradeMenuButton);

                counter ++;


            }

        }


        for (CraftingProduct craftingProduct : craftingProducts.keySet()) {

            ImageButton imageButton = craftingProducts.get(craftingProduct);
            imageButton.setVisible(false);
            stage.addActor(imageButton);

        }

        for (CookingProduct cookingProduct : cookingProducts.keySet()) {

            ImageButton imageButton = cookingProducts.get(cookingProduct);
            imageButton.setVisible(false);
            stage.addActor(imageButton);

        }

        for ( int k = 0 ; k < 16; k++ ){

            Image skillPoint = skillPoints.get(k);
            skillPoint.setPosition(874 + (k%4) * 57,704 - (k/4) * 84);
            skillPoint.setVisible(false);
            stage.addActor(skillPoint);

        }

        for ( int t = 0 ; t < 10; t++ ){

            Image checkBox = checkBoxes.get(t);
            checkBox.setPosition(1123 + (t%2) * 118,688 - (t/2) * 112);
            checkBox.setVisible(false);
            stage.addActor(checkBox);

        }

        for ( int j = 0; j < 5; j++ ) {
            npcAvatars.get(j).setSize(110,110);
            npcAvatars.get(j).setPosition(496,680 - 110 * j - 2 * j);
            npcAvatars.get(j).setVisible(false);
            npcLabels.get(j).setPosition(620,680 - 110 * j - 2 * j+40);
            npcLabels.get(j).setVisible(false);
            npcLabels.get(j).setFontScale(0.8f);
            npcLabels.get(j).setColor(Color.BLACK);
            stage.addActor(npcAvatars.get(j));
            stage.addActor(npcLabels.get(j));
            Label infoLabel = new Label("",skin);
            npcInfos.add(infoLabel);
            infoLabel.setVisible(false);
            infoLabel.setColor(Color.BLACK);
            infoLabel.setPosition(800,680 - 110 * j - 2 * j+50);
            stage.addActor(infoLabel);
        }

        stage.addActor(textInputField);


        setListeners();

    }

    private void displayClock() {

        if ( ClientApp.getCurrentGame().getTime().getHour() == 9 ){
            clockImage.remove();
        }

        controller.updateClockImage();

        clockImage.setPosition(1800 - clockImage.getWidth() - 10, 1080 - clockImage.getHeight());


        clockArrowImage.setPosition(clockImage.getX() + (0.255f * clockImage.getWidth()), clockImage.getY() + (0.37f * clockImage.getHeight()));
        clockArrowImage.setOrigin(clockArrowImage.getWidth() / 2, clockArrowImage.getHeight());
        clockArrowImage.setRotation(controller.getClockArrowDegree());

        clockArrowImage.toFront();


    }

    private void displayDayInfo() {

        controller.setDayInfo(dayInfo);
        dayInfo.setPosition(clockImage.getX() + clockImage.getWidth() / 2, clockImage.getY() + clockImage.getHeight() - 35);
        dayInfo.setColor(new Color(0.86f, 0.169f, 0f, 1f));
        dayInfo.toFront();

    }

    private void displayMoneyInfo() {

        controller.setMoneyInfo(moneyInfo);
        moneyInfo.setPosition(clockImage.getX() + clockImage.getWidth() / 2 + 25, clockImage.getY() + 30);
        moneyInfo.setColor(new Color(0.86f, 0.169f, 0f, 1f));
        moneyInfo.toFront();

    }

    private void displayTimeInfo() {

        controller.setTimeInfo(timeInfo);
        timeInfo.setPosition(clockImage.getX() + clockImage.getWidth() / 2, clockImage.getY() + clockImage.getHeight() / 2f - 5);
        timeInfo.setColor(new Color(0.86f, 0.169f, 0f, 1f));
        timeInfo.toFront();

    }

    private void displayInputField() {

        if (tJustPressed) {
            tJustPressed = false;
            textInputField.setText("");
        }

        textInputField.setVisible(isInputFieldVisible);

        TextField.TextFieldStyle style = new TextField.TextFieldStyle(textInputField.getStyle());
        style.fontColor = new Color(1, 1, 1, 1);
        textInputField.setStyle(style);

        textInputField.setColor(0, 0, 0, 0.5f);

        textInputField.setWidth(Gdx.graphics.getWidth());


    }

    private void showErrorMessage() {
        errorLabel.setPosition(Gdx.graphics.getWidth() / 2f - 175, Gdx.graphics.getHeight() - 40);

    }

    private void displayInventoryHotBar() {

        if ( currentMenu == InGameMenuType.NONE ) {
            inventoryHotBarImage.setPosition((Gdx.graphics.getWidth() - inventoryHotBarImage.getWidth()) / 2, 10);
            inventorySelectSlotImage.setPosition(inventoryHotBarImage.getX() + 18 + controller.getSlotPosition(), 26);

            inventoryHotBarImage.setVisible(true);
            inventorySelectSlotImage.setVisible(true);
        }
        else{
            inventoryHotBarImage.setVisible(false);
            inventorySelectSlotImage.setVisible(false);
        }

    }

    private void showHotBarItems() {


        if ( currentMenu == InGameMenuType.NONE ) {



            for ( int i = 0 ; i < Math.min(onScreenItems.size(),12); i++ ){
                onScreenItems.get(i).getItem().getItemImage().setVisible(true);
                onScreenItems.get(i).getItem().getItemImage().setPosition(inventoryHotBarImage.getX() + 18 + controller.getItemPosition(i) + 5, 26 + 5);
                onScreenItems.get(i).getItem().getItemImage().toFront();
            }

            for( int i = 12; i < onScreenItems.size(); i++ ){
                onScreenItems.get(i).getItem().getItemImage().setVisible(false);
            }



        }

    }

    private void displayInventoryMenu() {

        inventoryMenuBackground.setPosition((Gdx.graphics.getWidth() - inventoryMenuBackground.getWidth()) / 2f, (Gdx.graphics.getHeight() - inventoryMenuBackground.getHeight()) / 2f);

        inventoryMenuBackground.setVisible(currentMenu == InGameMenuType.INVENTORY);


        // INVENTORY ITEMS

        if ( currentMenu == InGameMenuType.INVENTORY ) {

            for ( int i = 0; i < Math.min(onScreenItems.size(),12); i++ ){
                onScreenItems.get(i).getItem().getItemImage().setPosition(520 + controller.getItemPosition(i%12), 705);
                onScreenItems.get(i).getItem().getItemImage().setVisible(true);
                onScreenItems.get(i).getItem().getItemImage().toFront();
            }

            for ( int i = 12; i < onScreenItems.size(); i++ ){

                onScreenItems.get(i).getItem().getItemImage().setVisible(false);

            }

            int k = 12;

            for ( int i = 12 * rowCoEfficient; i < Math.min(12 * (rowCoEfficient+2),onScreenItems.size()); i++){
                onScreenItems.get(i).getItem().getItemImage().setVisible(true);
                onScreenItems.get(i).getItem().getItemImage().setPosition(520 + controller.getItemPosition(k%12), 705 - (float)((80-((k/12)-1)*5) * (k/12)));
                onScreenItems.get(i).getItem().getItemImage().toFront();
                k++;
            }


        }

        // DISPLAYING RED BOX

        if ( currentMenu == InGameMenuType.INVENTORY) {

            if (currentSlotInInventory != null) {
                inventorySelectSlotImage.setVisible(true);
                inventorySelectSlotImage.setSize(63,60);
                if ( currentSlotInInventory/12 == 0 ){
                    inventorySelectSlotImage.setPosition((520 + (currentSlotInInventory%12)*63), 700);
                }
                else if ( currentSlotInInventory/12 == 1 ){
                    inventorySelectSlotImage.setPosition((520 + (currentSlotInInventory%12)*63), 620);
                }
                else if ( currentSlotInInventory/12 == 2 ){
                    inventorySelectSlotImage.setPosition((520 + (currentSlotInInventory%12)*63), 550);
                }
                inventorySelectSlotImage.toFront();
            }
            else{
                inventorySelectSlotImage.setVisible(false);
            }


        }




    }

    private void displaySkillMenu() {

        skillMenuBackground.setPosition((Gdx.graphics.getWidth() - skillMenuBackground.getWidth()) / 2f, (Gdx.graphics.getHeight() - skillMenuBackground.getHeight()) / 2f);
        skillMenuBackground.setVisible(currentMenu == InGameMenuType.SKILL);

        for( int i = 0; i < Math.min(3,player.getAbility(AbilityType.Farming).getLevel()); i++ ){
            skillPoints.get(i).setVisible(currentMenu == InGameMenuType.SKILL);
        }

        for( int i = 0; i < Math.min(3,player.getAbility(AbilityType.Mining).getLevel()); i++ ){
            skillPoints.get(i+4).setVisible(currentMenu == InGameMenuType.SKILL);
        }

        for( int i = 0; i < Math.min(3,player.getAbility(AbilityType.Foraging).getLevel()); i++ ){
            skillPoints.get(i+8).setVisible(currentMenu == InGameMenuType.SKILL);
        }

        for( int i = 0; i < Math.min(3,player.getAbility(AbilityType.Fishing).getLevel()); i++ ){
            skillPoints.get(i+12).setVisible(currentMenu == InGameMenuType.SKILL);
        }


    }

    private void displaySocialMenu(){

        socialMenuBackground.setPosition((Gdx.graphics.getWidth() - socialMenuBackground.getWidth()) / 2f, (Gdx.graphics.getHeight() - socialMenuBackground.getHeight()) / 2f);
        socialMenuBackground.setVisible(currentMenu == InGameMenuType.SOCIAL);

        for ( int i = 0; i < 5; i++ ){

            npcInfos.get(i).setText(controller.getNPCInfo(NPCType.values()[i]));

            npcAvatars.get(i).setVisible(currentMenu == InGameMenuType.SOCIAL);
            npcLabels.get(i).setVisible(currentMenu == InGameMenuType.SOCIAL);
            npcInfos.get(i).setVisible(currentMenu == InGameMenuType.SOCIAL);
            checkBoxes.get(2*i).setVisible(currentMenu == InGameMenuType.SOCIAL &&
                    controller.gotGiftedToday(NPCType.values()[i]) );

            checkBoxes.get(2*i + 1).setVisible(currentMenu == InGameMenuType.SOCIAL &&
                    controller.metToday(NPCType.values()[i]) );

        }



    }

    private void displayCraftingMenu() {

        //  BACKGROUND
        craftingMenuBackground.setPosition((Gdx.graphics.getWidth() - craftingMenuBackground.getWidth()) / 2f, (Gdx.graphics.getHeight() - craftingMenuBackground.getHeight()) / 2f);
        craftingMenuBackground.setVisible(currentMenu == InGameMenuType.CRAFTING);

        //  ITEMS
        for (CraftingProduct craftingProduct : craftingProducts.keySet()) {

            ImageButton imageButton = craftingProducts.get(craftingProduct);

            imageButton.setColor(
                    imageButton.getColor().r,
                    imageButton.getColor().g,
                    imageButton.getColor().b,
                    0.3f + ((ClientApp.getCurrentGame().getCurrentPlayer().hasEnoughIngredients(craftingProduct.getRecipe())) ? 0.7f : 0f)
            );

            if (currentMenu == InGameMenuType.CRAFTING) {
                imageButton.setVisible(
                        ClientApp.getCurrentGame().getCurrentPlayer().getAvailableCraftingRecipes().contains(craftingProduct.getRecipe())
                );
            } else {
                imageButton.setVisible(false);
            }

        }

        // INVENTORY ITEMS

        if ( currentMenu == InGameMenuType.CRAFTING ) {

            for ( int i = 0; i < Math.min(onScreenItems.size(),36); i++ ){

                onScreenItems.get(i).getItem().getItemImage().setPosition(520 + controller.getItemPosition(i%12), 385 - (float)(70 * (i/12)) );
                onScreenItems.get(i).getItem().getItemImage().setVisible(true);
                onScreenItems.get(i).getItem().getItemImage().toFront();

            }

        }

    }

    private void displayExitMenu() {

        exitMenuBackground.setPosition((Gdx.graphics.getWidth() - exitMenuBackground.getWidth()) / 2f, (Gdx.graphics.getHeight() - exitMenuBackground.getHeight()) / 2f);

        exitMenuBackground.setVisible(currentMenu == InGameMenuType.EXIT);
        exitToMainButton.setVisible(currentMenu == InGameMenuType.EXIT);
        exitGameButton.setVisible(currentMenu == InGameMenuType.EXIT);
        playerSelectBox.setVisible(currentMenu == InGameMenuType.EXIT &&
                Objects.equals(player.getUsername(), ClientApp.getCurrentGame().getAdmin().getUsername()));
        kickPlayerButton.setVisible(currentMenu == InGameMenuType.EXIT &&
                Objects.equals(player.getUsername(), ClientApp.getCurrentGame().getAdmin().getUsername()));

        exitGameButton.toFront();
        kickPlayerButton.toFront();
        exitToMainButton.toFront();
        playerSelectBox.toFront();

    }

    private void updateOnScreenItems() {

        int commonPrefix = Math.min(onScreenItems.size(), inventoryItems.getItems().size());
        ArrayList<Stacks> removableStacks = new ArrayList<>();
        ArrayList<Stacks> addableStack = new ArrayList<>();

        for (int i = commonPrefix; i < onScreenItems.size(); i++) {
            removableStacks.add(onScreenItems.get(i));
        }

        for (int i = 0; i < commonPrefix; i++) {
            if (!Stacks.compare(onScreenItems.get(i), inventoryItems.get(i))) {
                removableStacks.add(onScreenItems.get(i));
                addableStack.add(Stacks.copy(inventoryItems.get(i)));
            }
        }

        for (int i = commonPrefix; i < inventoryItems.getItems().size(); i++) {
            addableStack.add(Stacks.copy(inventoryItems.get(i)));
        }

        for (Stacks removableStack : removableStacks) {
            removeFromScreen(removableStack);
        }

        for ( Stacks stack : addableStack ) {
            addToScreen(stack);
        }

    }

    private void displayItemQuantity(){

        for ( Stacks stacks: onScreenItems ){
            Label label = onScreenItemsQuantity.get(stacks);
            if ( stacks.getItem().getItemImage().isVisible() ){
                label.setVisible(true);
                label.setPosition(stacks.getItem().getItemImage().getX()+stacks.getItem().getItemImage().getWidth()-15,
                        stacks.getItem().getItemImage().getY()+stacks.getItem().getItemImage().getHeight()-25);
                label.toFront();
            }
            else{
                label.setVisible(false);
            }

        }

    }

    private void displayCookingMenu(){

        // BACKGROUND
        cookingMenuBackground.setVisible(currentMenu == InGameMenuType.COOKING);

        //  ITEMS
        for (CookingProduct cookingProduct : cookingProducts.keySet()) {

            ImageButton imageButton = cookingProducts.get(cookingProduct);

            imageButton.setColor(
                    imageButton.getColor().r,
                    imageButton.getColor().g,
                    imageButton.getColor().b,
                    0.3f + ((ClientApp.getCurrentGame().getCurrentPlayer().hasEnoughIngredients(cookingProduct.getRecipe())) ? 0.7f : 0f)
            );

            if (currentMenu == InGameMenuType.COOKING) {
                imageButton.setVisible(
                        ClientApp.getCurrentGame().getCurrentPlayer().getAvailableCookingRecipes().contains(cookingProduct.getRecipe())
                );
            } else {
                imageButton.setVisible(false);
            }

        }

        // INVENTORY ITEMS

        if ( currentMenu == InGameMenuType.COOKING ) {

            for ( int i = 0; i < Math.min(onScreenItems.size(),36); i++ ){

                onScreenItems.get(i).getItem().getItemImage().setPosition(520 + controller.getItemPosition(i%12), 420 - (float)(70 * (i/12)) );
                onScreenItems.get(i).getItem().getItemImage().setVisible(true);
                onScreenItems.get(i).getItem().getItemImage().toFront();

            }

        }


    }

    private void applyNightEffect(){
        blackImage.setColor(0,0,0,(ClientApp.getCurrentGame().getTime().getHour()>18)? 0.5f:0);
    }

    private void displayHoveringItemInfo(){

        if ( currentStacksHover != null ){
            craftingProductNameLabel.setText(currentStacksHover.getName());

            if (currentStacksHover instanceof CraftingProduct product){
                Recipe recipe = product.getRecipe();
                craftingProductIngredientsLabel.setText(recipe.getInfo());
            }else if (currentStacksHover instanceof CookingProduct product){
                Recipe recipe = product.getRecipe();
                craftingProductIngredientsLabel.setText(recipe.getInfo());
            }


        }

        hoveringInfoWindow.setVisible(currentStacksHover != null);
        craftingProductNameLabel.setVisible(currentStacksHover != null);
        craftingProductIngredientsLabel.setVisible(currentStacksHover != null);


    }

    private void displaySkillInfo(){

        farmingHoverImage.setVisible(false);
        miningHoverImage.setVisible(false);
        foragingHoverImage.setVisible(false);
        fishingHoverImage.setVisible(false);

        farmingHoverImage.toFront();
        miningHoverImage.toFront();
        foragingHoverImage.toFront();
        fishingHoverImage.toFront();

        if ( currentMenu == InGameMenuType.SKILL ){

            double mouseX = Gdx.input.getX();
            double mouseY = 1080 - Gdx.input.getY();


            if ( (690< mouseX && mouseX < 850 ) && ( 715 < mouseY && mouseY < 760 ) ){
                currentAbilityTypeHovering = AbilityType.Farming;
            }
            else if ( (716< mouseX && mouseX < 854 ) && ( 634 < mouseY && mouseY < 666 ) ){
                currentAbilityTypeHovering = AbilityType.Mining;
            }
            else if ( (686< mouseX && mouseX < 854 ) && ( 554 < mouseY && mouseY < 581 ) ){
                currentAbilityTypeHovering = AbilityType.Foraging;
            }
            else if ( (708< mouseX && mouseX < 856 ) && ( 459 < mouseY && mouseY < 497 ) ){
                currentAbilityTypeHovering = AbilityType.Fishing;
            }
            else{
                currentAbilityTypeHovering = null;
            }

            if ( currentAbilityTypeHovering != null ){

                if ( currentAbilityTypeHovering == AbilityType.Farming ){
                    farmingHoverImage.setVisible(true);
                    farmingHoverImage.setPosition((float)  mouseX,(float) mouseY);
                }
                else if ( currentAbilityTypeHovering == AbilityType.Mining ){
                    miningHoverImage.setVisible(true);
                    miningHoverImage.setPosition((float)  mouseX,(float) mouseY);
                }
                else if ( currentAbilityTypeHovering == AbilityType.Fishing ){
                    fishingHoverImage.setVisible(true);
                    fishingHoverImage.setPosition((float)  mouseX,(float) mouseY);
                }
                else {
                    foragingHoverImage.setVisible(true);
                    foragingHoverImage.setPosition((float)  mouseX,(float) mouseY);
                }

            }


        }


    }

    private void displayPlayerSocial(){

        playerSocialMenuBackground.setVisible(currentMenu == InGameMenuType.PLAYER_SOCIAL);

        for ( int i = 0; i < Math.min(4, ClientApp.getCurrentGame().getPlayers().size()) - 1; i++ ){

            friendsLabels.get(i).setVisible(currentMenu == InGameMenuType.PLAYER_SOCIAL);
            friendshipInfos.get(i).setVisible(currentMenu == InGameMenuType.PLAYER_SOCIAL);
            friendButtons.get(2 * i).setVisible(currentMenu == InGameMenuType.PLAYER_SOCIAL);
            friendButtons.get(2 * i + 1).setVisible(currentMenu == InGameMenuType.PLAYER_SOCIAL);
            friendsLabels.get(i).toFront();
            friendshipInfos.get(i).toFront();

        }

    }

    private void displayMapMenu(){

        mapMenuBackground.setVisible(currentMenu == InGameMenuType.MAP);

    }

    private void displayEnergy(){

        boostBar.setVisible(player.getBoostEnergy() != 0);
        redBar.setVisible(player.getBoostEnergy() != 0);
        greenBar.setVisible(player.getEnergy() != 0);
        greenBar.setColor(1 - ((float) player.getEnergy() / player.getMaxEnergy()),((float) player.getEnergy() / player.getMaxEnergy()),1,1);

        energyBar.setPosition(Gdx.graphics.getWidth()-energyBar.getWidth()-20,20);
        boostBar.setPosition(Gdx.graphics.getWidth()-boostBar.getWidth()-20,20 + boostBar.getHeight() + 20);
        greenBar.setHeight(250 * ((float) player.getEnergy() / player.getMaxEnergy()));
        greenBar.setPosition(energyBar.getX()+11,energyBar.getY()+9);
        redBar.setHeight(250 * ((float) player.getBoostEnergy() /100));
        redBar.setPosition(boostBar.getX()+11,boostBar.getY()+9);

        energyBar.toFront();
        boostBar.toFront();
        redBar.toFront();
        greenBar.toFront();


    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
    }

    public void displayHUD(float delta) {

        errorLabel.update(delta);
        inventoryItems = player.getBackpack();
        updateOnScreenItems();

        applyNightEffect();
        displayClock();
        displayInventoryHotBar();
        showErrorMessage();
        showHotBarItems();
        displayDayInfo();
        displayMoneyInfo();
        displayTimeInfo();
        displayInventoryMenu();
        displaySkillMenu();
        displaySocialMenu();
        displayCraftingMenu();
        displayExitMenu();
        displayMapMenu();
        displayCookingMenu();
        displayItemQuantity();
        displayHoveringItemInfo();
        displaySkillInfo();
        displayPlayerSocial();
        displayEnergy();
        displayInputField();

    }

    public void displayOnlyClock() {
        displayClock();
        displayDayInfo();
        displayMoneyInfo();
        displayTimeInfo();
        displayEnergy();
        makeOnScreenItemsInvisible();
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

    private void setListeners() {

        stage.addListener(new InputListener() {


            @Override
            public boolean keyDown(InputEvent event, int keycode) {

                if (!isInputFieldVisible) {

                    if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT)) {
                        ctrlPressed = true;
                    }

                    if (keycode == Input.Keys.T) {
                        playClickSound();
                        isInputFieldVisible = true;
                        textInputField.setText("");
                        tJustPressed = true;
                        return true;
                    }
                    else if (keycode == Input.Keys.E) {

                        if (currentMenu != InGameMenuType.INVENTORY ) {
                            currentMenu = InGameMenuType.INVENTORY;
                        } else {
                            currentMenu = InGameMenuType.NONE;
                        }

                    }
                    else if (keycode == Input.Keys.ESCAPE) {

                        if (currentMenu == InGameMenuType.EXIT) {
                            currentMenu = InGameMenuType.NONE;
                        }
                        else {
                            currentMenu = InGameMenuType.EXIT;
                            makeOnScreenItemsInvisible();
                        }

                    }
                    else if (keycode == Input.Keys.V) {

                        if ( currentMenu == InGameMenuType.SKILL ) {
                            currentMenu = InGameMenuType.NONE;
                        }
                        else{
                            currentMenu = InGameMenuType.SKILL;
                            makeOnScreenItemsInvisible();
                        }

                    }
                    else if (keycode == Input.Keys.N) {

                        if ( currentMenu == InGameMenuType.MAP ) {
                            currentMenu = InGameMenuType.NONE;
                        }
                        else{
                            currentMenu = InGameMenuType.MAP;
                            makeOnScreenItemsInvisible();
                        }

                    }
                    else if (keycode == Input.Keys.B) {

                        if ( currentMenu == InGameMenuType.SOCIAL ) {
                            currentMenu = InGameMenuType.NONE;
                        }
                        else{
                            currentMenu = InGameMenuType.SOCIAL;
                            makeOnScreenItemsInvisible();
                        }

                    }
                    else if (keycode == Input.Keys.Z) {

                        if ( currentMenu == InGameMenuType.PLAYER_SOCIAL ) {
                            currentMenu = InGameMenuType.NONE;
                        }
                        else{
                            currentMenu = InGameMenuType.PLAYER_SOCIAL;
                            makeOnScreenItemsInvisible();
                        }

                    }else if (keycode == Input.Keys.M) {

                        if (currentMenu == InGameMenuType.CRAFTING) {
                            currentMenu = InGameMenuType.NONE;
                        }
                        else {
                            currentMenu = InGameMenuType.CRAFTING;
                            makeOnScreenItemsInvisible();
                        }

                    } else if ( keycode == Input.Keys.C ) {

                        if ( currentMenu == InGameMenuType.COOKING ) {
                            currentMenu = InGameMenuType.NONE;
                        }
                        else{
                            currentMenu = InGameMenuType.COOKING;
                            makeOnScreenItemsInvisible();
                        }

                    }

                    if ( currentMenu == InGameMenuType.INVENTORY ) {

                        if ( keycode == Input.Keys.D ) {

                            if ( currentSlotInInventory != null ) {

                                int itemNumber = (currentSlotInInventory<12)? currentSlotInInventory:(currentSlotInInventory + (rowCoEfficient-1) * 12);

                                if ( itemNumber >= onScreenItems.size() ){
                                    errorLabel.set(new GraphicalResult("Selected slot is empty!"));
                                }
                                else{
                                    ///  TODO: RASSA MOVE KON TO TRASH CAN
                                    errorLabel.set(controller.removeFromInventory(onScreenItems.get(itemNumber)));
                                }
                            }

                        }

                    }

                    else if ( currentMenu == InGameMenuType.NONE ) {
                        if (keycode == Input.Keys.NUM_1) {

                            controller.quickSetHotBarIndex(0);

                        } else if (keycode == Input.Keys.NUM_2) {

                            controller.quickSetHotBarIndex(1);

                        } else if (keycode == Input.Keys.NUM_3) {

                            controller.quickSetHotBarIndex(2);

                        } else if (keycode == Input.Keys.NUM_4) {

                            controller.quickSetHotBarIndex(3);

                        } else if (keycode == Input.Keys.NUM_5) {

                            controller.quickSetHotBarIndex(4);

                        } else if (keycode == Input.Keys.NUM_6) {

                            controller.quickSetHotBarIndex(5);

                        } else if (keycode == Input.Keys.NUM_7) {

                            controller.quickSetHotBarIndex(6);

                        } else if (keycode == Input.Keys.NUM_8) {

                            controller.quickSetHotBarIndex(7);

                        } else if (keycode == Input.Keys.NUM_9) {

                            controller.quickSetHotBarIndex(8);

                        } else if (keycode == Input.Keys.NUM_0) {

                            controller.quickSetHotBarIndex(9);

                        } else if (keycode == Input.Keys.MINUS) {

                            controller.quickSetHotBarIndex(10);

                        } else if (keycode == Input.Keys.EQUALS) {

                            controller.quickSetHotBarIndex(11);

                        }
                    }


                }
                else {

                    if ( keycode == Input.Keys.TAB ) {

                        controller.handleTabPressInTextInput();

                    }
                    else if (keycode == Input.Keys.ENTER) {
                        playClickSound();
                        errorLabel.set(controller.handleTextInput());
                        return true;
                    } else if (keycode == Input.Keys.ESCAPE) {
                        playClickSound();
                        controller.closeTextInputField();
                        return true;
                    }

                }
                return false;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.CONTROL_LEFT || keycode == Input.Keys.CONTROL_RIGHT) {
                    ctrlPressed = false;
                }
                return false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (!isInputFieldVisible) {

                    if ( (525 < x && x < 580) && ( 800 < y && y < 860 )){
                        currentMenu = InGameMenuType.INVENTORY;
                        return true;
                    }
                    else if ( (590 < x && x < 645) && ( 800 < y && y < 860 )){
                        currentMenu = InGameMenuType.SKILL;
                        makeOnScreenItemsInvisible();
                        return true;
                    }
                    else if ( (650 < x && x < 715) && ( 800 < y && y < 860 )){
                        currentMenu = InGameMenuType.SOCIAL;
                        makeOnScreenItemsInvisible();
                        return true;
                    }
                    else if ( (715 < x && x < 780) && ( 800 < y && y < 860 )){
                        currentMenu = InGameMenuType.MAP;
                        makeOnScreenItemsInvisible();
                        return true;
                    }
                    else if ( (785 < x && x < 836) && ( 800 < y && y < 860 )){
                        currentMenu = InGameMenuType.CRAFTING;
                        makeOnScreenItemsInvisible();
                        return true;
                    }
                    else if ( (850 < x && x < 900) && ( 800 < y && y < 860 )){
                        currentMenu = InGameMenuType.EXIT;
                        makeOnScreenItemsInvisible();
                        return true;
                    }

//                    System.out.println(x+" "+y);

                    for ( int i = 0; i < 12; i++ ){

                        if ( (520 + i*63) < x && x < (520 + (i+1)*63) ){

                            if ( !ctrlPressed ){
                                if ( 700 < y && y < 760 ){
                                    if ( currentSlotInInventory == null ){
                                        currentSlotInInventory = i;
                                    }
                                    else{
                                        if ( currentSlotInInventory != i ){
                                            currentSlotInInventory = i;
                                        }
                                        else{
                                            currentSlotInInventory = null;
                                        }
                                    }
                                    return true;
                                }
                                else if ( 620 < y && y < 680 ){
                                    if ( currentSlotInInventory == null ){
                                        currentSlotInInventory = i+12;
                                    }
                                    else{
                                        if ( currentSlotInInventory != (i+12) ){
                                            currentSlotInInventory = i+12;
                                        }
                                        else{
                                            currentSlotInInventory = null;
                                        }
                                    }
                                    return true;
                                }
                                else if ( 550 < y && y < 610 ){
                                    if ( currentSlotInInventory == null ){
                                        currentSlotInInventory = i+24;
                                    }
                                    else{
                                        if ( currentSlotInInventory != (i+24) ){
                                            currentSlotInInventory = i+24;
                                        }
                                        else{
                                            currentSlotInInventory = null;
                                        }
                                    }
                                    return true;
                                }
                            }
                            else{

                                if ( currentSlotInInventory != null ){
                                    int currentItemIndex = (currentSlotInInventory<12)? currentSlotInInventory:currentSlotInInventory+12*(rowCoEfficient-1);

                                    if ( currentItemIndex >= onScreenItems.size() ){
                                        errorLabel.set(new GraphicalResult("First item was empty"));
                                        return true;
                                    }

                                    if ( 700 < y && y < 760 ){

                                        if ( i >= onScreenItems.size() ){
                                            errorLabel.set(new GraphicalResult("You can't place your item here"));
                                            return true;
                                        }

                                        inventoryItems.switchItem(currentItemIndex, i);
                                        currentSlotInInventory = null;
                                        return true;
                                    }
                                    else if ( 620 < y && y < 680 ){

                                        if ( (i+12*rowCoEfficient) >= onScreenItems.size() ){
                                            errorLabel.set(new GraphicalResult("You can't place your item here"));
                                            return true;
                                        }

                                        inventoryItems.switchItem(currentItemIndex, i+12*rowCoEfficient);
                                        currentSlotInInventory = null;
                                        return true;
                                    }
                                    else if ( 550 < y && y < 610 ){

                                        if ( (i+12*(rowCoEfficient+1)) >= onScreenItems.size() ){
                                            errorLabel.set(new GraphicalResult("You can't place your item here"));
                                            return true;
                                        }

                                        inventoryItems.switchItem(currentItemIndex, i+12*(rowCoEfficient+1));
                                        currentSlotInInventory = null;
                                        return true;
                                    }
                                }


                            }

                        }

                    }


                }

                return false;
            }

            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {

                if (!isInputFieldVisible && currentMenu == InGameMenuType.NONE) {

                    if (amountY > 0) {
                        controller.updateSlotIndex(-1);
                    } else if (amountY < 0) {
                        controller.updateSlotIndex(1);
                    }
                    return true;

                }
                else if ( currentMenu == InGameMenuType.INVENTORY ) {
                    if ( (495 < x && x < 1300) && (540 < y && y < 780) ){
                        if (amountY > 0 && (rowCoEfficient+2)*12 < onScreenItems.size()) {
                            rowCoEfficient += 1;
                            if ( currentSlotInInventory != null ){
                                if ( 12 <= currentSlotInInventory && currentSlotInInventory < 24 ){
                                    currentSlotInInventory = null;
                                }
                                else if ( 24 <= currentSlotInInventory ){
                                    currentSlotInInventory -= 12;
                                }
                            }
                        }
                        else if ( amountY < 0 ){
                            rowCoEfficient = Math.max(1, rowCoEfficient-1);
                            if ( currentSlotInInventory != null){
                                if ( 24 <= currentSlotInInventory ){
                                    currentSlotInInventory = null;
                                }
                                else if (12 <= currentSlotInInventory){
                                    currentSlotInInventory += 12;
                                }
                            }

                        }
                    }
                    return true;
                }
                return false;

            }


        });

        socialButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if ( currentMenu == InGameMenuType.PLAYER_SOCIAL ) {
                    currentMenu = InGameMenuType.NONE;
                }
                else{
                    currentMenu = InGameMenuType.PLAYER_SOCIAL;
                    makeOnScreenItemsInvisible();
                }
            }

        });

        for (CraftingProduct craftingProduct: craftingProducts.keySet()) {
            ImageButton imageButton = craftingProducts.get(craftingProduct);
            imageButton.addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    super.enter(event, x, y, pointer, fromActor);
                    currentStacksHover = craftingProduct;
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    super.exit(event, x, y, pointer, toActor);
                    currentStacksHover = null;
                }
            });

        }

        for (Map.Entry<CraftingProduct, ImageButton> entry : craftingProducts.entrySet()) {
            ImageButton imageButton = entry.getValue();
            imageButton.addListener(new ClickListener() {
               @Override
               public void clicked(InputEvent event, float x, float y) {
                   playClickSound();
                   if (imageButton.getColor().a > 0.3f) {   // not enough ingredient
                       errorLabel.set(controller.craft(entry.getKey()));
                   }
                   else {
                       errorLabel.set(new GraphicalResult("You don't have enough ingredient!"));
                   }
               }
            });
        }

        for (Map.Entry<CookingProduct, ImageButton> entry : cookingProducts.entrySet()) {
            ImageButton imageButton = entry.getValue();
            imageButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    playClickSound();
                    if (imageButton.getColor().a > 0.3f) {
                        errorLabel.set(controller.cook(entry.getKey()));
                    }
                    else {
                        errorLabel.set(new GraphicalResult("You don't have enough ingredient!"));
                    }
                }
            });
        }

        for ( CookingProduct cookingProduct: cookingProducts.keySet()  ){

            ImageButton imageButton = cookingProducts.get(cookingProduct);
            imageButton.addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    super.enter(event, x, y, pointer, fromActor);
                    currentStacksHover = cookingProduct;
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    super.exit(event, x, y, pointer, toActor);
                    currentStacksHover = null;
                }
            });

        }

//        int counter = 0;
//        for ( int i = 0; i < Math.min(4, ClientApp.getCurrentGame().getPlayers().size()); i++ ){
//
//
//            if (!Objects.equals(ClientApp.getCurrentGame().getPlayers().get(i).getUsername(),
//                    player.getUsername())){
//
//
////                int finalI = i;
////                friendButtons.get(2*i + 1).addListener(new ClickListener() {
////                    @Override
////                    public void clicked(InputEvent event, float x, float y) {
////                        playClickSound();
////                        controller.openTradeMenu(finalI);
////                    }
////
////                });
//
//                counter ++;
//
//
//            }
//
//        }


        int counter = 0;
        for ( MiniPlayer inGamePlayer: ClientApp.getCurrentGame().getPlayers() ){

            if ( !player.getUsername().equals(inGamePlayer.getUsername()) ){
                friendButtons.get(2*counter + 1).addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        playClickSound();
                        controller.openTradeMenu2(inGamePlayer.getUsername());
                    }

                });
                counter ++;
            }


        }

    }


    public void setInputFieldVisible(boolean inputFieldVisible) {
        isInputFieldVisible = inputFieldVisible;
    }

    public void setClockImage(Image clockImage) {
        this.clockImage = clockImage;
        stage.addActor(clockImage);
    }

    public TextField getTextInputField() {
        return textInputField;
    }

    @Override
    public void executeCommands(Scanner scanner) {

    }

    public boolean isInputFieldVisible() {
        return isInputFieldVisible;
    }

    private void removeFromScreen(Stacks stack) {

        onScreenItemsQuantity.get(stack).remove();
        onScreenItemsQuantity.remove(stack);

        stack.getItem().getItemImage().remove();
        onScreenItems.remove(stack);

    }

    private void addToScreen(Stacks stack) {

        Label quantityLabel = new Label(Integer.toString(stack.getQuantity()),skin);
        quantityLabel.setVisible(false);
        quantityLabel.setColor(Color.RED);
        quantityLabel.setFontScale(0.7f);
        stage.addActor(quantityLabel);
        onScreenItemsQuantity.put(stack,quantityLabel);

        stack.getItem().getItemImage().setSize(48,48);
        stack.getItem().getItemImage().setVisible(false);
        stage.addActor(stack.getItem().getItemImage());
        onScreenItems.add(stack);



    }

    private void makeOnScreenItemsInvisible(){

        for (Stacks onScreenItem : onScreenItems) {
            onScreenItem.getItem().getItemImage().setVisible(false);
        }

    }

    public InGameMenuType getCurrentMenu() {
        return currentMenu;
    }

    public HUDController getController() {
        return controller;
    }

}
