package org.example.client.controller;

import org.example.client.Main;
import org.example.client.controller.menus.MenuController;
import org.example.client.model.ClientApp;
import org.example.client.view.HomeView;
import org.example.client.view.shopview.*;
import org.example.common.models.ItemManager;
import org.example.common.models.Message;
import org.example.server.models.*;
import org.example.server.models.AnimalProperty.Animal;
import org.example.server.models.Map.*;
import org.example.server.models.Map.Map;
import org.example.server.models.Shops.Shop;
import org.example.server.models.enums.*;
import org.example.server.models.enums.Plants.*;
import org.example.server.models.enums.Seasons.Season;
import org.example.server.models.enums.Weathers.Weather;
import org.example.server.models.enums.items.*;
import org.example.server.models.enums.items.products.AnimalProduct;
import org.example.server.models.enums.items.products.CookingProduct;
import org.example.server.models.enums.items.products.CraftingProduct;
import org.example.server.models.enums.items.products.ProcessedProductType;
import org.example.client.view.GameView;

import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class GameMenuController extends MenuController {
    private GameView view;

    public GameMenuController(GameView view) {
        this.view = view;
    }

    public Result enterMenu(String menuName) {
        Menu newMenu = Menu.getMenu(menuName);
        if (newMenu == null)
            return new Result(false, "menu doesn't exist!");
        if (newMenu == Menu.MainMenu)
            return exitMenu();
        else if (newMenu == Menu.Home)
            return goToHome();
        else if (newMenu.isShop())
            return goToShop(menuName);
        return new Result(false, "can't enter this menu!");
    }

    public Result exitMenu() {
        ServerGame serverGame = App.getCurrentGame();
        if (!serverGame.getCurrentPlayer().getUsername().equals(serverGame.getAdmin().getUsername()))
            return new Result(false, "You cannot end this game!");
        App.setCurrentGame(null);
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "Redirecting to main menu ...");
    }

    public boolean playerPassedOut() {
        return App.getCurrentGame().getCurrentPlayer().hasPassedOut();
    }

    public Result nextTurn(Scanner scanner) {
        boolean fullTurn = App.getCurrentGame().nextPlayer();
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();

        if (fullTurn)
            App.getCurrentGame().getTime().passAnHour();

        if (currentPlayer.hasPassedOut())
            return nextTurn(scanner);

        App.setCurrentMenu(currentPlayer.getCurrentMenu());
        String result =
                currentPlayer.getUsername() + "'s turn!\n" +
                        App.getCurrentGame().getCurrentPlayer().getNotification();
        return new Result(true, result);
    }

    public Result terminateGame(Scanner scanner) {
        ArrayList<Player> players = App.getCurrentGame().getPlayers();
        int opposes = 0;
        for (Player player : players) {
            if (player != App.getCurrentGame().getCurrentPlayer())
                opposes += getPlayerVote(player, scanner);
        }
        if (opposes == 0) {
            eraseGame();
            return new Result(true, "Redirecting to main menu ...");
        }
        return new Result(false, "Can't terminate this game!");
    }

    public Result showWeather() {
        return new Result(true, "The Current State Of Weather is " +
                App.getCurrentGame().getCurrentWeather() + " Weather!");
    }

    public Result forecastWeather() {
        ServerGame serverGame = App.getCurrentGame();
        Weather weather;
        if (serverGame.getTomorrowWeather() != null) weather = serverGame.getTomorrowWeather();
        else serverGame.setTomorrowWeather(weather = serverGame.getTime().getSeason().pickARandomWeather());
        return new Result(true, "The Weather Forecasted For Tomorrow is " +
                weather.toString() + " Weather!");
    }

    public Result showTime() {
        return new Result(true, "The current time is: " + App.getCurrentGame().getTime().getHour());
    }

    public Result showDate() {
        return new Result(true, "The current date is: " + App.getCurrentGame().getTime().getSeason() +
                " " + App.getCurrentGame().getTime().getDate());
    }

    public Result showDateTime() {
        return new Result(true, "The current date&time is: " +
                App.getCurrentGame().getTime().getDateTime());
    }

    public Result showDayOfWeek() {
        return new Result(true, "The current weekday is: " +
                App.getCurrentGame().getTime().getDayOfWeek());
    }

    public Result showSeason() {
        return new Result(true, "The current season is: " + App.getCurrentGame().getTime().getSeason());
    }

    public Result walk(String s, String t) {
        return walk(s, t, new Scanner(""));
    }

    public Result walk(String s, String t, Scanner scanner) {

        int i = Integer.parseInt(s), j = Integer.parseInt(t);


//        System.out.println("Walking to " + i + " and " + j);
        Player currentPlayer = ClientApp.getCurrentGame().getCurrentPlayer();
        Map currentMap = currentPlayer.getCurrentMap();
        Cell destination = currentMap.getCell(i, j);
        if (destination == null)
            return new Result(false, "Invalid cell");
        if (!currentMap.areConnected(currentPlayer.getCurrentCell(), destination)) {
            return new Result(false, "There Is No Path Between These Cells");
        } else {
            int energy = currentMap.getPathEnergy(currentPlayer.getCurrentCell(), destination);
//            System.out.println("The Energy Needed for This Walk is " +
//                    energy + " And You Have " + currentPlayer.getEnergy() +
//                    ", Would You Like To Walk? (Y/N)");
            //String answer = scanner.nextLine();
            while (true) {
                //if (answer.trim().equals("Y")) {
                    if (currentPlayer.getEnergy() > energy) {
                        currentPlayer.consumeEnergy(energy);
                        currentPlayer.setCurrentCell(destination);
                        if (destination.getType() == CellType.MapLink) {
                            if (((Cell) destination.getObject()).getMap() == currentPlayer.getFarmMap() ||
                                    ((Cell) destination.getObject()).getMap() == ClientApp.getCurrentGame().getNpcMap() ||
                                    (currentPlayer.getSpouse() != null &&
                                            ((Cell) destination.getObject()).getMap() == currentPlayer.getSpouse().getFarmMap())) {
                                Cell newDestination = (Cell) destination.getObject();
                                currentPlayer.setCurrentCell(newDestination);
                                currentPlayer.setCurrentMap(newDestination.getMap());
                                if (((Cell) destination.getObject()).getMap() instanceof NPCMap) {
                                    ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
                                        put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
                                        put("username", currentPlayer.getUsername());
                                        put("x", ((Cell) destination.getObject()).getPosition().getX());
                                        put("y", ((Cell) destination.getObject()).getPosition().getY());
                                    }}, Message.Type.enter_npc));
                                }
                                if (((Cell) destination.getObject()).getMap() instanceof FarmMap) {
                                    ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
                                        put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
                                        put("username", currentPlayer.getUsername());
                                        put("x", ((Cell) destination.getObject()).getPosition().getX());
                                        put("y", ((Cell) destination.getObject()).getPosition().getY());
                                    }}, Message.Type.leave_npc));
                                }
                                return new Result(true, "You Changed your Map And Now Are On Cell(" +
                                        newDestination.getPosition().getX() + "," +
                                        newDestination.getPosition().getY() + ") of " +
                                        newDestination.getMap().getClass().getSimpleName());
                            } else {
                                return new Result(true, "You Walked But Are Not Able to Change Your Map!!");
                            }
                        } else if (destination.getType() == CellType.Door) {
                            if (destination.getBuilding() instanceof StoreBuilding storeBuilding) {
                                currentPlayer.setCurrentCell(destination.getAdjacentCells().get(2));
                                int time = ClientApp.getCurrentGame().getTime().getHour();
                                if (storeBuilding.getStore().getShopType().getStartTime() > time ||
                                        storeBuilding.getStore().getShopType().getEndTime() < time) {
                                    return new Result(false,
                                            "The shop you want to enter is closed at the moment.");
                                }
                                switch (storeBuilding.getStore().getShopType()) {
                                    case ShopType.CarpenterShop -> {
                                        currentPlayer.setCurrentMenu(Menu.CarpenterShop);
                                        ClientApp.setCurrentMenu(new CarpenterShop());
                                        Main.getMain().getScreen().dispose();
                                        Main.getMain().setScreen(ClientApp.getCurrentMenu());
                                        return new Result(true, "You Entered The Carpenter Shop!");
                                    }
                                    case ShopType.FishShop -> {
                                        currentPlayer.setCurrentMenu(Menu.FishShop);
                                        ClientApp.setCurrentMenu(new FishShop());
                                        Main.getMain().getScreen().dispose();
                                        Main.getMain().setScreen(ClientApp.getCurrentMenu());
                                        return new Result(true, "You Entered The Fish Shop!");
                                    }
                                    case ShopType.Blacksmith -> {
                                        currentPlayer.setCurrentMenu(Menu.BlackSmithShop);
                                        ClientApp.setCurrentMenu(new BlackSmithShop());
                                        Main.getMain().getScreen().dispose();
                                        Main.getMain().setScreen(ClientApp.getCurrentMenu());
                                        return new Result(true, "You Entered The Blacksmith Shop!");
                                    }
                                    case ShopType.JojaMart -> {
                                        currentPlayer.setCurrentMenu(Menu.JojaMartShop);
                                        ClientApp.setCurrentMenu(new JojaMartShop());
                                        Main.getMain().getScreen().dispose();
                                        Main.getMain().setScreen(ClientApp.getCurrentMenu());
                                        return new Result(true, "You Entered The Joja Mart Shop!");
                                    }
                                    case ShopType.MarnieRanch -> {
                                        currentPlayer.setCurrentMenu(Menu.MarnieRanch);
                                        ClientApp.setCurrentMenu(new MarnieRanch());
                                        Main.getMain().getScreen().dispose();
                                        Main.getMain().setScreen(ClientApp.getCurrentMenu());
                                        return new Result(true, "You Entered The Marnie Ranch!");
                                    }
                                    case ShopType.PierreGeneralStore -> {
                                        currentPlayer.setCurrentMenu(Menu.PierreGeneralShop);
                                        ClientApp.setCurrentMenu(new PierreGeneralShop());
                                        Main.getMain().getScreen().dispose();
                                        Main.getMain().setScreen(ClientApp.getCurrentMenu());
                                        return new Result(true, "You Entered The Pierre General Shop!");
                                    }
                                    case ShopType.StardropSaloon -> {
                                        currentPlayer.setCurrentMenu(Menu.StardropSaloonShop);
                                        ClientApp.setCurrentMenu(new StardropSaloonShop());
                                        Main.getMain().getScreen().dispose();
                                        Main.getMain().setScreen(ClientApp.getCurrentMenu());
                                        return new Result(true, "You Entered The Stardrop Saloon Shop!");
                                    }
                                }
                            } else if (destination.getBuilding() instanceof Hut) {
                                currentPlayer.setCurrentCell(destination.getAdjacentCells().get(2));
                                currentPlayer.setCurrentMenu(Menu.Home);
                                App.setCurrentMenu(Menu.Home);
                                Main.getMain().getScreen().dispose();
                                Main.getMain().setScreen(new HomeView());
                                Main.getBatch().getProjectionMatrix().setToOrtho2D(0, 0, 1920, 1080);
                                return new Result(true, "You Entered Your Home :)");
                            }
                        }
                        return new Result(true, "You Walked And Now Are On Cell(" +
                                i + "," + j + ")");
                    } else {

                        Cell trueDestination = currentMap.getPlaceInPath(currentPlayer.getCurrentCell(), destination,
                                currentPlayer.getEnergy());
                        currentPlayer.consumeEnergy(100000);

                        currentPlayer.setCurrentCell(trueDestination);
                        return new Result(false, "You Passed Out In Cell (" +
                                trueDestination.getPosition().getX() + ", " +
                                trueDestination.getPosition().getY() + ") !");
                    }
