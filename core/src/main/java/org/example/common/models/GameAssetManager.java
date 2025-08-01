package org.example.common.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.example.server.models.Item;
import org.example.server.models.enums.Plants.*;
import org.example.server.models.enums.items.FishType;
import org.example.server.models.enums.items.MineralType;
import org.example.server.models.enums.items.ShopItems;
import org.example.server.models.enums.items.ToolType;
import org.example.server.models.enums.items.products.AnimalProduct;
import org.example.server.models.enums.items.products.CookingProduct;
import org.example.server.models.enums.items.products.CraftingProduct;
import org.example.server.models.utils.MusicPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GameAssetManager {


    private static GameAssetManager gameAssetManager;

    private final Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));

    private final Texture background = new Texture(Gdx.files.internal("Images/menu_background.png"));
    private final Texture StardewValleyText = new Texture(Gdx.files.internal("Images/stardew_valley_text.png"));

    private final MusicPlayer musicPlayer = new MusicPlayer();

    private final Sound clickSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/click.wav"));

    private final Color errorColor = new Color(1, 0.31f, 0, 1);
    private final Color acceptColor = new Color(0.216f, 0.831f, 0.255f, 1);

    private final Texture map1 = new Texture(Gdx.files.internal("MapImage/avatar7.png"));
    private final Texture map2 = new Texture(Gdx.files.internal("MapImage/avatar10.png"));
    private final Texture map3 = new Texture(Gdx.files.internal("MapImage/avatar11.png"));
    private final Texture map4 = new Texture(Gdx.files.internal("MapImage/avatar15.png"));

    private final Texture rainyFall = new Texture(Gdx.files.internal("Clock/RainyFall.png"));
    private final Texture stormyFall = new Texture(Gdx.files.internal("Clock/StormyFall.png"));
    private final Texture sunnyFall = new Texture(Gdx.files.internal("Clock/SunnyFall.png"));
    private final Texture snowyFall = new Texture(Gdx.files.internal("Clock/SnowyFall.png"));
    private final Texture rainyWinter = new Texture(Gdx.files.internal("Clock/RainyWinter.png"));
    private final Texture stormyWinter = new Texture(Gdx.files.internal("Clock/StormyWinter.png"));
    private final Texture sunnyWinter = new Texture(Gdx.files.internal("Clock/SunnyWinter.png"));
    private final Texture snowyWinter = new Texture(Gdx.files.internal("Clock/SnowyWinter.png"));
    private final Texture rainySpring = new Texture(Gdx.files.internal("Clock/RainySpring.png"));
    private final Texture stormySpring = new Texture(Gdx.files.internal("Clock/StormySpring.png"));
    private final Texture sunnySpring = new Texture(Gdx.files.internal("Clock/SunnySpring.png"));
    private final Texture snowySpring = new Texture(Gdx.files.internal("Clock/SnowySpring.png"));
    private final Texture rainySummer = new Texture(Gdx.files.internal("Clock/RainySummer.png"));
    private final Texture stormySummer = new Texture(Gdx.files.internal("Clock/StormySummer.png"));
    private final Texture sunnySummer = new Texture(Gdx.files.internal("Clock/SunnySummer.png"));
    private final Texture snowySummer = new Texture(Gdx.files.internal("Clock/SnowySummer.png"));

    private final Texture homeTexture = new Texture("assets/Images/home_cropped.png");
    private final Texture greenHouseTexture = new Texture("assets/Images/greenhouse2.1.png");
    private final Texture hutTexture = new Texture("assets/Images/hut.png");

    private final Texture freeCellTexture = new Texture("assets/Images/Floorings/Free4.png");
    private final Texture quarryCellTexture = new Texture("assets/Images/Floorings/Quarry.png");
    private final Texture NpcMapCellTexture = new Texture("assets/Images/Floorings/NPCValley.png");
    private final Texture WaterCellTexture = new Texture("assets/Images/Floorings/Water.png");

    private final TextureAtlas characterAtlas = new TextureAtlas("assets/Character/character.atlas");
    private final Sprite standingSprite = characterAtlas.createSprite("standing_1");

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

    private final TextureAtlas treeAtlas = new TextureAtlas("assets/Images/Trees/Trees.atlas");

    private final HashMap<TreeType, ArrayList<TextureRegion>> treeTextureMap = new HashMap<>() {{
        for (TreeType treeType : TreeType.values()) {
            if (treeType.getAddresses() != null) {
                put(treeType, new ArrayList<TextureRegion>() {{
                    for (String address : treeType.getAddresses()) {
                        add(new TextureRegion(treeAtlas.createSprite(address).getTexture(),
                                treeAtlas.createSprite(address).getRegionX(),
                                treeAtlas.createSprite(address).getRegionY(),
                                48, 80));
                    }
                }});
            }
        }
    }};

    private final TextureAtlas cropsAtlas = new TextureAtlas("assets/Images/Crops/atlas/Crops.atlas");

    private final HashMap<CropType, ArrayList<TextureRegion>> cropTextureMap = new HashMap<>() {{
        for (CropType cropType : CropType.values()) {
            if (cropType.getAddress() == null) {
                put(cropType, new ArrayList<>(){{

                }});
            }
        }
    }};

    private final HashMap<Item, Image> itemImageMap = new HashMap<>() {{
        for (Entry<Item, Texture> entry : itemTextureMap.entrySet()) {
            put(entry.getKey(), new Image(entry.getValue()));
        }
    }};

    private final HashMap<PlantType, Texture> plantTextureMap = new HashMap<>() {{
        for (CropType cropType : CropType.values()) {
            put(cropType, new Texture(Gdx.files.internal(cropType.getAddress())));
        }
    }};

    private final Animation<Sprite> walkDown = new Animation<>(
            0.1f,
            characterAtlas.createSprite("down_1"),
            characterAtlas.createSprite("down_2"),
            characterAtlas.createSprite("down_3"),
            characterAtlas.createSprite("down_4")
    );
    private final Animation<Sprite> walkRight = new Animation<>(
            0.1f,
            characterAtlas.createSprite("right_1"),
            characterAtlas.createSprite("right_2"),
            characterAtlas.createSprite("right_3"),
            characterAtlas.createSprite("right_4")
    );
    private final Animation<Sprite> walkUp = new Animation<>(
            0.1f,
            characterAtlas.createSprite("up_1"),
            characterAtlas.createSprite("up_2"),
            characterAtlas.createSprite("up_3"),
            characterAtlas.createSprite("up_4")
    );
    private final Animation<Sprite> walkLeft = new Animation<>(
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
    private final Image exitMenuBackground = new Image(new Texture(Gdx.files.internal("Images/Exit/ExitMenuMan.png")));


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


    public static GameAssetManager getGameAssetManager() {

        if (gameAssetManager == null) {
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;

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

    public Texture getRainyFall() {
        return rainyFall;
    }

    public Texture getStormyFall() {
        return stormyFall;
    }

    public Texture getSunnyFall() {
        return sunnyFall;
    }

    public Texture getSnowyFall() {
        return snowyFall;
    }

    public Texture getRainyWinter() {
        return rainyWinter;
    }

    public Texture getStormyWinter() {
        return stormyWinter;
    }

    public Texture getSunnyWinter() {
        return sunnyWinter;
    }

    public Texture getSnowyWinter() {
        return snowyWinter;
    }

    public Texture getRainySpring() {
        return rainySpring;
    }

    public Texture getStormySpring() {
        return stormySpring;
    }

    public Texture getSunnySpring() {
        return sunnySpring;
    }

    public Texture getSnowySpring() {
        return snowySpring;
    }

    public Texture getRainySummer() {
        return rainySummer;
    }

    public Texture getStormySummer() {
        return stormySummer;
    }

    public Texture getSunnySummer() {
        return sunnySummer;
    }

    public Texture getSnowySummer() {
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

    public Animation<Sprite> getWalkDown() {
        return walkDown;
    }

    public Animation<Sprite> getWalkRight() {
        return walkRight;
    }

    public Animation<Sprite> getWalkUp() {
        return walkUp;
    }

    public Animation<Sprite> getWalkLeft() {
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

    public Sprite getStandingSprite() {
        return standingSprite;
    }

    public Texture getInventorySelectSlot() {
        return inventorySelectSlot;
    }

    public Texture getCoinTexture() {
        return coinTexture;
    }

    public Texture getItemTexture(Item item) {
        return itemTextureMap.get(item);
    }

    public Image getItemImage(Item item) {
        return itemImageMap.get(item);
    }

    public Texture getPlantTexture(PlantType plantType) {
        return plantTextureMap.get(plantType);
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

    public HashMap<TreeType, ArrayList<TextureRegion>> getTreeTextureMap() {
        return treeTextureMap;
    }
}
