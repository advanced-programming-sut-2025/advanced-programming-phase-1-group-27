package org.example.client.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import org.example.client.controller.*;
import org.example.client.controller.InteractionsWithOthers.InteractionsWithNPCController;
import org.example.client.controller.InteractionsWithOthers.InteractionsWithUserController;
import org.example.client.model.ClientApp;
import org.example.client.model.MiniPlayer;
import org.example.client.model.PopUpTexture;
import org.example.client.model.enums.Emoji;
import org.example.common.models.GameAssetManager;
import org.example.common.models.GraphicalResult;
import org.example.server.models.*;
import org.example.server.models.AnimalProperty.Animal;
import org.example.server.models.AnimalProperty.AnimalEnclosure;
import org.example.server.models.NPCs.Quest;
import org.example.server.models.Relations.Relation;
import org.example.server.models.enums.AbilityType;
import org.example.server.models.enums.InGameMenuType;
import org.example.server.models.enums.NPCType;
import org.example.server.models.enums.items.Recipe;
import org.example.server.models.enums.items.ShopItems;
import org.example.server.models.enums.items.products.CookingProduct;
import org.example.server.models.enums.items.products.CraftingProduct;
import org.example.server.models.tools.Backpack;

import java.util.*;

import static java.lang.Math.max;

public class HUDView extends AppMenu {

    private final HUDController controller;

    private final Stage stage;

    private final Player player;

    private final ArtisanController artisanController;
    private final FridgeController fridgeController;
    private final ShippingBinController shippingBinController;

    private final Label dayInfo;
    private final Label moneyInfo;
    private final Label timeInfo;
    private final Label craftingProductNameLabel;
    private final Label craftingProductIngredientsLabel;
    private final Label messageLabel;
    private ArrayList<Label> enclosureLabels = new ArrayList<>();
    private Label animalInfoLabel;
    private final Label songNameLabel;
    private final Label listenTogetherLabel;
    private final Label leaderBoardUsernameLabel;
    private final Label leaderBoardEarningsLabel;
    private final Label leaderBoardSkillsLabel;
    private final Label leaderBoardNumberOfQuestsLabel;
    private final Label sortByLabel;
    private final Label requestLabel;
    private final Label rewardLabel;
    private final Label journalLabel;

    private ArrayList<Label> questLabels = new ArrayList<>();

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
    private final Image enclosureBackground;
    private final Image animalBackground;
    private final Image radioBackgroundImage;
    private final Image mapImage;
    private final Image reactionMenuBackground;
    private final Image leaderBoardMenuBackground;
    private final Image journalMenuBackground;
    private Image clockImage;
    private Image buffImage;

    private final ImageButton socialButton;
    private final ImageButton messageAlertImage;
    private final ImageButton messageBackgroundImage;


    private final ArrayList<Image> skillPoints;
    private final ArrayList<Image> npcAvatars;
    private final ArrayList<Image> checkBoxes;
    private final HashMap<MiniPlayer,Image> playerIconsInMap;

    private final HashMap<CraftingProduct, ImageButton> craftingProducts;
    private final HashMap<CookingProduct, ImageButton> cookingProducts;
    private HashMap<Stacks,Label> onScreenItemsQuantity;

    private final TextField textInputField;
    private final TextField reactionTextField;

    private final GraphicalResult errorLabel;

    private boolean isInputFieldVisible;
    private boolean tJustPressed;
    private boolean ctrlPressed;
    private boolean yourSongsPage;
    private boolean readingMessage;
    private boolean reactionTyping;

    private Backpack inventoryItems;

    private ArrayList<Stacks> onScreenItems;

    private Integer rowCoEfficient;
    private Integer currentSlotInInventory;

    private InGameMenuType currentMenu;

    private Item currentStacksHover;

    private AnimalEnclosure animalEnclosure;
    private Animal animal;

    private float miniPlayerUpdateTimer = 0f;

    private final TextButton exitGameButton;
    private final TextButton terminateButton;
    private final TextButton kickPlayerButton;
    private final TextButton playButton;
    private final TextButton listenButton;
    private final TextButton nextPageButton;
    private final TextButton previousPageButton;
    private final TextButton uploadSongButton;
    private final TextButton enclosureMenuExitButton;
    private final TextButton animalSellButton;
    private final TextButton animalFeedButton;
    private final TextButton animalPetButton;
    private final TextButton animalCollectButton;
    private final TextButton animalExitButton;
    private final TextButton earningsSortButton;
    private final TextButton numberOfQuestsSortButton;
    private final TextButton skillSortButton;


    private final TextButton randomizeEmojis;
    private final TextButton sendReaction;

    private final SelectBox<String> playerSelectBox;

    private AbilityType currentAbilityTypeHovering;

    private final ArrayList<Label> npcLabels;
    private final ArrayList<Label> npcInfos;

    private final ArrayList<Label> friendsLabels;
    private final ArrayList<Label> friendshipInfos;
    private final ArrayList<TextButton> friendButtons;
    private ArrayList<TextButton> enclosureButtons = new ArrayList<>();


    private final SelectBox<String> yourSongsSelectBox;
    private final SelectBox<String> othersSongsSelectBox;

    private ArrayList<String> inbox;

    private ArrayList<Emoji> emojiButtons;

    private ArrayList<MiniPlayer> playersInLeaderBoard;

    private int sortType;       // 0 for earnings, 1 for skills, 2 for quests