//                } else if (answer.trim().equals("N")) {
//                    return new Result(false, "Alright.");
//                }
                //System.out.println("Invalid Response, Please Answer By (Y/N)");
                //answer = scanner.nextLine();
            }
        }
    }

    public Result printMap(String s, String t, String sizeString) {

        int x = Integer.parseInt(s), y = Integer.parseInt(t), size = Integer.parseInt(sizeString);
        String view = "  ";
        Map map = App.getCurrentGame().getCurrentPlayer().getCurrentMap();
        for (int j = y; j < Integer.min(y + size, map.getWidth()); j++) {
            view += " " + j % 10;
        }
        view += "\n";
        for (int i = x; i < Integer.min(x + size, map.getHeight()); i++) {
            if (i > 0) view += "|\n";
            if (i < 10) view += " ";
            view += i;
            view += "|";
            for (int j = y; j < Integer.min(y + size, map.getWidth()); j++) {
                view += map.getCell(i, j).toString();
            }
        }
        return new Result(true, view + "|");
    }

    public Result helpReadingMap() {
        return new Result(true, App.getCurrentGame().getCurrentPlayer().getCurrentMap().getMapReadingManual());
    }

    public Result showEnergy() {
        return new Result(true, "Energy Remaining for The Day : " +
                App.getCurrentGame().getCurrentPlayer().getDayEnergy() + "\n" +
                "Energy Remaining for This Turn : " +
                App.getCurrentGame().getCurrentPlayer().getEnergy());
    }

    public Result cropInfo(String plantName) {
        PlantType plantType = CropType.getItem(plantName);
        if (plantType == null)
            plantType = TreeType.getItem(plantName);
        if (plantType == null)
            return new Result(false, "Plant Type Not Found");
        return new Result(true,
                "Name: " + plantType.getName() + "\n" +
                        "Source: " + ((Item) plantType.getSource() != null ? ((Item) plantType.getSource()).getName() : " is foraging, no source") + "\n" +
                        "Stages: " + (plantType.getStages()) + "\n" +
                        "Total Harvest Time: " + plantType.getTotalHarvestTime() + "\n" +
                        "One Time: " + ((Boolean) plantType.getOneTime()).toString().toUpperCase() + "\n" +
                        "Regrowth Time: " + plantType.getHarvestCycle() + "\n" +
                        "Base Sell Price: " + plantType.getFruit() + "\n" +
                        "Is Edible: " + ((Boolean) plantType.getFruit().isFruitEdible()).toString().toUpperCase() + "\n" +
                        "Base Energy: " + plantType.getFruit().getEnergy() + "\n" +
                        "Season(s): " + plantType.getSeasons().toString().replaceAll("\\[|\\]", "") + "\n" +
                        "Can Become Giant: " + (plantType instanceof CropType crop ?
                        ((Boolean) crop.canBecomeGiant()).toString().toUpperCase() : ""));
    }

    public Result buildGreenHouse() {
        Player player = App.getCurrentGame().getCurrentPlayer();

        if (player.getMoney() < 1000) {
            return new Result(false, "Not Enough Money, 1000 coins needed but you only have " +
                    player.getMoney() + ".");
        } else if (!player.getBackpack().hasEnoughItem(MineralType.Wood, 500)) {
            return new Result(false, "Not Enough Wood, 500 needed but you only have I dont know How Much.");
        }
        player.getFarmMap().getGreenHouse().repair();

        player.addMoney(-1000);
        player.getBackpack().reduceItems(MineralType.Wood, 500);
        return new Result(true, "GreenHouse Repaired!");
    }

    public Result startTrade() {
        StringBuilder result = new StringBuilder();
        result.append("Available players:\n");
        for (Player player : App.getCurrentGame().getPlayers()) {
            result.append(player.getUsername()).append("\n");
        }
        App.setCurrentMenu(Menu.Trade);
        App.getCurrentGame().getCurrentPlayer().setCurrentMenu(Menu.Trade);
        return new Result(true, result.toString());
    }

    private Result goToHome() {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        Cell cell = currentPlayer.getCurrentCell();
        for (Cell adjacentCell : cell.getAdjacentCells()) {
            if (adjacentCell.getType() == CellType.Building && adjacentCell.getBuilding() instanceof Hut) {
                App.setCurrentMenu(Menu.Home);
                currentPlayer.setCurrentMenu(Menu.Home);
                return new Result(true, "Redirecting to home ...");
            }
        }
        return new Result(false, "There are no houses nearby!");
    }

    private boolean subRectCouldBeGiant(int i, int j, Cell cell) {
        Cell[][] cells = cell.getMap().getCells();
        Crop crop = (Crop) cell.getObject();
        CropType type = (CropType) crop.getType();
        cell = cells[i][j];


        if (cells[i][j + 1].getObject() instanceof Crop crop1 && crop1.getType() == type &&
                cells[i + 1][j].getObject() instanceof Crop crop2 && crop2.getType() == type &&
                cells[i + 1][j + 1].getObject() instanceof Crop crop3 && crop3.getType() == type &&
                cells[i][j].getObject() instanceof Crop crop4 && crop4.getType() == type) {
            crop.setGiant(true);
            cells[i + 1][j + 1].setObject(crop);
            cells[i][j + 1].setObject(crop);
            cells[i + 1][j].setObject(crop);
            return true;
        }
        return false;
    }

    private boolean checkForGiantCrop(Cell cell) {
        if (cell.getBuilding() != null && cell.getBuilding() instanceof GreenHouse)
            return false;
        Crop crop = (Crop) cell.getObject();
        CropType type = (CropType) crop.getType();
        int i = cell.getPosition().getX(), j = cell.getPosition().getY();
        Cell[][] cells = cell.getMap().getCells();
        if (i > 0 && j > 0)
            if (subRectCouldBeGiant(i - 1, j - 1, cell)) return true;
        if (i > 0)
            if (subRectCouldBeGiant(i - 1, j, cell)) return true;
        if (j > 0)
            if (subRectCouldBeGiant(i, j - 1, cell)) return true;
        return subRectCouldBeGiant(i, j, cell);
    }

    public Result plant(String sourceName, String directionString) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        int direction = Integer.parseInt(directionString);
        PlantSourceType source = SeedType.getItem(sourceName);
        if (source == null)
            source = SaplingType.getItem(sourceName);
        if (source == null)
            return new Result(false, "invalid Source!");
        else if (!player.getBackpack().hasEnoughItem((Item) source, 1))
            return new Result(false, "You don't Have This Seed/Sapling in Your Inventory!");

        Cell cell = player.getCurrentCell().getAdjacentCells().get(direction);


        if (cell.getType() == CellType.Quarry)
            return new Result(false, "This cell is in the quarry!");
        else if (cell.getType() != CellType.Plowed)
            return new Result(false, "Cell Not Plowed");
        else if (cell.getObject() != null || (cell.getBuilding() != null && !(cell.getBuilding() instanceof GreenHouse)))
            return new Result(false, "Cell is Occupied");
        else if (source.getPlant() == null) {
            player.getBackpack().reduceItems((Item) source, 1);
            Season season = App.getCurrentGame().getTime().getSeason();
            ArrayList<CropType> cropTypes = CropType.getMixedSeedPossibilitiesBySeason().get(season);
            CropType cropType = cropTypes.get((new Random()).nextInt(cropTypes.size()));

            cell.setObject(new Crop(cropType));
            if (checkForGiantCrop(cell))
                return new Result(true, "You planted A Mixed Seed and it became A " +
                        cropType.getName() + ". And It also Became GIANT!!!!");
            else
                return new Result(true, "You planted A Mixed Seed and it became A " +
                        cropType.getName() + ".");
        } else if (source.getPlant() instanceof CropType cropType) {
            player.getBackpack().reduceItems((Item) source, 1);

            cell.setObject(new Crop(cropType));
            if (checkForGiantCrop(cell))
                return new Result(true, "You planted A Crop. A " + cropType.getName() +
                        ". And It Became GIANT!!!!");
            else
                return new Result(true, "You planted A Crop. A " + cropType.getName() + ".");
        } else if (source.getPlant() instanceof TreeType treeType) {
            player.getBackpack().reduceItems((Item) source, 1);

            cell.setObject(new Tree(treeType));
            return new Result(true, "You planted A Tree. A " + treeType.getName() + ".");
        } else {
            return new Result(false, "WTF in plant/GameController\n" +
                    sourceName + " " + direction);
        }

    }

    public Result showPlant(String iString, String jString) {
        int i = Integer.parseInt(iString), j = Integer.parseInt(jString);
        Cell cell = App.getCurrentGame().getCurrentPlayer().getCurrentMap().getCell(i, j);

        if (cell.getObject() instanceof Plant plant) {
            return new Result(true, (plant instanceof Crop ? "Crop " : "Tree ") +
                    "Name: " + (plant.isGiant() ? "GIANT " : "") + plant.getType().getName() + "\n" +
                    "Total time left: " + plant.getTillNextHarvest() + "\n" +
                    "Stage: " + plant.getCurrentStage() + " (0-based)\n" +
                    (plant.getWateredToday() ? "Watered today" : "Not Watered today") + "\n" +
                    "Is Foraging: " + plant.isForaging());
        } else
            return new Result(false, "No Plant Here!");
    }

    public Result pet(String animalName) {
        for (Cell cell : App.getCurrentGame().getCurrentPlayer().getCurrentCell().getAdjacentCells()) {
            if (cell != null && cell.getObject() instanceof Animal animal && animal.getName().equals(animalName)) {
                animal.addFriendShip(15);
                animal.setWasPet(true);
                return new Result(true, animalName + " " + animal.getType().getName() +
                        " was Pet!");
            }
        }
        return new Result(false, "No animal Found!");
    }

    public Result showAnimals() {
        String res = "";
        for (Animal animal : App.getCurrentGame().getCurrentPlayer().getFarmMap().getAnimals()) {
            res += animal.showDetails() + "\n";
        }
        return new Result(true, res);
    }

    public Result shepherd(String name, String iString, String jString) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        int i = Integer.parseInt(iString), j = Integer.parseInt(jString);
        if (!(player.getCurrentMap() instanceof FarmMap))
            return new Result(false, "You are not in a Farm!");
        FarmMap map = (FarmMap) player.getCurrentMap();
        Animal animal = null;
        for (Animal a : map.getAnimals()) {
            if (a.getName().equals(name)) {
                animal = a;
            }
        }
        if (animal == null) {
            return new Result(false, "No animal Found!");
        }
        Cell cell = map.getCell(i, j);
        if (animal.isOut()) {
            cell.setObject(null);
            animal.setOut(false);
            return new Result(true, "Animal Went Back In");
        } else {
            if (cell.getObject() != null) {
                return new Result(true, "Cell is Occupied " + cell.getObject().getClass().getName());
            } else if (App.getCurrentGame().getCurrentWeather().equals(Weather.Sunny)) {
                animal.setOut(true);
                animal.setWasFeed(true);
                animal.addFriendShip(8);
                cell.setObject(animal);
                return new Result(true, "Animal is Out!");
            } else {
                return new Result(false, "The Weather is not Good!!");
            }
        }
    }

    public Result feedHay(String name) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        Animal animal = null;
        if (!(player.getCurrentMap() instanceof FarmMap))
            return new Result(false, "You are not in a Farm!");
        if (player.getBackpack().getSlotByItemName("Hay") == null)
            return new Result(false, "You should have hey in you inventory!");
        FarmMap map = (FarmMap) player.getCurrentMap();
        for (Animal a : map.getAnimals()) {
            if (a.getName().equals(name)) {
                animal = a;
            }
        }
        if (animal == null)
            return new Result(false, "No animal Found!");
        else if (animal.isWasFeed())
            return new Result(true, "Animal was fed before");

        animal.setWasFeed(true);
        return new Result(true, "Animal was Fed");
    }

    public Result products() {
        Player player = App.getCurrentGame().getCurrentPlayer();
        String res = "Animals With Available Products\n";
        if (!(player.getCurrentMap() instanceof FarmMap))
            return new Result(false, "You are not in a Farm!");
        FarmMap map = (FarmMap) player.getCurrentMap();
        for (Animal animal : map.getAnimals()) {
            if (animal.getTillNextProduction() == 0) {
                res += animal.getType().getName() + " : " + animal.getName() + "\n";
            }
        }
        return new Result(true, res);
    }

    public Result collectProduct(String name) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        Animal animal = null;
        if (!(player.getCurrentMap() instanceof FarmMap))
            return new Result(false, "You are not in a Farm!");
        FarmMap map = (FarmMap) player.getCurrentMap();
        for (Animal a : map.getAnimals()) {
            if (a.getName().equals(name)) {
                animal = a;
            }
        }

        if (animal == null) {
            return new Result(false, "No animal Found!");
        }

        Stacks product = animal.getProduct();
        if (product == null) {
            return new Result(false, "Animal is Not Ready!");
        }
        if (animal.getType() == AnimalType.Pig && !animal.isOut()) {
            return new Result(false, "The Pig Must Be Out for You to be Able to Collect its Products!");
        }
        player.getBackpack().addItems(product.getItem(), product.getStackLevel(), product.getQuantity());
        return new Result(true, "You Got " + product.getQuantity() + " of " + product.getItem().getName() + "!");
    }

    public Result sellAnimal(String name) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        Animal animal = null;
        if (!(player.getCurrentMap() instanceof FarmMap))
            return new Result(false, "You are not in a Farm!");
        FarmMap map = (FarmMap) player.getCurrentMap();
        for (Animal a : map.getAnimals()) {
            if (a.getName().equals(name)) {
                animal = a;
            }
        }

        if (animal == null) {
            return new Result(false, "No animal Found!");
        }

        player.addMoney(animal.getPrice());
        map.removeAnimal(animal);
        animal.getEnclosure().removeAnimal(animal);
        return new Result(true, "You Sold " + animal.getName() + " " +
                animal.getType().getName() + " for " + animal.getPrice() + " Coins!");
    }

    public Result placeItem(String itemName, int direction) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        Item item = player.getItemFromBackpack(itemName);
        if (item == null)
            return new Result(false, "Item not found in backpack!");
        if (direction < 0 || direction > 7)
            return new Result(false, "Invalid direction!");
        Cell cell = player.getCurrentCell().getAdjacentCells().get(direction);

        if (cell == null || cell.getType() != CellType.Free || cell.getObject() != null ||
                !(cell.getMap() instanceof FarmMap))
            return new Result(false, "Can't place anything in this cell!");
        if (cell.getType() == CellType.Quarry) {
            return new Result(false, "This cell is in the quarry!");
        }

        ArtisanTypes artisanType = ArtisanTypes.getArtisan(item);
        player.getBackpack().reduceItems(item, 1);

        if (artisanType != null) {
            Artisan artisan = new Artisan(artisanType);
            cell.setObject(artisan);
            if (artisanType == ArtisanTypes.BeeHouse) {
                artisan.setFinalProduct(ProcessedProductType.Honey);
                artisan.setTimeLeft(ProcessedProductType.Honey.getProcessingTime());
            }
            return new Result(true, "Artisan placed successfully!");
        } else if (item == BuildingType.ShippingBin) {
            cell.setType(CellType.Building);
            ShippingBin shippingBin = new ShippingBin(cell);
            cell.setBuilding(shippingBin);
            ((FarmMap) cell.getMap()).addShippingBin(shippingBin);
            return new Result(true, "Shipping Bin Placed!");
        } else if (item == CraftingProduct.CherryBomb)
            return bombMap(cell.getPosition().getX(), cell.getPosition().getY(), 3);
        else if (item == CraftingProduct.Bomb)
            return bombMap(cell.getPosition().getX(), cell.getPosition().getY(), 5);
        else if (item == CraftingProduct.MegaBomb)
            return bombMap(cell.getPosition().getX(), cell.getPosition().getY(), 7);
        else if (item == CraftingProduct.Sprinkler)
            return waterMap(cell.getPosition().getX(), cell.getPosition().getY(), 0);
        else if (item == CraftingProduct.QualitySprinkler)
            return waterMap(cell.getPosition().getX(), cell.getPosition().getY(), 1);
        else if (item == CraftingProduct.IridiumSprinkler)
            return waterMap(cell.getPosition().getX(), cell.getPosition().getY(), 2);
        else if (item == CraftingProduct.Scarecrow)
            return protectMap(cell.getPosition().getX(), cell.getPosition().getY(), 8);
        else if (item == CraftingProduct.DeluxeScarecrow)
            return protectMap(cell.getPosition().getX(), cell.getPosition().getY(), 12);
        else if (item == CraftingProduct.GrassStarter) {
            if (cell.getObject() != null)
                return new Result(false, "The cell is Ocuupied");
            Crop grass = new Crop(CropType.Grass);
            grass.setForaging(true);
            cell.plant(grass);
            return new Result(true, "The cell has grass now!");
        } else {
            return new Result(false, "You can't place this item!");
        }
    }

    public Result sellItem(String itemName, String amountString) {
        int amount = (amountString == null ? 10000000 : Integer.parseInt(amountString));
        Player player = App.getCurrentGame().getCurrentPlayer();
        Item item = player.getItemFromBackpack(itemName);
        if (item == null)
            return new Result(false, "You don't have this item!");
        if (item instanceof ToolType || ArtisanTypes.getArtisan(item) != null)
            return new Result(false, "You cant sell tools!");
        ShippingBin shippingBin = null;
        for (Cell cell : player.getCurrentCell().getAdjacentCells())
            if (cell.getType() == CellType.Building && cell.getBuilding() instanceof ShippingBin)
                shippingBin = (ShippingBin) cell.getBuilding();
        if (shippingBin == null)
            return new Result(false, "There is no shipping bin near you");
        ArrayList<Stacks> removedStacks = new ArrayList<>();
        int removedCount = 0;
        for (Stacks stack : player.getBackpack().getItems()) {
            if (stack.getItem() == item) {
                int val = min(amount, stack.getQuantity());
                amount -= val;
                removedCount += val;
                shippingBin.addItem(new Stacks(item, stack.getStackLevel(), stack.getQuantity()));
                stack.addQuantity(-val);
                if (stack.getQuantity() == 0)
                    removedStacks.add(stack);
            }
        }
        for (Stacks stack : removedStacks) {
            player.getBackpack().getItems().remove(stack);
        }

        return new Result(true, "You added " + removedCount + " of " + item.getName()
                + " to the shipping bin!");
    }

    public Result inventoryShow() {
        StringBuilder result = new StringBuilder("Your inventory:\n");
        Player player = App.getCurrentGame().getCurrentPlayer();
        for (Stacks slot : player.getBackpack().getItems()) {
            if (slot.getItem() instanceof FruitType || slot.getItem() instanceof FishType || slot.getItem() instanceof AnimalProduct)
                result.append(slot.getStackLevel().toString()).append(" ");
            result.append(slot.getItem().getName()).append(" ");
            result.append(slot.getQuantity());
            result.append("\n");
        }
        return new Result(true, result.toString());
    }

    public Result inventoryTrash(String itemName, int number) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        ToolType trashCan = player.getTrashCan();
        assert trashCan != null;
        int money = 0, numberCopy = number;
        Item item = player.getItemFromBackpack(itemName);
        if (item == null)
            return new Result(false, "Item not found in backpack!");
        if (!player.getBackpack().hasEnoughItem(item, number))
            return new Result(false, "You don't have " + number + " of " + itemName);
        for (Stacks slot : player.getBackpack().getItems()) {
            if (slot.getItem().getName().equalsIgnoreCase(itemName)) {
                int amount = Math.min(number, slot.getQuantity());
                number -= amount;
                money += (int) (slot.getTotalPrice() * amount * trashCan.getTrashCanModifier() / slot.getQuantity());
                slot.addQuantity(-amount);
            }
        }
        player.getBackpack().mergeItems();
        player.addMoney(money);
        return new Result(false, numberCopy + " of " + itemName + " moved to trash. " +
                money + " money earned!");
    }

    public Result eatFood(String foodName) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        Stacks slot = player.getBackpack().getSlotByItemName(foodName);
        if (slot == null)
            return new Result(false, "This item doesn't exist in your inventory!");
        if (!isEdible(slot.getItem()))
            return new Result(false, "This item is not edible!");
        player.getBackpack().reduceItems(slot.getItem(), slot.getStackLevel(), 1);
        if (slot.getItem() instanceof CookingProduct cookingProduct) {
            player.addEnergy(cookingProduct.getEnergy());
            if (cookingProduct.getBuff() != null) {
                player.removeBuff();
                player.setBuff(cookingProduct.getBuff());
            }
        } else if (slot.getItem() instanceof ProcessedProduct) {
            player.addEnergy(((ProcessedProduct) slot.getItem()).getEnergy());
        } else if (slot.getItem() instanceof FruitType) {
            player.addEnergy(((FruitType) slot.getItem()).getEnergy());
        }
        return new Result(true, "Yum Yum!");
    }

    public Result fertilize(String fertilizerName, String directionString) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        int direction = Integer.parseInt(directionString);
        if (direction > 7 || direction < 0 || player.getCurrentCell().getAdjacentCells().get(direction) == null)
            return new Result(false, "Invalid direction!");
        Cell cell = player.getCurrentCell().getAdjacentCells().get(direction);
        if (!(cell.getObject() instanceof Plant plant))
            return new Result(false, "There is not a plant in this cell!");
        if (fertilizerName.replaceAll("\\s", "").equalsIgnoreCase(ShopItems.SpeedGro.name())) {
            plant.setTillNextHarvest(max(plant.getTillNextHarvest() - 1, 0));
            return new Result(true, "The selected plant will bear fruit sooner!");
        } else if (fertilizerName.replaceAll("\\s", "").equalsIgnoreCase(
                ShopItems.DeluxeRetainingSoil.name())) {
            plant.setAlwaysWatered(true);
            return new Result(true, "The selected plant will never need water!");
        } else {
            return new Result(false, "Invalid fertilizer!");
        }
    }

    // Cheats :

    public Result cheatAddItem(String itemName, int count) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        Item item = ItemManager.getItemByName(itemName);
        if (item == null)
            return new Result(false, "Item not found!");
        if (count < 0)
            return new Result(false, "Invalid quantity!");
        StackLevel level = item instanceof ToolType ? ((ToolType) item).getLevel() : StackLevel.Basic;
        if (!player.getBackpack().canAdd(item, level, count))
            return new Result(false, "You don't have enough space in your backpack!");
        player.getBackpack().addItems(item, level, count);
        return new Result(true, count + " of " + item.getName() + " added to the backpack!");
    }

    public Result cheatSetWeather(String weatherString) {
        Weather weather = null;
        for (Weather w : Weather.values()) {
            if (w.toString().equals(weatherString)) {
                weather = w;
            }
        }
        if (weather == null) {
            return new Result(false, "Please choose a valid weather type from " + Weather.values());
        }
        ServerGame serverGame = App.getCurrentGame();
        serverGame.setTomorrowWeather(weather);
        return new Result(true, "Weather set to " + weather.toString() + " Weather!");
    }

    public Result showMoney() {
        Player player = App.getCurrentGame().getCurrentPlayer();
        return new Result(true, player.getMoney() + "$");
    }

    public Result cheatThor(String s, String t) {
        int i = Integer.parseInt(s), j = Integer.parseInt(t);
        FarmMap map = App.getCurrentGame().getCurrentPlayer().getFarmMap();
        Cell cell = map.getCell(i, j);
        if (cell.getBuilding() != null) {
            return new Result(false, "There is A Building!!");
        } else {
            cell.thor();
            return new Result(true, "The Cell [" + i + ", " + j + "] was hit by Thor!");
        }
    }

    public Result cheatSetEnergy(String energyString) {
        int energy = Integer.parseInt(energyString);

        App.getCurrentGame().getCurrentPlayer().setEnergy(energy);
        App.getCurrentGame().getCurrentPlayer().setDayEnergy(energy);
        return new Result(true, "Energy Set to " + energy);
    }

    public Result cheatEnergyUnlimited() {
        Player player = App.getCurrentGame().getCurrentPlayer();
        player.setCheater(true);
        return new Result(true, "Cheat Activated!!");
    }

    public Result cheatSetFriendship(String name, String amountString) {
        int val = Integer.parseInt(amountString);
        for (Animal animal : App.getCurrentGame().getCurrentPlayer().getFarmMap().getAnimals()) {
            if (animal.getName().equals(name)) {
                animal.cheatSetFriendShip(val);
                return new Result(true, "Cheat acitvated!");
            }
        }
        return new Result(false, "No animal found!");
    }

    public Result cheatAddMoney(String amountString) {
        int val = Integer.parseInt(amountString);
        App.getCurrentGame().getCurrentPlayer().addMoney(val);
        return new Result(true, "cheat Activated, You Now Have " +
                App.getCurrentGame().getCurrentPlayer().getMoney() + "$");
    }

    public Result cheatAdvanceTime(String hourString) {
        String oldTime = App.getCurrentGame().getTime().getDateTime();
        int hour = Integer.parseInt(hourString);
        App.getCurrentGame().getTime().cheatAdvanceTime(hour);
        return new Result(true, "The old time was " + oldTime + "\n" +
                "The new time is " + App.getCurrentGame().getTime().getDateTime());
    }

    public Result cheatAdvanceDate(String dayString) {
        String oldTime = App.getCurrentGame().getTime().getDateTime();
        int day = Integer.parseInt(dayString);
        App.getCurrentGame().getTime().cheatAdvanceDate(day);
        return new Result(true, "The old time was " + oldTime + "\n" +
                "The new time is " + App.getCurrentGame().getTime().getDateTime());
    }

    public Result cheatSetAbility(String abilityName, int level) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        AbilityType type = AbilityType.getAbilityType(abilityName);
        if (type == null)
            return new Result(false, "Invalid ability type!");
        if (level > 4)
            return new Result(false, "Cannot reach this level!");
        while (player.getAbility(type).getLevel() < level) {
            if (type == AbilityType.Farming)
                player.farmXp(1);
            else if (type == AbilityType.Mining)
                player.mineXp(1);
            else if (type == AbilityType.Fishing)
                player.fishXp(1);
            else { // foraging
                player.forageXp(1);
            }
        }
        return new Result(true, abilityName + " level set to " + level);
    }

    public Result fishing(String fishPoleName) {
        ToolType type = ToolType.getFishPole(fishPoleName);
        if (type == null)
            return new Result(false, "Fish pole type is invalid!");
        Player player = App.getCurrentGame().getCurrentPlayer();
        if (!player.getBackpack().hasEnoughItem(type, 1))
            return new Result(false, "This fish pole is not available in your backpack!");
        if (!player.isByWater())
            return new Result(false, "You must be by the water!");

        int energyNeeded = ToolType.getFishPoleEnergy(type);
        if (player.getAbility(AbilityType.Fishing).getLevel() == 4)
            --energyNeeded;

        if (player.getEnergy() < energyNeeded) {
            player.consumeEnergy(player.getEnergy());
            return new Result(false, "Fishing failed! You don't have enough energy!");
        }
        player.consumeEnergy(energyNeeded);

        Season currentSeason = App.getCurrentGame().getTime().getSeason();
        ArrayList<FishType> availableFish;
        if (type == ToolType.TrainingRod)
            availableFish = new ArrayList<>(List.of(FishType.getCheapestOfSeason().get(currentSeason)));
        else
            availableFish = FishType.getAvailableFish(currentSeason);
        if (player.getAbility(AbilityType.Fishing).getLevel() == 4)
            availableFish.addAll(FishType.getAvailableLegendaryFish(currentSeason));

        int numberOfFish = min(6, getNumberOfFish());
        ArrayList<Stacks> capturedFish = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numberOfFish; i++) {
            double coefficient = getFishCoefficient(type);
            StackLevel fishLevel = getStackLevel(coefficient);
            FishType fishType = availableFish.get(random.nextInt(availableFish.size()));
            capturedFish.add(new Stacks(fishType, fishLevel, 1));
        }
        StringBuilder result = new StringBuilder("The following fish added to inventory:");
        for (Stacks stacks : capturedFish) {
            if (player.getBackpack().canAdd(stacks.getItem(), stacks.getStackLevel(), stacks.getQuantity())) {
                player.getBackpack().addItems(stacks.getItem(), stacks.getStackLevel(), stacks.getQuantity());
                result.append("\n").append(stacks.getStackLevel().toString());
                result.append(" ").append(stacks.getItem().getName());
            } else
                break;
        }
        player.fishXp(5);
        return new Result(false, result.toString());
    }

    public Result useArtisan(String artisanName, String itemList) {
        ArtisanTypes artisanType = ArtisanTypes.getArtisan(artisanName);
        if (artisanType == null)
            return new Result(false, "Artisan name is invalid!");
        if (artisanType == ArtisanTypes.BeeHouse)
            return new Result(false, "Bee house doesn't need any ingredient!");
        Artisan artisan = getNearArtisan(artisanType);
        if (artisan == null)
            return new Result(false, "The is no " + artisanName + " nearby!");
        String[] itemsList = itemList.split("\\s+");
        Recipe recipe = artisanType.getRecipe(itemsList);
        if (recipe == null)
            return new Result(false, "There is no recipe for the desired items!");
        Player player = App.getCurrentGame().getCurrentPlayer();
        if (!player.hasEnoughIngredients(recipe))
            return new Result(false, "You don't have enough ingredients!");
        if (artisan.getFinalProduct() != null)
            return new Result(false, "Artisan is currently in use!");

        player.useRecipeWithoutAdd(recipe);
        ProcessedProductType finalProduct = (ProcessedProductType) recipe.getFinalProduct();
        if (finalProduct.getPrice() == null) {
            artisan.setFinalProduct(
                    finalProduct,
                    itemsList[0].equalsIgnoreCase("Coal") ? itemsList[1] : itemsList[0]
            );
        } else
            artisan.setFinalProduct(finalProduct);
        if (finalProduct.getProcessingTime() == null)
            artisan.setTimeLeft(23 - App.getCurrentGame().getTime().getHour());
        else
            artisan.setTimeLeft(finalProduct.getProcessingTime());
        return new Result(true, "New process started!");
    }

    public Result getArtisanProduct(String artisanName) {
        ArtisanTypes artisanType = ArtisanTypes.getArtisan(artisanName);
        if (artisanType == null)
            return new Result(false, "Artisan name is invalid!");
        Artisan artisan = getNearArtisan(artisanType);
        if (artisan == null)
            return new Result(false, "The is no " + artisanName + " nearby!");
        if (artisan.getFinalProduct() == null)
            return new Result(false, "This artisan has no product currently!");
        if (artisan.getTimeLeft() > 0)
            return new Result(false, "You should wait. The product is being prepared!");
        Player player = App.getCurrentGame().getCurrentPlayer();
        if (!player.getBackpack().canAdd(artisan.getFinalProduct(), StackLevel.Basic, 1))
            return new Result(false, "You don't have enough space in your backpack!");
        player.getBackpack().addItems(artisan.getFinalProduct(), StackLevel.Basic, 1);
        String productName = artisan.getFinalProduct().getName();
        artisan.free();
        if (artisanType == ArtisanTypes.BeeHouse) {
            artisan.setFinalProduct(ProcessedProductType.Honey);
            artisan.setTimeLeft(ProcessedProductType.Honey.getProcessingTime());
        }
        return new Result(true, productName + " collected successfully!");
    }

    private void eraseGame() {
        ServerGame serverGame = App.getCurrentGame();
        for (Player player : serverGame.getPlayers()) {
            User user = App.getUserByUsername(player.getUsername());
            assert user != null;
            user.setMaxMoneyEarned(max(player.getMoney(), user.getMaxMoneyEarned()));
            user.addNumberOfGamesPlayed();
        }
        App.setCurrentGame(null);
        App.setCurrentMenu(Menu.MainMenu);
    }

    private int getPlayerVote(Player player, Scanner scanner) {
        view.printString(player.getUsername() + "! Should we terminate the game? (accept|reject)");
        Result result;
        do {
            result = view.askToVote(scanner);
        } while (!result.success());
        return result.message().equals("accept") ? 0 : 1;
    }

    private Result goToShop(String shopName) {
        ShopType shopType = ShopType.getShopType(shopName);
        if (shopType == null)
            return new Result(false, "There is no shop with that name!");
        Player player = App.getCurrentGame().getCurrentPlayer();
        Cell cell = player.getCurrentCell();
        if (cell.getType() == CellType.Door && cell.getBuilding() instanceof StoreBuilding building) {
            Shop shop = building.getStore();
            if (shop.getShopType() != shopType)
                return new Result(false, "There is no " + shopName + " nearby!");
            int hour = App.getCurrentGame().getTime().getHour();
            //TODO : Sobhan! time!
            if (hour < shopType.getStartTime() || hour > shopType.getEndTime())
                return new Result(false, "This shop is currently closed!");
            Menu newMenu = Menu.getMenu(shopName);
            App.setCurrentMenu(newMenu);
            player.setCurrentMenu(newMenu);
            return new Result(true, "Redirecting to " + shopName + " ...");
        }
        return new Result(false, "You should first reach this shop door!");
    }

    private Artisan getNearArtisan(ArtisanTypes artisanType) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        for (Cell adjacentCell : player.getCurrentCell().getAdjacentCells()) {
            if (adjacentCell.getObject() instanceof Artisan) {
                if (((Artisan) adjacentCell.getObject()).getType() == artisanType) {
                    return (Artisan) adjacentCell.getObject();
                }
            }
        }
        return null;
    }

    private int getNumberOfFish() {
        Random random = new Random();
        return (int) Math.ceil(
                App.getCurrentGame().getCurrentWeather().getFishingModifier() *
                        random.nextDouble() *
                        (App.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Fishing).getLevel() + 2)
        );
    }

    private double getFishCoefficient(ToolType type) {
        Random random = new Random();
        return (ToolType.getFishPoleModifier(type) *
                (App.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Fishing).getLevel() + 2) *
                random.nextDouble()) / (7.0 - App.getCurrentGame().getCurrentWeather().getFishingModifier());
    }

    private StackLevel getStackLevel(double coefficient) {
        if (0 < coefficient && coefficient <= 0.5)
            return StackLevel.Basic;
        if (0.5 < coefficient && coefficient <= 0.7)
            return StackLevel.Silver;
        if (0.7 < coefficient && coefficient <= 0.9)
            return StackLevel.Gold;
        else
            return StackLevel.Iridium;
    }

    private Result protectMap(int x, int y, int r) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        FarmMap farmMap = (FarmMap) player.getCurrentMap();
        for (int i = max(x - r, 0); i < min(x + r, farmMap.getHeight()); i++) {
            for (int j = max(y - r, 0); j < min(y + r, farmMap.getWidth()); j++) {
                farmMap.getCell(i, j).setProtected(true);
            }
        }
        return new Result(true, "Fu#k crows");
    }

    private Result waterMap(int x, int y, int r) {
        FarmMap farmMap = (FarmMap) App.getCurrentGame().getCurrentPlayer().getCurrentMap();
        Cell[][] cells = farmMap.getCells();
        if (cells[x - 1][y].getObject() instanceof Plant plant)
            plant.water();
        if (cells[x + 1][y].getObject() instanceof Plant plant)
            plant.water();
        if (cells[x][y - 1].getObject() instanceof Plant plant)
            plant.water();
        if (cells[x][y + 1].getObject() instanceof Plant plant)
            plant.water();
        for (int i = max(x - r, 0); i < min(x + r, farmMap.getHeight()); i++) {
            for (int j = max(y - r, 0); j < min(y + r, farmMap.getWidth()); j++) {
                if (cells[i][j].getObject() instanceof Plant plant)
                    plant.water();
            }
        }
        return new Result(true, "Map watered!");
    }

    private Result bombMap(int x, int y, int r) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        FarmMap farmMap = (FarmMap) player.getCurrentMap();
        for (int i = max(x - r, 0); i < min(x + r, farmMap.getHeight()); i++) {
            for (int j = max(y - r, 0); j < min(y + r, farmMap.getWidth()); j++) {
                Cell cell = farmMap.getCell(i, j);
                if (cell.getType() != CellType.Building && !(cell.getObject() instanceof Building)) {
                }
                cell.setObject(null);
                if (cell.getType() == CellType.Plowed) {
                    cell.setType(CellType.Free);
                }
            }
        }
        return new Result(true, "BOOOOOM!");
    }

    private boolean isEdible(Item item) {
        if (!(item instanceof CookingProduct) &&
                !(item instanceof ProcessedProduct) &&
                !(item instanceof FruitType)) {
            return false;
        }
        if (item instanceof FruitType fruit && !fruit.isFruitEdible())
            return false;
        if (item instanceof ProcessedProduct processedProduct && !processedProduct.isEdible())
            return false;
        return true;
    }
}
