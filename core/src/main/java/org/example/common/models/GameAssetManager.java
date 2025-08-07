package org.example.common.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.example.server.models.Item;
import org.example.server.models.enums.NPCType;
import org.example.server.models.enums.Plants.*;
import org.example.server.models.enums.items.*;
import org.example.server.models.enums.items.products.AnimalProduct;
import org.example.server.models.enums.items.products.CookingProduct;
import org.example.server.models.enums.items.products.CraftingProduct;
import org.example.server.models.utils.MusicPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


public class GameAssetManager {



    private static GameAssetManager gameAssetManager;

    private final Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));

    private final Image blackImage = new Image(new Texture(Gdx.files.internal("black_4k.png")));

    private final Texture background = new Texture(Gdx.files.internal("Images/menu_background.png"));
    private final Texture StardewValleyText = new Texture(Gdx.files.internal("Images/stardew_valley_text.png"));

    private final MusicPlayer musicPlayer = new MusicPlayer();

    private final Sound clickSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/click.wav"));

    private final Color errorColor = new Color(1, 0.31f, 0, 1);
    private final Color acceptColor = new Color(0.216f, 0.831f, 0.255f, 1);

    private final Texture[] avatarTextures = {
            new Texture(Gdx.files.internal("Avatars/stardew_avatar_0_0.png")),
            new Texture(Gdx.files.internal("Avatars/stardew_avatar_0_1.png")),
            new Texture(Gdx.files.internal("Avatars/stardew_avatar_0_2.png")),
            new Texture(Gdx.files.internal("Avatars/stardew_avatar_1_0.png")),
            new Texture(Gdx.files.internal("Avatars/stardew_avatar_1_1.png")),
            new Texture(Gdx.files.internal("Avatars/stardew_avatar_1_2.png")),
            new Texture(Gdx.files.internal("Avatars/stardew_avatar_2_0.png")),
            new Texture(Gdx.files.internal("Avatars/stardew_avatar_2_1.png")),
            new Texture(Gdx.files.internal("Avatars/stardew_avatar_2_2.png")),
    };

    private final Texture map1 = new Texture(Gdx.files.internal("MapImage/avatar7.png"));
    private final Texture map2 = new Texture(Gdx.files.internal("MapImage/avatar10.png"));
    private final Texture map3 = new Texture(Gdx.files.internal("MapImage/avatar11.png"));
    private final Texture map4 = new Texture(Gdx.files.internal("MapImage/avatar15.png"));

    private final Image rainyFall = new Image(new Texture(Gdx.files.internal("Clock/RainyFall.png")));
    private final Image stormyFall = new Image(new Texture(Gdx.files.internal("Clock/StormyFall.png")));
    private final Image sunnyFall = new Image(new Texture(Gdx.files.internal("Clock/SunnyFall.png")));
    private final Image snowyFall = new Image(new Texture(Gdx.files.internal("Clock/SnowyFall.png")));
    private final Image rainyWinter = new Image(new Texture(Gdx.files.internal("Clock/RainyWinter.png")));
    private final Image stormyWinter = new Image(new Texture(Gdx.files.internal("Clock/StormyWinter.png")));
    private final Image sunnyWinter = new Image(new Texture(Gdx.files.internal("Clock/SunnyWinter.png")));
    private final Image snowyWinter = new Image(new Texture(Gdx.files.internal("Clock/SnowyWinter.png")));
    private final Image rainySpring = new Image(new Texture(Gdx.files.internal("Clock/RainySpring.png")));
    private final Image stormySpring = new Image(new Texture(Gdx.files.internal("Clock/StormySpring.png")));
    private final Image sunnySpring = new Image(new Texture(Gdx.files.internal("Clock/SunnySpring.png")));
    private final Image snowySpring = new Image(new Texture(Gdx.files.internal("Clock/SnowySpring.png")));
    private final Image rainySummer = new Image(new Texture(Gdx.files.internal("Clock/RainySummer.png")));
    private final Image stormySummer = new Image(new Texture(Gdx.files.internal("Clock/StormySummer.png")));
    private final Image sunnySummer = new Image(new Texture(Gdx.files.internal("Clock/SunnySummer.png")));
    private final Image snowySummer = new Image(new Texture(Gdx.files.internal("Clock/SnowySummer.png")));

    private final Texture homeTexture = new Texture("assets/Images/home_cropped.png");
    private final Texture greenHouseTexture = new Texture("assets/Images/greenhouse2.1.png");
    private final Texture hutTexture = new Texture("assets/Images/hut.png");

    private final Texture freeCellTexture = new Texture("assets/Images/Floorings/Free4.png");
    private final Texture quarryCellTexture = new Texture("assets/Images/Floorings/Quarry.png");
    private final Texture NpcMapCellTexture = new Texture("assets/Images/Floorings/NPCValley.png");
    private final Texture WaterCellTexture = new Texture("assets/Images/Floorings/Water.png");
    private final Texture plowedCellTexture = new Texture("assets/Images/Floorings/Plowed.png");
    private final Texture lightGrassCellTexture = new Texture("assets/Images/Floorings/LightGrass.png");
    private final Texture darkGrassCellTexture = new Texture("assets/Images/Floorings/Flooring_40.png");
    private final Texture darkGrass2CellTexture = new Texture("assets/Images/Floorings/DarkGrass2.png");
    private final Texture mapLinkCellTexture = new Texture("assets/Images/Floorings/Flooring_34.png");
    private final Texture hoedCellTexture = new Texture("assets/Images/Floorings/Hoed.png");
    private final Texture hoedAndWateredCellTexture = new Texture("assets/Images/Floorings/Hoed_and_Watered.png");
    private final Texture RedTileCellTexture = new Texture("assets/Images/Floorings/Red_Tile.png");
    private final Texture GreenTileCellTexture = new Texture("assets/Images/Floorings/Green_Tile.png");

    private final Texture NPCDialogueSign = new Texture("assets/Images/!_Npc_Talking.png");
    private final Texture npcDialogueBox = new Texture("assets/Images/HoveringInfoWindow.png");
    private final Texture THEFUCKINGTEXTURE = new Texture("assets/Images/PlayerSocial/PlayerSocialBackground2.png");

    private final TextureAtlas characterAtlas = new TextureAtlas("assets/Character/character.atlas");
    private final TextureRegion standingTexture = characterAtlas.createSprite("standing_1");
    private final Texture passOutTexture = new Texture("assets/Character/PassOut.png");

    private final Texture inventoryHotBar = new Texture(Gdx.files.internal("Inventory/InventoryHotbar.png"));

    private final Texture inventorySelectSlot = new Texture(Gdx.files.internal("Inventory/SelectedSlot.png"));

    private final Texture coinTexture = new Texture(Gdx.files.internal("Items/Shop_Items/Coin.png"));

    private final Texture jojaMartTexture = new Texture(Gdx.files.internal("Images/Shops/JojaMart.png"));
    private final Texture pierresGeneralTexture = new Texture(Gdx.files.internal("Images/Shops/Pierres General Store.png"));
    private final Texture starDropTexture = new Texture(Gdx.files.internal("Images/Shops/The Stardrop Saloon.png"));

    private final TextureAtlas cabinsAtlas = new TextureAtlas("assets/Buildings/Buildings.atlas");

    private final TextureRegion[] cabinTextures= new TextureRegion[]{
            new TextureRegion(cabinsAtlas.createSprite("cabin_1").getTexture(),
                    cabinsAtlas.findRegion("cabin_1").getRegionX(),
                    cabinsAtlas.findRegion("cabin_1").getRegionY(),
                    80, 112),
            new TextureRegion(cabinsAtlas.createSprite("cabin_2").getTexture(),
                    cabinsAtlas.findRegion("cabin_2").getRegionX(),
                    cabinsAtlas.findRegion("cabin_2").getRegionY(),
                    80, 112),
            new TextureRegion(cabinsAtlas.createSprite("cabin_3").getTexture(),
                    cabinsAtlas.findRegion("cabin_3").getRegionX(),
                    cabinsAtlas.findRegion("cabin_3").getRegionY(),
                    80, 112),
            new TextureRegion(cabinsAtlas.createSprite("cabin_4").getTexture(),
                    cabinsAtlas.findRegion("cabin_4").getRegionX(),
                    cabinsAtlas.findRegion("cabin_4").getRegionY(),
                    80, 112),
            new TextureRegion(cabinsAtlas.createSprite("cabin_5").getTexture(),
                    cabinsAtlas.findRegion("cabin_5").getRegionX(),
                    cabinsAtlas.findRegion("cabin_5").getRegionY(),
                    80, 112),
            new TextureRegion(cabinsAtlas.createSprite("cabin_6").getTexture(),
                    cabinsAtlas.findRegion("cabin_6").getRegionX(),
                    cabinsAtlas.findRegion("cabin_6").getRegionY(),
                    80, 112),
            new TextureRegion(cabinsAtlas.createSprite("cabin_7").getTexture(),
                    cabinsAtlas.findRegion("cabin_7").getRegionX(),
                    cabinsAtlas.findRegion("cabin_7").getRegionY(),
                    80, 112),
            new TextureRegion(cabinsAtlas.createSprite("cabin_8").getTexture(),
                    cabinsAtlas.findRegion("cabin_8").getRegionX(),
                    cabinsAtlas.findRegion("cabin_8").getRegionY(),
                    80, 112),
            new TextureRegion(cabinsAtlas.createSprite("cabin_9").getTexture(),
                    cabinsAtlas.findRegion("cabin_9").getRegionX(),
                    cabinsAtlas.findRegion("cabin_9").getRegionY(),
                    80, 112),
            new TextureRegion(cabinsAtlas.createSprite("cabin_10").getTexture(),
                    cabinsAtlas.findRegion("cabin_10").getRegionX(),
                    cabinsAtlas.findRegion("cabin_10").getRegionY(),
                    80, 112),
            new TextureRegion(cabinsAtlas.createSprite("cabin_11").getTexture(),
                    cabinsAtlas.findRegion("cabin_11").getRegionX(),
                    cabinsAtlas.findRegion("cabin_11").getRegionY(),
                    80, 112),
            new TextureRegion(cabinsAtlas.createSprite("cabin_12").getTexture(),
                    cabinsAtlas.findRegion("cabin_12").getRegionX(),
                    cabinsAtlas.findRegion("cabin_12").getRegionY(),
                    80, 112),
    };

    private final HashMap<BuildingType, Texture> animalEnclosureTextureMap = new HashMap<>(){{
        put(BuildingType.Coop, new Texture("assets/Buildings/Coop.png"));
        put(BuildingType.BigCoop, new Texture("assets/Buildings/Big Coop.png"));
        put(BuildingType.DeluxeCoop, new Texture("assets/Buildings/Deluxe Coop.png"));
        put(BuildingType.Barn, new Texture("assets/Buildings/Barn.png"));
        put(BuildingType.BigBarn, new Texture("assets/Buildings/Big Barn.png"));
        put(BuildingType.DeluxeBarn, new Texture("assets/Buildings/Deluxe Barn.png"));
    }};

    private final TextureAtlas abigailAtlas = new TextureAtlas("assets/NPCs/Other/Abigail.atlas");
    private final TextureRegion abigailTexture = new TextureRegion(abigailAtlas.createSprite("standing_1"),
            abigailAtlas.findRegion("standing_1").getRegionX(),
            abigailAtlas.findRegion("standing_1").getRegionY(),
            16, 32);

    private final TextureAtlas harveyAtlas = new TextureAtlas("assets/NPCs/Other/Harvey.atlas");
    private final TextureRegion harveyTexture = new TextureRegion(harveyAtlas.createSprite("standing_1"),
            harveyAtlas.findRegion("standing_1").getRegionX(),
            harveyAtlas.findRegion("standing_1").getRegionY(),
            16, 32);

    private final TextureAtlas liaAtlas = new TextureAtlas("assets/NPCs/Other/Lia.atlas");
    private final TextureRegion liaTexture = new TextureRegion(liaAtlas.createSprite("standing_1"),
            liaAtlas.findRegion("standing_1").getRegionX(),
            liaAtlas.findRegion("standing_1").getRegionY(),
            16, 32);

    private final TextureAtlas robbinAtlas = new TextureAtlas("assets/NPCs/Other/Robbin.atlas");
    private final TextureRegion robbinTexture = new TextureRegion(robbinAtlas.createSprite("standing_1"),
            robbinAtlas.findRegion("standing_1").getRegionX(),
            robbinAtlas.findRegion("standing_1").getRegionY(),
            16, 32);

    private final TextureAtlas sebastianAtlas = new TextureAtlas("assets/NPCs/Other/Sebastian.atlas");
    private final TextureRegion sebastianTexture = new TextureRegion(sebastianAtlas.createSprite("standing_1"),
            sebastianAtlas.findRegion("standing_1").getRegionX(),
            sebastianAtlas.findRegion("standing_1").getRegionY(),
            16, 32);

    private final HashMap<NPCType, TextureRegion> NPCTextureMap = new HashMap<>(){{
        put(NPCType.Abigail, abigailTexture);
        put(NPCType.Harvey, harveyTexture);
        put(NPCType.Lia, liaTexture);
        put(NPCType.Robbin, robbinTexture);
        put(NPCType.Sebastian, sebastianTexture);

    }};

    private final HashMap<Item, Texture> itemTextureMap = new HashMap<>() {{
        for (ToolType toolType : ToolType.values())
            put(toolType, new Texture(Gdx.files.internal(toolType.getAddress())));

        for (SeedType seedType : SeedType.values())
            put(seedType, new Texture(Gdx.files.internal(seedType.getAddress())));

        for (SaplingType saplingType : SaplingType.values())
            put(saplingType, new Texture(Gdx.files.internal(saplingType.getAddress())));

        for (FruitType fruitType : FruitType.values())
            put(fruitType, new Texture(Gdx.files.internal(fruitType.getAddress())));

        for (MineralType mineralType : MineralType.values())
            put(mineralType, new Texture(Gdx.files.internal(mineralType.getAddress())));

        for (ShopItems shopItems : ShopItems.values())
            put(shopItems, new Texture(Gdx.files.internal(shopItems.getAddress())));

        for (AnimalProduct animalProduct : AnimalProduct.values())
            put(animalProduct, new Texture(Gdx.files.internal(animalProduct.getAddress())));

        for (CookingProduct cookingProduct : CookingProduct.values())
            put(cookingProduct, new Texture(Gdx.files.internal(cookingProduct.getAddress())));

        for (CraftingProduct craftingProduct : CraftingProduct.values())
            put(craftingProduct, new Texture(Gdx.files.internal(craftingProduct.getAddress())));

        for (FishType fishType : FishType.values())
            put(fishType, new Texture(Gdx.files.internal(fishType.getAddress())));
    }};

    private final Texture redCrossTexture = new Texture(Gdx.files.internal("Images/red-cross.png"));
    private final Image redCrossImage = new Image(redCrossTexture);

    private final TextureAtlas treeAtlas = new TextureAtlas("assets/Images/Trees/Trees.atlas");


    private final HashMap<Item, Image> itemImageMap = new HashMap<>() {{
        for (Entry<Item, Texture> entry : itemTextureMap.entrySet()) {
            put(entry.getKey(), new Image(entry.getValue()));
        }
    }};

    private final HashMap<PlantType, ArrayList<Texture>> plantTextureMap = new HashMap<>() {{
        for (CropType cropType : CropType.values()) {
            if (new File(cropType.getAddress()).isDirectory()) {
                int stageCount = cropType.getStages().length + 1;
                ArrayList<Texture> arrayList = new ArrayList<Texture>();
                for (int i = 1; i <= stageCount; i++) {
                    arrayList.add(new Texture(cropType.getAddress() + "/  (" + i + ").png"));
                }
                put(cropType, arrayList);
            } else {
                put(cropType, new ArrayList<>(){{
                    add(new Texture(Gdx.files.internal(cropType.getAddress())));
                }});
            }
        }
        for (TreeType treeType : TreeType.values()) {
            if (new File(treeType.getAddress()).isDirectory()) {
                int stageCount = treeType.getStages().length + 1;
                ArrayList<Texture> arrayList = new ArrayList<Texture>();
                for (int i = 1; i <= stageCount; i++) {
                    arrayList.add(new Texture(treeType.getAddress() + "/  (" + i + ").png"));
                }
                put(treeType, arrayList);
            }
        }
    }};

    private final HashMap<AnimalType, Texture> animalTextureMap = new HashMap<>(){{
        for (AnimalType animalType : AnimalType.values()) {
            put(animalType, new Texture("assets/Images/Animals/" + animalType.getName() + ".png"));
        }
    }};


    private final Animation<TextureRegion> walkDown = new Animation<>(
            0.1f,
            characterAtlas.createSprite("down_1"),
            characterAtlas.createSprite("down_2"),
            characterAtlas.createSprite("down_3"),
            characterAtlas.createSprite("down_4")
    );
    private final Animation<TextureRegion> walkRight = new Animation<>(
            0.1f,
            characterAtlas.createSprite("right_1"),
            characterAtlas.createSprite("right_2"),
            characterAtlas.createSprite("right_3"),
            characterAtlas.createSprite("right_4")
    );
    private final Animation<TextureRegion> walkUp = new Animation<>(
            0.1f,
            characterAtlas.createSprite("up_1"),
            characterAtlas.createSprite("up_2"),
            characterAtlas.createSprite("up_3"),
            characterAtlas.createSprite("up_4")
    );
    private final Animation<TextureRegion> walkLeft = new Animation<>(
            0.1f,
            characterAtlas.createSprite("left_1"),
            characterAtlas.createSprite("left_2"),
            characterAtlas.createSprite("left_3"),
            characterAtlas.createSprite("left_4")
    );

    private final Texture arrowTexture = new  Texture(Gdx.files.internal("Clock/Arrow.png"));

    {
//        musicPlayer.setCurrentTrack(Track.THEME_1);
    }

    private final Image craftingMenuBackground = new Image(new Texture(Gdx.files.internal("Images/Crafting/CraftingMenuMan.png")));
    private final Image inventoryMenuBackground = new Image(new Texture(Gdx.files.internal("Images/Inventory/InventoryMenuMan.png")));
    private final Image skillMenuBackground = new Image(new Texture(Gdx.files.internal("Images/Skill/SkillsMenuMan.png")));
    private final Image exitMenuBackground = new Image(new Texture(Gdx.files.internal("Images/Exit/ExitMenuMan2.png")));


    private final Image craft_bomb = new Image(new Texture(Gdx.files.internal("Images/Crafting/Bomb.png")));
    private final Image craft_charcoalKiln = new Image(new Texture(Gdx.files.internal("Images/Crafting/Charcoal_Kiln.png")));
    private final Image craft_cherry_Bomb = new Image(new Texture(Gdx.files.internal("Images/Crafting/Cherry_Bomb.png")));
    private final Image craft_furnace = new Image(new Texture(Gdx.files.internal("Images/Crafting/Furnace.png")));
    private final Image craft_iridiumSprinkler = new Image(new Texture(Gdx.files.internal("Images/Crafting/Iridium_Sprinkler.png")));
    private final Image craft_qualitySprinkler = new Image(new Texture(Gdx.files.internal("Images/Crafting/Quality_Sprinkler.png")));
    private final Image craft_sprinkler = new Image(new Texture(Gdx.files.internal("Images/Crafting/Sprinkler.png")));
    private final Image craft_megaBomb = new Image(new Texture(Gdx.files.internal("Images/Crafting/Mega_Bomb.png")));
    private final Image craft_scarecrow = new Image(new Texture(Gdx.files.internal("Images/Crafting/Scarecrow.png")));
    private final Image craft_deluxeScarecrow = new Image(new Texture(Gdx.files.internal("Images/Crafting/Deluxe_Scarecrow.png")));
    private final Image craft_beeHouse = new Image(new Texture(Gdx.files.internal("Images/Crafting/Bee_House.png")));
    private final Image craft_cheesePress = new Image(new Texture(Gdx.files.internal("Images/Crafting/Cheese_Press.png")));
    private final Image craft_loom = new Image(new Texture(Gdx.files.internal("Images/Crafting/Loom.png")));
    private final Image craft_keg = new Image(new Texture(Gdx.files.internal("Images/Crafting/Keg.png")));
    private final Image craft_mayonnaiseMachine = new Image(new Texture(Gdx.files.internal("Images/Crafting/Mayonnaise_Machine.png")));
    private final Image craft_preservesJar = new Image(new Texture(Gdx.files.internal("Images/Crafting/Preserves_Jar.png")));
    private final Image craft_oilMaker = new Image(new Texture(Gdx.files.internal("Images/Crafting/Oil_Maker.png")));
    private final Image craft_dehydrator = new Image(new Texture(Gdx.files.internal("Images/Crafting/Dehydrator.png")));
    private final Image craft_grassStarter = new Image(new Texture(Gdx.files.internal("Images/Crafting/Grass_Starter.png")));
    private final Image craft_fishSmoker = new Image(new Texture(Gdx.files.internal("Images/Crafting/Fish_Smoker.png")));
    private final Image craft_mysticTreeSeed = new Image(new Texture(Gdx.files.internal("Images/Crafting/Mystic_Tree_Seed.png")));

    private final Image cookingMenuBackground = new Image(new Texture(Gdx.files.internal("Images/Cooking/CookingMenu.png")));

    private final Image hoveringInfoWindow = new Image(new Texture(Gdx.files.internal("Images/HoveringInfoWindow.png")));
    private final Image hoveringMiningWindow = new Image(new Texture(Gdx.files.internal("Images/Skill/HoverMining.png")));
    private final Image hoveringFarmingWindow = new Image(new Texture(Gdx.files.internal("Images/Skill/HoverFarming.png")));
    private final Image hoveringFishingWindow = new Image(new Texture(Gdx.files.internal("Images/Skill/HoverFishing.png")));
    private final Image hoveringForagingWindow = new Image(new Texture(Gdx.files.internal("Images/Skill/HoverForaging.png")));

    private final Texture skillPointImage = new Texture(Gdx.files.internal("Images/Skill/SkillPoint.png"));

    private final Image socialMenuBackgroundImage = new Image(new Texture(Gdx.files.internal("Images/Social/SocialMenuMan.png")));

    private final Texture npc1Avatar = new Texture(Gdx.files.internal("NPCs/Other/Abigail.png"));
    private final Texture npc2Avatar = new Texture(Gdx.files.internal("NPCs/Other/Harvey.png"));
    private final Texture npc3Avatar = new Texture(Gdx.files.internal("NPCs/Other/Lia.png"));
    private final Texture npc4Avatar = new Texture(Gdx.files.internal("NPCs/Other/Robbin.png"));
    private final Texture npc5Avatar = new Texture(Gdx.files.internal("NPCs/Other/Sebastian.png"));

    private final Texture checkBox = new Texture(Gdx.files.internal("Images/Social/CheckedBox.png"));

    private final Texture socialButton = new Texture(Gdx.files.internal("Images/PlayerSocial/SocialButton.png"));

    private final Image playerSocialBackground = new Image( new Texture(Gdx.files.internal("Images/PlayerSocial/PlayerSocialBackground.png")));

    private final Image mapMenuBackground = new Image( new Texture(Gdx.files.internal("Images/Map/MapMenu.png")));

    private final Image energyBar = new Image( new Texture(Gdx.files.internal("EnergyIcons/EnergyBar2.png")));
    private final Image boostBar = new Image( new Texture(Gdx.files.internal("EnergyIcons/EnergyBar2.png")));
    private final Image greenBar = new Image( new Texture(Gdx.files.internal("EnergyIcons/GreenBar.png")));
    private final Image redBar = new Image( new Texture(Gdx.files.internal("EnergyIcons/RedBar.png")));

    private final Sound thorSound = Gdx.audio.newSound(Gdx.files.internal("Thor/ThorSFX.wav"));

    private final Texture thor1 = new Texture(Gdx.files.internal("Thor/Lightning1.png"));
    private final Texture thor2 = new Texture(Gdx.files.internal("Thor/Lightning2.png"));
    private final Texture thor3 = new Texture(Gdx.files.internal("Thor/Lightning3.png"));
    private final Texture thor4 = new Texture(Gdx.files.internal("Thor/Lightning4.png"));

    private final Animation<Texture> thorAnimationFrames = new Animation<>(0.25f, thor1,thor2,thor3,thor4);

    private final Image tradeInventoryBackground = new Image( new Texture(Gdx.files.internal("Images/Trade/InventoryBackground2.png")));

    private final Image selectSlot = new Image( new Texture(Gdx.files.internal("Images/Trade/SelectedSlot.png")));

    private final Image messageBackgroundImage = new Image( new Texture(Gdx.files.internal("Images/Inbox/InboxMessageBackground.png")));
    private final Image messageAlertImage = new Image( new Texture(Gdx.files.internal("Images/Inbox/MessageAlert.png")));

    public static GameAssetManager getGameAssetManager() {

        if (gameAssetManager == null) {
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;

    }

    public Image getMessageAlertImage() {
        return messageAlertImage;
    }

    public Image getMessageBackgroundImage() {
        return messageBackgroundImage;
    }

    public Image getSelectSlot() {
        return selectSlot;
    }

    public Image getTradeInventoryBackground() {
        return tradeInventoryBackground;
    }

    public Animation<Texture> getThorAnimationFrames() {
        return thorAnimationFrames;
    }

    public Image getRedBar() {
        return redBar;
    }

    public Image getBoostBar() {
        return boostBar;
    }

    public Sound getThorSound() {
        return thorSound;
    }

    public Image getGreenBar() {
        return greenBar;
    }

    public Image getEnergyBar() {
        return energyBar;
    }

    public Image getMapMenuBackground() {
        return mapMenuBackground;
    }

    public Texture getSocialButton() {
        return socialButton;
    }

    public Image getPlayerSocialBackground() {
        return playerSocialBackground;
    }

    public Texture getCheckBox() {
        return checkBox;
    }

    public Texture getPlayerSocialBackGroundTexture() {
        return THEFUCKINGTEXTURE;
    }

    public Texture getAnimalEnclosureTexture(BuildingType buildingType) {
        return animalEnclosureTextureMap.getOrDefault(buildingType, null);
    }

    public Image getNpc5Avatar() {
        TextureRegion region = new TextureRegion(npc5Avatar);
        region.flip(false, false);
        return new Image(region);
    }

    public Image getNpc4Avatar() {
        TextureRegion region = new TextureRegion(npc4Avatar);
        region.flip(true, false);
        return new Image(region);
    }

    public Image getNpc3Avatar() {
        TextureRegion region = new TextureRegion(npc3Avatar);
        region.flip(true, false);
        return new Image(region);
    }

    public Image getNpc2Avatar() {
        TextureRegion region = new TextureRegion(npc2Avatar);
        region.flip(true, false);
        return new Image(region);
    }

    public Image getNpc1Avatar() {
        TextureRegion region = new TextureRegion(npc1Avatar);
        region.flip(true, false);
        return new Image(region);
    }

    public Texture getPassOutTexture() {
        return passOutTexture;
    }

    public Image getSocialMenuBackgroundImage() {
        return socialMenuBackgroundImage;
    }

    public Texture getSkillPointImage() {
        return skillPointImage;
    }

    public Image getHoveringMiningWindow() {
        return hoveringMiningWindow;
    }

    public Image getHoveringFarmingWindow() {
        return hoveringFarmingWindow;
    }

    public Image getHoveringFishingWindow() {
        return hoveringFishingWindow;
    }

    public Image getHoveringForagingWindow() {
        return hoveringForagingWindow;
    }

    public Image getHoveringInfoWindow() {
        return hoveringInfoWindow;
    }

    public Image getCookingMenuBackground() {
        return cookingMenuBackground;
    }

    public Image getBlackImage() {
        return blackImage;
    }

    public Texture getLightGrassCellTexture() {
        return lightGrassCellTexture;
    }

    public Texture getDarkGrassCellTexture() {
        return darkGrassCellTexture;
    }

    public Texture getDarkGrass2CellTexture() {
        return darkGrass2CellTexture;
    }

    public Texture getRedTileCellTexture() {
        return RedTileCellTexture;
    }

    public Texture getGreenTileCellTexture() {
        return GreenTileCellTexture;
    }

    public Texture getMapLinkCellTexture() {
        return mapLinkCellTexture;
    }

    public Texture getNPCDialogueSign() {
        return NPCDialogueSign;
    }

    public Texture getNpcDialogueBox() {
        return npcDialogueBox;
    }

    public Texture getHoedCellTexture() {
        return hoedCellTexture;
    }

    public Texture getHoedAndWateredCellTexture() {
        return hoedAndWateredCellTexture;
    }

    public Image getCraft_bomb() {
        return craft_bomb;
    }

    public Image getCraft_charcoalKiln() {
        return craft_charcoalKiln;
    }

    public Image getCraft_cherry_Bomb() {
        return craft_cherry_Bomb;
    }

    public Image getCraft_furnace() {
        return craft_furnace;
    }

    public Image getCraft_iridiumSprinkler() {
        return craft_iridiumSprinkler;
    }

    public Image getCraft_qualitySprinkler() {
        return craft_qualitySprinkler;
    }

    public Image getCraft_sprinkler() {
        return craft_sprinkler;
    }

    public Image getCraft_megaBomb() {
        return craft_megaBomb;
    }

    public Image getCraft_scarecrow() {
        return craft_scarecrow;
    }

    public Image getCraft_deluxeScarecrow() {
        return craft_deluxeScarecrow;
    }

    public Image getCraft_beeHouse() {
        return craft_beeHouse;
    }

    public Image getCraft_cheesePress() {
        return craft_cheesePress;
    }

    public Image getCraft_loom() {
        return craft_loom;
    }

    public Image getCraft_keg() {
        return craft_keg;
    }

    public Image getCraft_mayonnaiseMachine() {
        return craft_mayonnaiseMachine;
    }

    public Image getCraft_preservesJar() {
        return craft_preservesJar;
    }

    public Image getCraft_oilMaker() {
        return craft_oilMaker;
    }

    public Image getCraft_dehydrator() {
        return craft_dehydrator;
    }

    public Image getCraft_grassStarter() {
        return craft_grassStarter;
    }

    public Image getCraft_fishSmoker() {
        return craft_fishSmoker;
    }

    public Image getCraft_mysticTreeSeed() {
        return craft_mysticTreeSeed;
    }

    public Image getCraftingMenuBackground(){

        return craftingMenuBackground;

    }

    public Image getInventoryMenuBackground() {
        return inventoryMenuBackground;
    }

    public Image getSkillMenuBackground() {
        return skillMenuBackground;
    }

    public Image getExitMenuBackground() {
        return exitMenuBackground;
    }

    public Image getRainyFall() {
        return rainyFall;
    }

    public Image getStormyFall() {
        return stormyFall;
    }

    public Image getSunnyFall() {
        return sunnyFall;
    }

    public Image getSnowyFall() {
        return snowyFall;
    }

    public Image getRainyWinter() {
        return rainyWinter;
    }

    public Image getStormyWinter() {
        return stormyWinter;
    }

    public Image getSunnyWinter() {
        return sunnyWinter;
    }

    public Image getSnowyWinter() {
        return snowyWinter;
    }

    public Image getRainySpring() {
        return rainySpring;
    }

    public Image getStormySpring() {
        return stormySpring;
    }

    public Image getSunnySpring() {
        return sunnySpring;
    }

    public Image getSnowySpring() {
        return snowySpring;
    }

    public Image getRainySummer() {
        return rainySummer;
    }

    public Image getStormySummer() {
        return stormySummer;
    }

    public Image getSunnySummer() {
        return sunnySummer;
    }

    public Image getSnowySummer() {
        return snowySummer;
    }

    public Texture getArrowTexture() {
        return arrowTexture;
    }

    public Texture getMap1() {
        return map1;
    }

    public Texture getMap2() {
        return map2;
    }

    public Texture getMap3() {
        return map3;
    }

    public Texture getMap4() {
        return map4;
    }

    public Skin getSkin() {
        return skin;
    }

    public Texture getMenuBackground() {
        return background;
    }

    public Image getStardewValleyText() {
        return new Image(StardewValleyText);
    }

    public Sound getClickSound() {
        return clickSound;
    }

    public Color getErrorColor() {
        return errorColor;
    }

    public Color getAcceptColor() {
        return acceptColor;
    }

    public TextureAtlas getCharacterAtlas() {
        return characterAtlas;
    }

    public Animation<TextureRegion> getWalkDown() {
        return walkDown;
    }

    public Animation<TextureRegion> getWalkRight() {
        return walkRight;
    }

    public Animation<TextureRegion> getWalkUp() {
        return walkUp;
    }

    public Animation<TextureRegion> getWalkLeft() {
        return walkLeft;
    }

    public Texture getHomeTexture() {
        return homeTexture;
    }

    public Texture getFreeCellTexture() {
        return freeCellTexture;
    }

    public Texture getGreenHouseTexture() {
        return greenHouseTexture;
    }

    public Texture getQuarryCellTexture() {
        return quarryCellTexture;
    }

    public Texture getNpcMapCellTexture() {
        return NpcMapCellTexture;
    }

    public Texture getWaterCellTexture() {
        return WaterCellTexture;
    }

    public Texture getHutTexture() {
        return hutTexture;
    }

    public Texture getInventoryHotBar() {
        return inventoryHotBar;
    }

    public TextureRegion getStandingSprite() {
        return standingTexture;
    }

    public Texture getInventorySelectSlot() {
        return inventorySelectSlot;
    }

    public Texture getCoinTexture() {
        return coinTexture;
    }

    public Texture getItemTexture(Item item) {
        if (itemTextureMap.containsKey(item))
            return itemTextureMap.get(item);
        return redCrossTexture;
    }

    public Image getItemImage(Item item) {
        if (itemImageMap.containsKey(item))
            return itemImageMap.get(item);
        return redCrossImage;
    }

    public Texture getAnimalTexture(AnimalType animalType) {
        return animalTextureMap.getOrDefault(animalType, null);
    }

    public Texture getPlantTexture(PlantType plantType, int index) {
        return plantTextureMap.get(plantType).get(index);
    }

    public Texture getCropTexture(CropType cropType, int index) {
        return plantTextureMap.get(cropType).get(index);
    }

    public Texture getTreeTexture(TreeType treeType, int index) {
        return plantTextureMap.get(treeType).get(index);
    }

    public Texture getPierresGeneralTexture() {
        return pierresGeneralTexture;
    }

    public Texture getJojaMartTexture() {
        return jojaMartTexture;
    }

    public Texture getStarDropTexture() {
        return starDropTexture;
    }

    public TextureRegion getCabinTextureRegion(int index) {
        return cabinTextures[index];
    }

    public Texture getAvatarTexture(int id) {
        return avatarTextures[id];
    }

    public Texture getPlowedCellTexture() {
        return plowedCellTexture;
    }

    public TextureRegion getAbigailTexture() {
        return abigailTexture;
    }

    public TextureRegion getHarveyTexture() {
        return harveyTexture;
    }

    public TextureRegion getLiaTexture() {
        return liaTexture;
    }

    public TextureRegion getRobbinTexture() {
        return robbinTexture;
    }

    public TextureRegion getSebastianTexture() {
        return sebastianTexture;
    }

    public TextureRegion getNpcTexture(NPCType npcType) {
        return NPCTextureMap.get(npcType);
    }
}