    public HUDView(Stage stage) {


        controller = new HUDController(this);

        this.stage = stage;
        sortType = 0;
        artisanController = new ArtisanController(this, stage);
        fridgeController = new FridgeController(this, stage);
        shippingBinController = new ShippingBinController(this, stage);


        playerIconsInMap = new HashMap<>();
        for( MiniPlayer inGamePlayer: ClientApp.getCurrentGame().getPlayers() ){
            Image playerIconImage = new Image(GameAssetManager.getGameAssetManager().getPlayerIcon());
            playerIconImage.setVisible(false);
            playerIconsInMap.put(inGamePlayer,
                    playerIconImage);

            stage.addActor(playerIconImage);
        }

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
        songNameLabel = new Label("Currently listening to: "+"Song name",skin);
        listenTogetherLabel = new Label("Listen together with:",skin);
        leaderBoardUsernameLabel = new Label("Username",skin);
        leaderBoardEarningsLabel = new Label("Earnings",skin);
        leaderBoardSkillsLabel = new Label("Skills",skin);
        leaderBoardNumberOfQuestsLabel = new Label("Number of\n  Quests",skin);
        sortByLabel = new Label("Sort by:",skin);
        journalLabel = new Label("Journal",skin);
        requestLabel = new Label("Request",skin);
        rewardLabel = new Label("Reward",skin);

        requestLabel.setColor(Color.BLACK);
        rewardLabel.setColor(Color.BLACK);
        journalLabel.setColor(Color.BLACK);
        journalLabel.setFontScale(2f);
        leaderBoardUsernameLabel.setColor(Color.BLACK);
        leaderBoardNumberOfQuestsLabel.setColor(Color.BLACK);
        leaderBoardSkillsLabel.setColor(Color.BLACK);
        leaderBoardEarningsLabel.setColor(Color.BLACK);
        sortByLabel.setColor(Color.BLACK);

        journalLabel.setVisible(false);
        requestLabel.setVisible(false);
        rewardLabel.setVisible(false);
        leaderBoardUsernameLabel.setVisible(false);
        leaderBoardNumberOfQuestsLabel.setVisible(false);
        leaderBoardSkillsLabel.setVisible(false);
        leaderBoardEarningsLabel.setVisible(false);
        sortByLabel.setVisible(false);



        playerSelectBox = new SelectBox<>(skin);

        Array<String> playersUsername = new Array<>();
        ClientApp.getCurrentGame().getPlayers().forEach(
                miniPlayer -> playersUsername.add(miniPlayer.getUsername())
        );
        playerSelectBox.setItems(playersUsername);
        playerSelectBox.setWidth(300);
        playerSelectBox.setPosition(600,380);
        playerSelectBox.setVisible(false);

        exitGameButton = new TextButton("Save and Exit",skin);
        terminateButton = new TextButton("Terminate Game",skin);
        kickPlayerButton = new TextButton("Kick",skin);
        playButton = new TextButton("Play",skin);
        listenButton = new TextButton("Listen", skin);
        nextPageButton = new TextButton(">",skin);
        previousPageButton = new TextButton("<",skin);
        uploadSongButton = new TextButton("Upload",skin);
        randomizeEmojis = new TextButton("New Emojis",skin);
        sendReaction = new TextButton("Send!",skin);
        earningsSortButton = new TextButton("Earnings",skin);
        numberOfQuestsSortButton = new TextButton("Number Of Quests",skin);
        skillSortButton = new TextButton("Skills",skin);

        earningsSortButton.setVisible(false);
        numberOfQuestsSortButton.setVisible(false);
        skillSortButton.setVisible(false);

        reactionTyping = false;
        reactionTextField = new TextField("", skin);

        randomizeEmojis.setVisible(false);
        sendReaction.setVisible(false);
        reactionTextField.setVisible(false);

        kickPlayerButton.setPosition(950,380-(kickPlayerButton.getHeight()-playerSelectBox.getHeight())/2f);
        kickPlayerButton.setVisible(false);

        enclosureMenuExitButton = new TextButton("Close",skin);
        enclosureMenuExitButton.setWidth(500);
        enclosureMenuExitButton.setPosition((Gdx.graphics.getWidth() - enclosureMenuExitButton.getWidth()) / 2f,
                100);
        enclosureMenuExitButton.setVisible(false);

        buffImage = new Image();
        buffImage.setPosition(1800, 100);

        animalExitButton = new TextButton("Close",skin);
        animalExitButton.setWidth(500);
        animalExitButton.setPosition((Gdx.graphics.getWidth() - animalExitButton.getWidth()) / 2f,
                100);
        animalExitButton.setVisible(false);

        animalSellButton = new TextButton("Sell",skin);
        animalSellButton.setWidth(600);
        animalSellButton.setPosition(Gdx.graphics.getWidth() / 2f - 610, 100 + animalSellButton.getHeight() + 20);
        animalSellButton.setVisible(false);

        animalFeedButton = new TextButton("Feed",skin);
        animalFeedButton.setWidth(600);
        animalFeedButton.setPosition(Gdx.graphics.getWidth() / 2f + 10, 100 + animalSellButton.getHeight() + 20);
        animalFeedButton.setVisible(false);

        animalPetButton = new TextButton("Pet",skin);
        animalPetButton.setWidth(600);
        animalPetButton.setPosition(Gdx.graphics.getWidth() / 2f - 610, 100 + 2 * animalSellButton.getHeight() + 40);
        animalPetButton.setVisible(false);

        animalCollectButton = new TextButton("Collect Product",skin);
        animalCollectButton.setWidth(600);
        animalCollectButton.setPosition(Gdx.graphics.getWidth() / 2f + 10, 100 + 2 * animalSellButton.getHeight() + 40);
        animalCollectButton.setVisible(false);



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
        enclosureBackground = new Image(GameAssetManager.getGameAssetManager().getPlayerSocialBackGroundTexture());
        animalBackground = new Image(GameAssetManager.getGameAssetManager().getPlayerSocialBackGroundTexture());
        mapMenuBackground = GameAssetManager.getGameAssetManager().getMapMenuBackground();
        energyBar = GameAssetManager.getGameAssetManager().getEnergyBar();
        boostBar = GameAssetManager.getGameAssetManager().getBoostBar();
        greenBar = GameAssetManager.getGameAssetManager().getGreenBar();
        redBar = GameAssetManager.getGameAssetManager().getRedBar();
        radioBackgroundImage = GameAssetManager.getGameAssetManager().getRadioBackground();
        mapImage = GameAssetManager.getGameAssetManager().getMapImage();
        reactionMenuBackground = GameAssetManager.getGameAssetManager().getReactionMenuBackground();
        leaderBoardMenuBackground = GameAssetManager.getGameAssetManager().getLeaderBoardBackground();
        journalMenuBackground = GameAssetManager.getGameAssetManager().getJournalMenuBackground();


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

        radioBackgroundImage.setPosition((Gdx.graphics.getWidth()- radioBackgroundImage.getWidth())/2f,(Gdx.graphics.getHeight()- radioBackgroundImage.getHeight())/2f);
        radioBackgroundImage.setVisible(false);

        reactionMenuBackground.setPosition((Gdx.graphics.getWidth()- reactionMenuBackground.getWidth())/2f,(Gdx.graphics.getHeight()- reactionMenuBackground.getHeight())/2f);
        reactionMenuBackground.setVisible(false);

        leaderBoardMenuBackground.setPosition((Gdx.graphics.getWidth()- leaderBoardMenuBackground.getWidth())/2f,(Gdx.graphics.getHeight()- leaderBoardMenuBackground.getHeight())/2f);
        leaderBoardMenuBackground.setVisible(false);

        journalMenuBackground.setPosition((Gdx.graphics.getWidth()- journalMenuBackground.getWidth())/2f,(Gdx.graphics.getHeight()- journalMenuBackground.getHeight())/2f);
        journalMenuBackground.setVisible(false);

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
        terminateButton.setPosition((Gdx.graphics.getWidth()- terminateButton.getWidth())/2f,
                500);

        terminateButton.setVisible(false);
        exitGameButton.setVisible(false);

        controller.updateClockImage();

        farmingHoverImage.setVisible(false);
        fishingHoverImage.setVisible(false);
        miningHoverImage.setVisible(false);
        foragingHoverImage.setVisible(false);

        mapImage.setVisible(false);
        mapImage.setSize(720,530);

        //  INBOX
        messageAlertImage = new ImageButton(GameAssetManager.getGameAssetManager().getMessageAlertImage().getDrawable());
        messageBackgroundImage = new ImageButton(GameAssetManager.getGameAssetManager().getMessageBackgroundImage().getDrawable());
        readingMessage = false;
        messageLabel.setColor(Color.BLACK);
        inbox = ClientApp.getCurrentGame().getCurrentPlayer().getChatInbox();


        enclosureBackground.setSize(1400, 1000);
        enclosureBackground.setPosition((Gdx.graphics.getWidth() - 1400) / 2f, (Gdx.graphics.getHeight() - 1000) / 2f);
        enclosureBackground.setVisible(false);

        animalBackground.setSize(1400, 1000);
        animalBackground.setPosition((Gdx.graphics.getWidth() - 1400) / 2f, (Gdx.graphics.getHeight() - 1000) / 2f);
        animalBackground.setVisible(false);


        // LEADERBOARD

        playersInLeaderBoard = new ArrayList<>();

        playersInLeaderBoard.addAll(ClientApp.getCurrentGame().getPlayers());

        // STAGE
        stage.addActor(blackImage);
        stage.addActor(cookingMenuBackground);
        stage.addActor(hoveringInfoWindow);
        stage.addActor(craftingProductNameLabel);
        stage.addActor(craftingProductIngredientsLabel);
        stage.addActor(exitGameButton);
        stage.addActor(kickPlayerButton);
        stage.addActor(terminateButton);
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
        stage.addActor(radioBackgroundImage);
        stage.addActor(reactionMenuBackground);
        stage.addActor(leaderBoardMenuBackground);
        stage.addActor(journalMenuBackground);
        stage.addActor(craftingMenuBackground);
        stage.addActor(socialMenuBackground);
        stage.addActor(farmingHoverImage);
        stage.addActor(fishingHoverImage);
        stage.addActor(miningHoverImage);
        stage.addActor(foragingHoverImage);
        stage.addActor(socialButton);
        stage.addActor(playerSocialMenuBackground);
        stage.addActor(mapMenuBackground);
        stage.addActor(mapImage);
        stage.addActor(energyBar);
        stage.addActor(boostBar);
        stage.addActor(greenBar);
        stage.addActor(redBar);
        stage.addActor(listenTogetherLabel);
        stage.addActor(messageBackgroundImage);
        stage.addActor(messageAlertImage);
        stage.addActor(messageLabel);
        stage.addActor(enclosureBackground);
        stage.addActor(enclosureMenuExitButton);
        stage.addActor(animalBackground);
        stage.addActor(animalFeedButton);
        stage.addActor(animalCollectButton);
        stage.addActor(animalPetButton);
        stage.addActor(animalSellButton);
        stage.addActor(animalExitButton);
        stage.addActor(reactionTextField);
        stage.addActor(randomizeEmojis);
        stage.addActor(buffImage);
        stage.addActor(sendReaction);
        stage.addActor(leaderBoardEarningsLabel);
        stage.addActor(leaderBoardNumberOfQuestsLabel);
        stage.addActor(leaderBoardSkillsLabel);
        stage.addActor(leaderBoardUsernameLabel);
        stage.addActor(earningsSortButton);
        stage.addActor(skillSortButton);
        stage.addActor(numberOfQuestsSortButton);
        stage.addActor(sortByLabel);
        stage.addActor(journalLabel);
        stage.addActor(requestLabel);
        stage.addActor(rewardLabel);


        messageLabel.setVisible(false);
        messageAlertImage.setVisible(false);
        messageBackgroundImage.setVisible(false);

        listenTogetherLabel.setVisible(false);
        listenTogetherLabel.setColor(Color.BLACK);
        listenTogetherLabel.setFontScale(1.5f);

        friendsLabels = new ArrayList<>();
        friendshipInfos = new ArrayList<>();
        friendButtons = new ArrayList<>();

        int counter = 0;

        for ( int z = 0; z < Math.min(4,ClientApp.getCurrentGame().getPlayers().size()); z++ ){

            if (!Objects.equals(ClientApp.getCurrentGame().getPlayers().get(z).getUsername(),
                    player.getUsername())){

                Relation relation = InteractionsWithUserController.getRelation(ClientApp.getCurrentGame().getPlayers().get(z).getUsername());

                Label nameLabel = new Label(ClientApp.getCurrentGame().getPlayers().get(z).getUsername(),skin);
                Label friendshipInfo = new Label("Level: "+relation.getLevel()+"    XP: "+relation.getXp(),skin);
                TextButton interactButton = new TextButton("Interact",skin);

                nameLabel.setColor(Color.BLACK);
                friendshipInfo.setColor(Color.BLACK);

                nameLabel.setVisible(false);
                friendshipInfo.setVisible(false);
                interactButton.setVisible(false);

                friendsLabels.add(nameLabel);
                friendshipInfos.add(friendshipInfo);
                friendButtons.add(interactButton);

                nameLabel.setPosition(520,660 - 180 * counter);
                friendshipInfo.setPosition(670,660 - 180 * counter);
                interactButton.setPosition(950,660 - 180 * counter - (interactButton.getHeight()-nameLabel.getHeight())/2f);


                stage.addActor(nameLabel);
                stage.addActor(friendshipInfo);
                stage.addActor(interactButton);

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

        yourSongsSelectBox = new SelectBox<>(skin);

        othersSongsSelectBox = new SelectBox<>(skin);

        yourSongsSelectBox.setVisible(false);
        othersSongsSelectBox.setVisible(false);

        playButton.setVisible(false);
        listenButton.setVisible(false);
        nextPageButton.setVisible(false);
        previousPageButton.setVisible(false);
        uploadSongButton.setVisible(false);
        songNameLabel.setVisible(false);
        songNameLabel.setColor(Color.BLACK);

        stage.addActor(playButton);
        stage.addActor(listenButton);
        stage.addActor(nextPageButton);
        stage.addActor(previousPageButton);
        stage.addActor(uploadSongButton);
        stage.addActor(songNameLabel);
        stage.addActor(yourSongsSelectBox);
        stage.addActor(othersSongsSelectBox);


        stage.addActor(textInputField);


        emojiButtons = new ArrayList<>();

        for(Emoji emoji : Emoji.values()) {


            emoji.getEmojiButton().setSize(96,96);
            emoji.getEmojiButton().setVisible(false);
            stage.addActor(emoji.getEmojiButton());
            emojiButtons.add(emoji);

        }

        for( MiniPlayer inGamePlayer: ClientApp.getCurrentGame().getPlayers() ){
            inGamePlayer.getUsernameLabel().setVisible(false);
            inGamePlayer.getEarningsLabel().setVisible(false);
            inGamePlayer.getSkillLabel().setVisible(false);
            inGamePlayer.getNumberOfQuestsLabel().setVisible(false);
            stage.addActor(inGamePlayer.getUsernameLabel());
            stage.addActor(inGamePlayer.getEarningsLabel());
            stage.addActor(inGamePlayer.getSkillLabel());
            stage.addActor(inGamePlayer.getNumberOfQuestsLabel());
        }

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
        moneyInfo.setPosition(clockImage.getX() + clockImage.getWidth() / 2 + 25 + 100 - 25 * (controller.digitCount()), clockImage.getY() + 30);
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

        for( int i = 0; i < Math.min(4,player.getAbility(AbilityType.Farming).getLevel()); i++ ){
            skillPoints.get(i).setVisible(currentMenu == InGameMenuType.SKILL);
        }

        for( int i = 0; i < Math.min(4,player.getAbility(AbilityType.Mining).getLevel()); i++ ){
            skillPoints.get(i+4).setVisible(currentMenu == InGameMenuType.SKILL);
        }

        for( int i = 0; i < Math.min(4,player.getAbility(AbilityType.Foraging).getLevel()); i++ ){
            skillPoints.get(i+8).setVisible(currentMenu == InGameMenuType.SKILL);
        }

        for( int i = 0; i < Math.min(4,player.getAbility(AbilityType.Fishing).getLevel()); i++ ){
            skillPoints.get(i+12).setVisible(currentMenu == InGameMenuType.SKILL);
        }


    }

    private void displayRadio(){

        Array<String> songList = new Array<>();
        ClientApp.getCurrentGame().getSongList().keySet().forEach(songList::add);
        yourSongsSelectBox.setItems(songList);
        Array<String> otherPlayerUsernames = new Array<>();
        ClientApp.getCurrentGame().getPlayers().forEach(player -> {
            if (!ClientApp.getLoggedInUser().getUsername().equals(player.getUsername()))
                otherPlayerUsernames.add(player.getUsername());
        });
        othersSongsSelectBox.setItems(otherPlayerUsernames);

        songNameLabel.setText("Currently listening to: " + ((ClientApp.getCurrentGame().getCurrentMusicName() == null)? "Nothing":ClientApp.getCurrentGame().getCurrentMusicName()));

        radioBackgroundImage.setVisible(currentMenu == InGameMenuType.RADIO);
        songNameLabel.setVisible(currentMenu == InGameMenuType.RADIO);
        playButton.setVisible(currentMenu == InGameMenuType.RADIO && yourSongsPage);
        listenButton.setVisible(currentMenu == InGameMenuType.RADIO && !yourSongsPage);
        nextPageButton.setVisible(currentMenu == InGameMenuType.RADIO);
        previousPageButton.setVisible(currentMenu == InGameMenuType.RADIO);
        uploadSongButton.setVisible(currentMenu == InGameMenuType.RADIO && yourSongsPage);
        listenTogetherLabel.setVisible(currentMenu == InGameMenuType.RADIO && !yourSongsPage);

        previousPageButton.setPosition(radioBackgroundImage.getX()+40, radioBackgroundImage.getY()+40);
        nextPageButton.setPosition(radioBackgroundImage.getX() + radioBackgroundImage.getWidth()-40 - nextPageButton.getWidth(), radioBackgroundImage.getY()+40);
        nextPageButton.toFront();
        previousPageButton.toFront();

        yourSongsSelectBox.setWidth(3 * radioBackgroundImage.getWidth() / 5);
        othersSongsSelectBox.setWidth(3 * radioBackgroundImage.getWidth() / 5);

        yourSongsSelectBox.setPosition(radioBackgroundImage.getX() + radioBackgroundImage.getWidth() / 5,nextPageButton.getY()+20);
        othersSongsSelectBox.setPosition(radioBackgroundImage.getX() + radioBackgroundImage.getWidth() / 5,nextPageButton.getY()+20);

        yourSongsSelectBox.setVisible(currentMenu == InGameMenuType.RADIO && yourSongsPage);
        othersSongsSelectBox.setVisible(currentMenu == InGameMenuType.RADIO && !yourSongsPage);

        songNameLabel.setPosition(radioBackgroundImage.getX() + 40, radioBackgroundImage.getY() + radioBackgroundImage.getHeight() - songNameLabel.getHeight() - 100);

        uploadSongButton.setWidth(yourSongsSelectBox.getWidth());
        playButton.setWidth(yourSongsSelectBox.getWidth());
        uploadSongButton.setPosition(yourSongsSelectBox.getX(),songNameLabel.getY()- uploadSongButton.getHeight()- 50);
        playButton.setPosition(yourSongsSelectBox.getX(),songNameLabel.getY()- uploadSongButton.getHeight()- 100 - playButton.getHeight());


        listenTogetherLabel.setPosition(othersSongsSelectBox.getX()+50, playButton.getY());
        listenButton.setWidth(othersSongsSelectBox.getWidth());
        listenButton.setPosition(othersSongsSelectBox.getX(), uploadSongButton.getY()-50);

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

        Array<String> playersUsername = new Array<>();
        ClientApp.getCurrentGame().getPlayers().forEach(
                miniPlayer -> playersUsername.add(miniPlayer.getUsername())
        );
        playerSelectBox.setItems(playersUsername);

        exitMenuBackground.setPosition((Gdx.graphics.getWidth() - exitMenuBackground.getWidth()) / 2f, (Gdx.graphics.getHeight() - exitMenuBackground.getHeight()) / 2f);

        exitMenuBackground.setVisible(currentMenu == InGameMenuType.EXIT);
        terminateButton.setVisible(currentMenu == InGameMenuType.EXIT);
        exitGameButton.setVisible(currentMenu == InGameMenuType.EXIT);
        playerSelectBox.setVisible(currentMenu == InGameMenuType.EXIT);
        kickPlayerButton.setVisible(currentMenu == InGameMenuType.EXIT);

        exitGameButton.toFront();
        kickPlayerButton.toFront();
        terminateButton.toFront();
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
            friendButtons.get(i).setVisible(currentMenu == InGameMenuType.PLAYER_SOCIAL);
            friendsLabels.get(i).toFront();
            friendshipInfos.get(i).toFront();

        }

    }

    private void displayAnimal() {
        animalBackground.setVisible(currentMenu == InGameMenuType.ANIMAL);
        animalCollectButton.setVisible(currentMenu == InGameMenuType.ANIMAL);
        animalFeedButton.setVisible(currentMenu == InGameMenuType.ANIMAL);
        if (animalInfoLabel != null)
            animalInfoLabel.setVisible(currentMenu == InGameMenuType.ANIMAL);
        animalPetButton.setVisible(currentMenu == InGameMenuType.ANIMAL);
        animalSellButton.setVisible(currentMenu == InGameMenuType.ANIMAL);
        animalExitButton.setVisible(currentMenu == InGameMenuType.ANIMAL);
    }

    private void setupAnimalData() {
        if (animalInfoLabel != null)
            animalInfoLabel.remove();
        animalInfoLabel = new Label("Animal Name:     " + animal.getName() + "\n" +
                "Animal Type:     " + animal.getType().getName() + "\n" +
                "Base SellPrice:     " + animal.getPrice() + "\n" +
                "Till next product:     " + animal.getTillNextProduction() + "\n" +
                "FriendShip pts:    " + animal.getFriendship(),
                skin);
        animalInfoLabel.setColor(Color.BLACK);
        animalInfoLabel.setFontScale(2f);
        animalInfoLabel.setPosition(Gdx.graphics.getWidth() / 2f - 600, 700);
        animalInfoLabel.setVisible(false);
        stage.addActor(animalInfoLabel);

    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
        setupAnimalData();
        currentMenu = InGameMenuType.ANIMAL;
        makeOnScreenItemsInvisible();
    }

    private void displayEnclosure() {
        if (animalEnclosure == null)
            return;
        enclosureBackground.setVisible(currentMenu == InGameMenuType.ANIMAL_ENCLOSURE);
        enclosureButtons.forEach(enclosureButton -> {
            enclosureButton.setVisible(currentMenu == InGameMenuType.ANIMAL_ENCLOSURE);
        });
        enclosureLabels.forEach(enclosureLabel -> {
            enclosureLabel.setVisible(currentMenu == InGameMenuType.ANIMAL_ENCLOSURE);
        });
        enclosureMenuExitButton.setVisible(currentMenu == InGameMenuType.ANIMAL_ENCLOSURE);
    }

    private void setupEnclosureData() {
        enclosureButtons.forEach(Actor::remove);
        enclosureLabels.forEach(Actor::remove);
        enclosureLabels.clear();
        enclosureButtons.clear();
        float x1 = (Gdx.graphics.getWidth() - 1200) / 2f, x2 = (Gdx.graphics.getWidth() + 400) / 2f,
                y = Gdx.graphics.getHeight() / 2f + 300;
        for (Animal a : animalEnclosure.getAnimals()) {
            Label label = new Label(a.getName() + " " + a.getType().getName(), skin);
            label.setColor(Color.BLACK);
            label.setPosition(x1, y + 40);
            label.setFontScale(2f);
            label.setVisible(false);
            enclosureLabels.add(label);
            stage.addActor(label);

            TextButton textButton = new TextButton("Shepard in/out", skin);
            textButton.setPosition(x2, y);
            textButton.setWidth(450);
            textButton.setVisible(false);
            enclosureButtons.add(textButton);
            textButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    playClickSound();
                    if (a.isOut()) {
                        a.getCurrentCell().setObject(null);
                        a.setOut(false);
                    } else {
                        ClientApp.getCurrentGame().getCurrentPlayer().getCurrentCell().setObject(a);
                        a.setOut(true);
                        a.setWasFeed(true);
                        a.addFriendShip(8);
                        a.setCurrentCell(ClientApp.getCurrentGame().getCurrentPlayer().getCurrentCell());
                    }
                }
            });
            stage.addActor(textButton);
            y -= 160;
        }
    }

    public void setAnimalEnclosure(AnimalEnclosure animalEnclosure) {
        this.animalEnclosure = animalEnclosure;
        setupEnclosureData();
        currentMenu = InGameMenuType.ANIMAL_ENCLOSURE;
        makeOnScreenItemsInvisible();
    }

    private void updatePlayerPositionsInMap(){

        for( MiniPlayer inGamePlayer: ClientApp.getCurrentGame().getPlayers() ){

            int xInitial = 764;
            int yInitial = 448;

            int xCoEfficient = (inGamePlayer.getMapIndex()==4)? 265:210;
            int yCoEfficient = (inGamePlayer.getMapIndex()==4)? 130:184;

            if ( inGamePlayer.getMapIndex() == 0 ){
                xInitial = 560;
                yInitial = 564;
            }
            else if ( inGamePlayer.getMapIndex() == 1 ){
                xInitial = 1022;
                yInitial = 564;
            }
            else if ( inGamePlayer.getMapIndex() == 2 ){
                xInitial = 566;
                yInitial = 271;
            }
            else if ( inGamePlayer.getMapIndex() == 3 ){
                xInitial = 1018;
                yInitial = 271;
            }


            Image playerIcon = playerIconsInMap.get(inGamePlayer);
            playerIcon.setPosition(xInitial + xCoEfficient * inGamePlayer.getXRatio() - playerIcon.getWidth()/2f,
                    yInitial + yCoEfficient * inGamePlayer.getYRatio() - playerIcon.getHeight()/2f);
            playerIcon.toFront();

        }

    }

    private void displayMapMenu(){

        mapMenuBackground.setVisible(currentMenu == InGameMenuType.MAP);
        mapImage.setVisible(currentMenu == InGameMenuType.MAP);
        for( MiniPlayer player : playerIconsInMap.keySet() ){
            playerIconsInMap.get(player).setVisible(currentMenu == InGameMenuType.MAP);
        }

        mapImage.setPosition(mapMenuBackground.getX()+(mapMenuBackground.getWidth()-mapImage.getWidth())/2f,
                mapMenuBackground.getY()+(mapMenuBackground.getHeight()-mapImage.getHeight())/2f-30);

        updatePlayerPositionsInMap();

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

    private void displayInbox(){

        messageAlertImage.setVisible(!inbox.isEmpty() && !readingMessage);
        messageBackgroundImage.setVisible(readingMessage);
        messageLabel.setVisible(readingMessage);


        messageAlertImage.setPosition(50,Gdx.graphics.getHeight()-50-messageAlertImage.getHeight());
        messageBackgroundImage.setPosition(50,Gdx.graphics.getHeight()-messageBackgroundImage.getHeight() - 50);
        messageLabel.setPosition(70, messageBackgroundImage.getY() + messageBackgroundImage.getHeight() - 50 - messageLabel.getHeight());


    }

    private void displayReactionMenu(){

        reactionTyping = stage.getKeyboardFocus() == reactionTextField;

        reactionMenuBackground.setVisible(currentMenu == InGameMenuType.REACTION);
        randomizeEmojis.setVisible(currentMenu == InGameMenuType.REACTION);
        reactionTextField.setVisible(currentMenu == InGameMenuType.REACTION);
        sendReaction.setVisible(currentMenu == InGameMenuType.REACTION);

        reactionTextField.setWidth(reactionMenuBackground.getWidth()-100);
        reactionTextField.setPosition(reactionMenuBackground.getX() + 50, reactionMenuBackground.getY() + 300);

        randomizeEmojis.setWidth(reactionTextField.getWidth()/2f-25);
        sendReaction.setWidth(reactionTextField.getWidth()/2f-25);

        sendReaction.setPosition(reactionTextField.getX(),reactionTextField.getY() - sendReaction.getHeight()-50);
        randomizeEmojis.setPosition(reactionTextField.getX() + sendReaction.getWidth() + 50,reactionTextField.getY() - sendReaction.getHeight()-50);

        int num = 0;
        for ( Emoji emoji : emojiButtons ){

            if ( num < 5 ){
                emoji.getEmojiButton().setVisible(currentMenu == InGameMenuType.REACTION);
                emoji.getEmojiButton().toFront();
                emoji.getEmojiButton().setPosition(
                        reactionMenuBackground.getX() + 150 * num + 70, reactionMenuBackground.getY() + reactionMenuBackground.getHeight() - emoji.getEmojiButton().getHeight() - 100
                );
            }
            else{
                emoji.getEmojiButton().setVisible(false);
            }
            num ++;

        }



    }

    private void displayBuff() {
        if (player.getCurrentBuff() != null) {
            buffImage.remove();
            buffImage = new Image(
                    GameAssetManager.getGameAssetManager().getAbilityTexture(player.getCurrentBuff().getAbility()));
            buffImage.setPosition(1650, 30);
            stage.addActor(buffImage);
            buffImage.toFront();
        }

        buffImage.setVisible(player.getCurrentBuff() != null);
    }

    private void displayLeaderBoard(){

        leaderBoardMenuBackground.setVisible(currentMenu == InGameMenuType.LEADERBOARD);
        leaderBoardUsernameLabel.setVisible(currentMenu == InGameMenuType.LEADERBOARD);
        leaderBoardEarningsLabel.setVisible(currentMenu == InGameMenuType.LEADERBOARD);
        leaderBoardSkillsLabel.setVisible(currentMenu == InGameMenuType.LEADERBOARD);
        leaderBoardNumberOfQuestsLabel.setVisible(currentMenu == InGameMenuType.LEADERBOARD);
        numberOfQuestsSortButton.setVisible(currentMenu == InGameMenuType.LEADERBOARD);
        earningsSortButton.setVisible(currentMenu == InGameMenuType.LEADERBOARD);
        skillSortButton.setVisible(currentMenu == InGameMenuType.LEADERBOARD);
        sortByLabel.setVisible(currentMenu == InGameMenuType.LEADERBOARD);

        float topY = leaderBoardMenuBackground.getY() + leaderBoardMenuBackground.getHeight();

        skillSortButton.setWidth(earningsSortButton.getWidth());

        leaderBoardUsernameLabel.setPosition(leaderBoardMenuBackground.getX()+50 + 25,topY - 115  - leaderBoardUsernameLabel.getHeight());
        leaderBoardEarningsLabel.setPosition(leaderBoardMenuBackground.getX()+250 + 25,topY - 115 - leaderBoardEarningsLabel.getHeight());
        leaderBoardSkillsLabel.setPosition(leaderBoardMenuBackground.getX()+425 + 25 ,topY - 115 - leaderBoardSkillsLabel.getHeight());
        leaderBoardNumberOfQuestsLabel.setPosition(leaderBoardMenuBackground.getX()+550 + 25,topY - 100 - leaderBoardNumberOfQuestsLabel.getHeight());
        skillSortButton.setPosition(leaderBoardMenuBackground.getX()+150+10,leaderBoardMenuBackground.getY()+25);
        earningsSortButton.setPosition(skillSortButton.getX()+skillSortButton.getWidth()+50+10,leaderBoardMenuBackground.getY()+25);
        sortByLabel.setPosition(leaderBoardMenuBackground.getX()+50,skillSortButton.getY()+skillSortButton.getHeight()+15 + (numberOfQuestsSortButton.getHeight()-sortByLabel.getHeight())/2f);
        numberOfQuestsSortButton.setPosition(sortByLabel.getX()+sortByLabel.getWidth()+50,skillSortButton.getY()+skillSortButton.getHeight()+15);

        for ( int i = 0; i < playersInLeaderBoard.size(); i++ ){

            MiniPlayer miniPlayer = playersInLeaderBoard.get(i);
            miniPlayer.getUsernameLabel().setVisible(currentMenu == InGameMenuType.LEADERBOARD);
            miniPlayer.getEarningsLabel().setVisible(currentMenu == InGameMenuType.LEADERBOARD);
            miniPlayer.getSkillLabel().setVisible(currentMenu == InGameMenuType.LEADERBOARD);
            miniPlayer.getNumberOfQuestsLabel().setVisible(currentMenu == InGameMenuType.LEADERBOARD);

            miniPlayer.getUsernameLabel().setPosition(leaderBoardUsernameLabel.getX(),645- 50 * i);
            miniPlayer.getEarningsLabel().setPosition(leaderBoardEarningsLabel.getX(),665- 50 * i);
            miniPlayer.getSkillLabel().setPosition(leaderBoardSkillsLabel.getX(),665- 50 * i);
            miniPlayer.getNumberOfQuestsLabel().setPosition(leaderBoardNumberOfQuestsLabel.getX(),665- 50 * i);

        }

    }

    private void updatePlayers(){

        if ( miniPlayerUpdateTimer > 1f ){

            for ( MiniPlayer inGamePlayer : ClientApp.getCurrentGame().getPlayers() ){
                inGamePlayer.updateMiniPlayer();
            }
            playersInLeaderBoard.clear();
            playersInLeaderBoard.addAll( ClientApp.getCurrentGame().getPlayers() );
            sortPlayersList();

            int counter = 0;
            for ( int z = 0; z < Math.min(4,ClientApp.getCurrentGame().getPlayers().size()); z++ ){

                if (!Objects.equals(ClientApp.getCurrentGame().getPlayers().get(z).getUsername(),
                        player.getUsername())){

                    Relation relation = InteractionsWithUserController.getRelation(ClientApp.getCurrentGame().getPlayers().get(z).getUsername());

                    friendshipInfos.get(counter).setText("Level: "+relation.getLevel()+"    XP: "+relation.getXp());
                    counter ++;
                }
            }

        }

        if ( miniPlayerUpdateTimer > 1f ){
            miniPlayerUpdateTimer = 0f;
        }

    }

    private void displayJournal(){

        journalMenuBackground.setVisible(currentMenu == InGameMenuType.JOURNAL);
        journalLabel.setVisible(currentMenu == InGameMenuType.JOURNAL);
        requestLabel.setVisible(currentMenu == InGameMenuType.JOURNAL);
        rewardLabel.setVisible(currentMenu == InGameMenuType.JOURNAL);

        journalLabel.setPosition(journalMenuBackground.getX()+50,
                journalMenuBackground.getY()+journalMenuBackground.getHeight()-journalLabel.getHeight()-100);

        requestLabel.setPosition(journalMenuBackground.getX()+50,journalLabel.getY()-requestLabel.getHeight()-20);
        rewardLabel.setPosition(journalMenuBackground.getX() + journalMenuBackground.getWidth()/2f,journalLabel.getY()-rewardLabel.getHeight()-20);

        if ( currentMenu != InGameMenuType.JOURNAL ){

            makeQuestsInvisible();

        }

    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
    }

    public void displayHUD(float delta) {



        miniPlayerUpdateTimer += delta;
        updatePlayers();

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
        displayRadio();
        displaySkillMenu();
        displaySocialMenu();
        displayJournal();
        displayCraftingMenu();
        displayExitMenu();
        displayMapMenu();
        displayCookingMenu();
        displayItemQuantity();
        displayHoveringItemInfo();
        displaySkillInfo();
        displayPlayerSocial();
        displayLeaderBoard();
        displayEnergy();
        displayInbox();
        displayInputField();
        displayEnclosure();
        displayAnimal();
        displayBuff();
        artisanController.update();
        fridgeController.update();
        shippingBinController.update();
        displayReactionMenu();

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

                if (!isInputFieldVisible && !reactionTyping) {

                    if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT)) {
                        ctrlPressed = true;
                    }

                    if (keycode == Input.Keys.T) {
                        playClickSound();
                        isInputFieldVisible = true;
                        textInputField.setText("");
                        stage.setKeyboardFocus(textInputField);
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
                    else if (keycode == Input.Keys.F) {

                        if ( currentMenu == InGameMenuType.JOURNAL ) {
                            currentMenu = InGameMenuType.NONE;
                        }
                        else{

                            int counter = 0;

                            for ( Quest quest: InteractionsWithNPCController.getQuestsForJournal() ){

                                Label requestLabelQuest = new Label(quest.getRequest().getItem().getName()+"\nx" + quest.getRequest().getQuantity(),skin);
                                questLabels.add(requestLabelQuest);

                                Label rewardLabelQuest;

                                if ( quest.getReward().getItem() == ShopItems.RelationLevel ){
                                    rewardLabelQuest = new Label("+1 Relation!!!",skin);
                                }
                                else{
                                    rewardLabelQuest = new Label(quest.getReward().getItem().getName()+"\nx" + quest.getReward().getQuantity(),skin);
                                }

                                questLabels.add(rewardLabelQuest);

                                requestLabelQuest.setColor(Color.BLACK);
                                rewardLabelQuest.setColor(Color.BLACK);

                                requestLabelQuest.setPosition(requestLabel.getX(), requestLabel.getY() - 70 * counter - requestLabelQuest.getHeight());
                                rewardLabelQuest.setPosition(rewardLabel.getX(), rewardLabel.getY() - 70 * counter - rewardLabelQuest.getHeight());

                                stage.addActor(requestLabelQuest);
                                stage.addActor(rewardLabelQuest);

                                counter ++;

                            }

                            currentMenu = InGameMenuType.JOURNAL;
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

                    }
                    else if (keycode == Input.Keys.L) {

                        if ( currentMenu == InGameMenuType.LEADERBOARD ) {
                            currentMenu = InGameMenuType.NONE;
                        }
                        else{
                            currentMenu = InGameMenuType.LEADERBOARD;
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

                    }
                    else if (keycode == Input.Keys.R) {

                        if (currentMenu == InGameMenuType.RADIO) {
                            currentMenu = InGameMenuType.NONE;
                        }
                        else {
                            currentMenu = InGameMenuType.RADIO;
                            makeOnScreenItemsInvisible();
                        }

                    }
                    else if (keycode == Input.Keys.G) {

                        if (currentMenu == InGameMenuType.REACTION) {
                            currentMenu = InGameMenuType.NONE;
                        }
                        else {
                            currentMenu = InGameMenuType.REACTION;
                            makeOnScreenItemsInvisible();
                        }

                    }
                    else if ( keycode == Input.Keys.C ) {

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

                    if ( reactionTyping ){
                        if ( keycode == Input.Keys.ENTER ) {
                            stage.setKeyboardFocus(null);
                        }
                    }
                    else{
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

                System.out.println(x + " " + y);

                if (!isInputFieldVisible) {

                    if (currentMenu != InGameMenuType.ARTISAN && currentMenu != InGameMenuType.FRIDGE) {
                        if ((525 < x && x < 580) && (800 < y && y < 860)) {
                            currentMenu = InGameMenuType.INVENTORY;
                            return true;
                        } else if ((590 < x && x < 645) && (800 < y && y < 860)) {
                            currentMenu = InGameMenuType.SKILL;
                            makeOnScreenItemsInvisible();
                            return true;
                        } else if ((650 < x && x < 715) && (800 < y && y < 860)) {
                            currentMenu = InGameMenuType.SOCIAL;
                            makeOnScreenItemsInvisible();
                            return true;
                        } else if ((715 < x && x < 780) && (800 < y && y < 860)) {
                            currentMenu = InGameMenuType.MAP;
                            makeOnScreenItemsInvisible();
                            return true;
                        } else if ((785 < x && x < 836) && (800 < y && y < 860)) {
                            currentMenu = InGameMenuType.CRAFTING;
                            makeOnScreenItemsInvisible();
                            return true;
                        } else if ((850 < x && x < 900) && (800 < y && y < 860)) {
                            currentMenu = InGameMenuType.EXIT;
                            makeOnScreenItemsInvisible();
                            return true;
                        }
                    }

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
                            rowCoEfficient = max(1, rowCoEfficient-1);
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

        reactionTextField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {

                if ( reactionTextField.getText().length() > 10 ){
                    String first = reactionTextField.getText().substring(0, 10);
                    reactionTextField.setText(first);
                    reactionTextField.setCursorPosition(reactionTextField.getText().length());
                }


            }
        });

        for ( int i = 0; i < 5; i++ ){
            Emoji emoji = emojiButtons.get(i);
            emoji.getEmojiButton().addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    playClickSound();
                    controller.reactWithEmoji(emoji);
                }

            });
        }

        earningsSortButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                sortType = 0;
                sortPlayersList();
            }

        });

        skillSortButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                sortType = 1;
                sortPlayersList();
            }

        });

        numberOfQuestsSortButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                sortType = 2;
                sortPlayersList();
            }

        });

        sendReaction.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.reactWithText(reactionTextField.getText());
                reactionTextField.setText("");
                stage.setKeyboardFocus(null);
            }

        });

        randomizeEmojis.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                Collections.shuffle(emojiButtons);
            }

        });

        messageAlertImage.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                readingMessage = true;
                messageLabel.setText(inbox.getFirst());
                inbox.removeFirst();
            }

        });

        messageBackgroundImage.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                readingMessage = false;
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

        nextPageButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                yourSongsPage = !yourSongsPage;
            }

        });

        previousPageButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                yourSongsPage = !yourSongsPage;
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

        int counter = 0;
        for ( MiniPlayer inGamePlayer: ClientApp.getCurrentGame().getPlayers() ){

            if ( !player.getUsername().equals(inGamePlayer.getUsername()) ){
                friendButtons.get(counter).addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        playClickSound();
                        controller.openInteractionMenu(inGamePlayer.getUsername());
                    }

                });
                counter ++;
            }


        }

        enclosureMenuExitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                currentMenu = InGameMenuType.NONE;
            }
        });

        animalExitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                currentMenu = InGameMenuType.NONE;
            }
        });

        animalCollectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                currentMenu = InGameMenuType.NONE;
                ResultController.addResult(new GameMenuController(new GameView()).collectProduct(animal.getName()));
            }
        });

        animalFeedButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                currentMenu = InGameMenuType.NONE;
                ResultController.addResult(new GameMenuController(new GameView()).feedHay(animal.getName()));
                float animalX = OutsideView.getGraphicalPositionInFarmMap(animal.getCurrentCell().getPosition().getX(),
                        animal.getCurrentCell().getPosition().getY()).getX();
                float animalY = OutsideView.getGraphicalPositionInFarmMap(animal.getCurrentCell().getPosition().getX(),
                        animal.getCurrentCell().getPosition().getY()).getY();
                PopUpController.addPopUp(new PopUpTexture(
                        ShopItems.Hay.getTexture(),
                        animalX + 20, animalY + 60,
                        animalX + 20, animalY + 20,
                        2
                ));
            }
        });

        animalPetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                currentMenu = InGameMenuType.NONE;
                ResultController.addResult(new GameMenuController(new GameView()).pet(animal.getName()));
                float animalX = OutsideView.getGraphicalPositionInFarmMap(animal.getCurrentCell().getPosition().getX(),
                animal.getCurrentCell().getPosition().getY()).getX();
                float animalY = OutsideView.getGraphicalPositionInFarmMap(animal.getCurrentCell().getPosition().getX(),
                        animal.getCurrentCell().getPosition().getY()).getY();
                PopUpController.addPopUp(new PopUpTexture(
                        ((TextureRegionDrawable) Emoji.GALB.getEmojiImage().getDrawable())
                                .getRegion().getTexture(),
                        animalX + 20, animalY + 40,
                        animalX + 20, animalY + 70,
                        2
                ));
            }
        });

        animalSellButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                currentMenu = InGameMenuType.NONE;
                ResultController.addResult(new GameMenuController(new GameView()).sellAnimal(animal.getName()));
            }
        });
        exitGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                errorLabel.set(controller.saveAndExit());
            }
        });

        terminateButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.terminateGame();
            }
        });

        kickPlayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.kickPlayer(playerSelectBox.getSelected());
            }
        });

        uploadSongButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.openFileChooser();
            }
        });

        playButton.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               playClickSound();
               controller.playSong(
                       ClientApp.getCurrentGame().getSongIdByName(yourSongsSelectBox.getSelected()),
                       yourSongsSelectBox.getSelected()
               );
           }
        });

        listenButton.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               playClickSound();
               errorLabel.set(controller.listenWith(othersSongsSelectBox.getSelected()));
           }
        });


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

    public void makeOnScreenItemsInvisible(){

        for (Stacks onScreenItem : onScreenItems) {
            onScreenItem.getItem().getItemImage().setVisible(false);
        }

    }

    public InGameMenuType getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(InGameMenuType currentMenu) {
        this.currentMenu = currentMenu;
    }

    public HUDController getController() {
        return controller;
    }

    public ArtisanController getArtisanController() {
        return artisanController;
    }

    public FridgeController getFridgeController() {
        return fridgeController;
    }

    public ShippingBinController getShippingBinController() {
        return shippingBinController;
    }

    private void sortPlayersList(){

        if ( sortType == 0 ){
            playersInLeaderBoard.sort(Comparator.comparingDouble(MiniPlayer::getMoney).reversed());
        }
        else if ( sortType == 1 ){
            playersInLeaderBoard.sort(Comparator.comparingDouble(MiniPlayer::getTotalAbility).reversed());
        }
        else if ( sortType == 2 ){
            playersInLeaderBoard.sort(Comparator.comparingDouble(MiniPlayer::getNumberOfQuestsCompleted).reversed());
        }


    }

    private void makeQuestsInvisible(){

        for( Label quest: questLabels ){
            quest.remove();
        }

        questLabels.clear();

    }

}
