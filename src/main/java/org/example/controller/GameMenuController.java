package org.example.controller;

import org.example.models.*;
import org.example.models.AnimalProperty.Animal;
import org.example.models.Map.*;
import org.example.models.Map.Map;
import org.example.models.Shops.Shop;
import org.example.models.enums.*;
import org.example.models.enums.Plants.*;
import org.example.models.enums.Seasons.Season;
import org.example.models.enums.Weathers.Weather;
import org.example.models.enums.items.*;
import org.example.models.enums.items.products.ProcessedProductType;
import org.example.models.tools.Tool;
import org.example.view.GameMenuView;

import java.util.*;

import static java.lang.Math.min;

public class GameMenuController extends MenuController {
    private GameMenuView view;

    public GameMenuController(GameMenuView view) {
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
        Game game = App.getCurrentGame();
        if (!App.getLoggedInUser().getUsername().equals(game.getAdmin().getUsername()))
            return new Result(false, "You cannot end this game!");
        App.setCurrentGame(null);
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "Redirecting to main menu ...");
    }

    public boolean playerPassedOut() {
        return App.getCurrentGame().getCurrentPlayer().hasPassedOut();
    }

    public void nextTurn(Scanner scanner) {
        App.getCurrentGame().getCurrentPlayer().setNextTurnEnergy();
        boolean fullTurn =  App.getCurrentGame().nextPlayer();
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();

        if (fullTurn)
            App.getCurrentGame().getTime().passAnHour();
        if (currentPlayer.getDayEnergy() <= 0) {
            nextTurn(scanner);
            return;
        }
        view.printString(currentPlayer.getUsername() + "'s turn!");
        App.setCurrentMenu(currentPlayer.getCurrentMenu());
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
        Game game = App.getCurrentGame();
        Weather weather;
        if (game.getTomorrowWeather() != null) weather = game.getTomorrowWeather();
        else game.setTomorrowWeather(weather = game.getTime().getSeason().pickARandomWeather());
        return new Result(true, "The Weather Forecasted For Tomorrow is " +
                weather.toString() + " Weather!");
    }

    public Result walk(String s, String t, Scanner scanner) {

        int i = Integer.parseInt(s), j = Integer.parseInt(t);
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        Map currentMap = currentPlayer.getCurrentMap();
        Cell destination = currentMap.getCell(i, j);
        if (!currentMap.areConnected(currentPlayer.getCurrentCell(), destination)) {
            return new Result(false, "There Is No Path Between These Cells");
        } else {
            int energy = currentMap.getDistance(currentPlayer.getCurrentCell(), destination) / 20;
            System.out.println("The Energy Needed for This Walk is " +
                     energy + " And You Have " + currentPlayer.getEnergy() +
                    ", Would You Like To Walk? (Y/N)");
            String answer = scanner.nextLine();
            while (true) {
                if (answer.trim().equals("Y")) {
                    if (currentPlayer.getEnergy() > energy) {
                        currentPlayer.consumeEnergy(energy);
                        currentPlayer.setCurrentCell(destination);
                        if (destination.getType() == CellType.MapLink) {
                            if (((Cell) destination.getObject()).getMap() == currentPlayer.getFarmMap() ||
                                    ((Cell) destination.getObject()).getMap() == App.getCurrentGame().getNpcMap() ||
                                    (currentPlayer.getSpouse() != null &&
                                            ((Cell) destination.getObject()).getMap() == currentPlayer.getSpouse().getFarmMap())) {
                                Cell newDestination = (Cell) destination.getObject();
                                currentPlayer.setCurrentCell(newDestination);
                                currentPlayer.setCurrentMap(newDestination.getMap());
                                return new Result(true, "You Changed your Map And Now Are On Cell(" +
                                        newDestination.getPosition().getX() + "," +
                                        newDestination.getPosition().getY() + ") of " +
                                        newDestination.getMap().getClass().getSimpleName());
                            } else {
                                return new Result(true, "You Walked But Are Not Able to Change Your Map!!");
                            }
                        }
                        return new Result(true, "You Walked And Now Are On Cell(" +
                                i + "," + j + ")");
                    } else {
                        currentPlayer.setDayEnergy(0);
                         // TODO rassa passOut kojast?
                        return new Result(false, "You Passed Out!!");
                    }
                } else if (answer.trim().equals("N")) {
                    return new Result(false, "Alright.");
                }
                System.out.println("Invalid Response, Please Answer By (Y/N)");
                answer = scanner.nextLine();
            }
        }
    }

    public Result printMap(String s, String t, String sizeString) {

        int x = Integer.parseInt(s), y = Integer.parseInt(t), size = Integer.parseInt(sizeString);
        String view = "";
        Map map = App.getCurrentGame().getCurrentPlayer().getCurrentMap();
        System.out.println(map.getHeight());
        for (int i = x; i < Integer.min(x + size, map.getHeight()); i++) {
            if (i > 0) view += "|\n";
            view += "|";
            for (int j = y; j < Integer.min(y + size, map.getWidth()); j++) {
                view += map.getCell(i, j).toString();
            }
        }
        return new Result(true, view);
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

    public Result useTool(String directionString) {
        int direction = Integer.parseInt(directionString);
        Player player = App.getCurrentGame().getCurrentPlayer();
        Cell currentCell = player.getCurrentCell();
        Cell cell = currentCell.getAdjacentCells().get(direction);
        Tool tool = player.getCurrentTool();
        if (tool == null) {
            return new Result(false, "You Don't Have A Tool in Hand");
        }
        return tool.use(cell);
    }

    public Result cropInfo(String plantName) {
        PlantType plantType = CropType.getItem(plantName);
        if (plantType == null)
            plantType = TreeType.getItem(plantName);
        if (plantType == null)
            return new Result(false, "Plant Type Not Found");
        return new Result(true, "Name: " + plantType.getName() +
                "Source: " + ((Item) plantType.getSource()).getName() +
                "Stages: " + (plantType.getStages().toString()).replaceAll("\\]|\\[", "") +
                "Total Harvest Time: " + plantType.getTotalHarvestTime() +
                "One Time: " + ((Boolean) plantType.getOneTime()).toString().toUpperCase() +
                "Regrowth Time: " + plantType.getHarvestCycle() +
                "Base Sell Price: " + plantType.getFruit() +
                "Is Edible: " + ((Boolean) plantType.getFruit().isFruitEdible()).toString().toUpperCase() +
                "Base Energy: " + plantType.getFruit().getEnergy() +
                "Season(s): " + plantType.getSeasons().toString().replaceAll("\\[|\\]", "") +
                "Can Become Giant: " + (plantType instanceof CropType crop?
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
            //TODO: Trade View
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

        if (cells[i][j + 1].getObject() instanceof Crop crop1 && crop1.getType() == type &&
                cells[i + 1][j].getObject() instanceof Crop crop2 && crop2.getType() == type &&
                cells[i + 1][j + 1].getObject() instanceof Crop crop3 && crop3.getType() == type) {
            crop.setGiant(true);
            cells[i + 1][j + 1].setObject(crop);
            cells[i][j + 1].setObject(crop);
            cells[i + 1][j].setObject(crop);
            return true;
        }
        return false;
    }

    private boolean checkForGiantCrop(Cell cell) {
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
            CropType cropType = cropTypes.get((new Random( )).nextInt(cropTypes.size()));

            cell.setObject(new Crop(cropType));
            if (checkForGiantCrop(cell))
                return new Result(true, "You planted A Mixed Seed and it became A " +
                        cropType.getName() + ". And It also Became GIANT!!!!");
            else
                return new Result(true, "You planted A Mixed Seed and it became A " +
                        cropType.getName() + ".");
        }
        else if (source.getPlant() instanceof CropType cropType) {
            player.getBackpack().reduceItems((Item) source, 1);

            cell.setObject(new Crop(cropType));
            if (checkForGiantCrop(cell))
                return new Result(true, "You planted A Crop. A " + cropType.getName() +
                        ". And It Became GIANT!!!!");
            else
                return new Result(true, "You planted A Crop. A " + cropType.getName() + ".");
        }
        else if (source.getPlant() instanceof TreeType treeType) {
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
            return new Result(true, (plant instanceof Crop? "Crop " : "Tree ") +
                    "Name: " + plant.getType().getName() + "\n" +
                        "Total time left: " + plant.getTillNextHarvest() + "\n" +
                        "Stage: " + plant.getCurrentStage() + " (0-based)\n" +
                        (plant.getWateredToday()? "Watered today": "Not Watered today") + "\n" +
                        "Is Foraging: " + plant.isForaging());
        }
        else
            return new Result(false, "No Plant Here!");
    }

    public Result pet(String animalName) {
        for (Cell cell: App.getCurrentGame().getCurrentPlayer().getCurrentCell().getAdjacentCells()) {
            if (cell.getObject() instanceof Animal animal && animal.getName().equals(animalName)) {
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
        for (Animal animal: App.getCurrentGame().getCurrentPlayer().getFarmMap().getAnimals()) {
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
        for (Animal a: map.getAnimals()) {
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
            } else if (App.getCurrentGame().getCurrentWeather().equals(Weather.Sunny)){
                animal.setOut(true);
                animal.setWasFeed(true);
                animal.addFriendShip(8);
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
        FarmMap map = (FarmMap) player.getCurrentMap();
        for (Animal a: map.getAnimals()) {
            if (a.getName().equals(name)) {
                animal = a;
            }
        }
        if (animal == null) {
            return new Result(false, "No animal Found!");
        }

        animal.setWasFeed(true);
        return new Result(true, "Animal was Fed");
    }

    public Result products() {
        Player player = App.getCurrentGame().getCurrentPlayer();
        String res = "Animals With Available Products\n";
        if (!(player.getCurrentMap() instanceof FarmMap))
            return new Result(false, "You are not in a Farm!");
        FarmMap map = (FarmMap) player.getCurrentMap();
        for (Animal animal: map.getAnimals()) {
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
        for (Animal a: map.getAnimals()) {
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
        for (Animal a: map.getAnimals()) {
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
        if (direction < 1 || direction > 8)
            return new Result(false, "Invalid direction!");
        ArtisanTypes artisanType = ArtisanTypes.getArtisan(item);
        if (artisanType == null && !(item == BuildingType.ShippingBin))
            return new Result(false, "This item cannot be placed!");
        Cell cell = player.getCurrentCell().getAdjacentCells().get(direction);
        if (cell.getType() == CellType.Quarry) {
            return new Result(false, "This cell is in the quarry!");
        }
        if (cell == null || cell.getType() != CellType.Free || !(cell.getMap() instanceof FarmMap))
            return new Result(false, "The desired cell is currently occupied!");
        player.getBackpack().reduceItems(item, 1);
        if (item == BuildingType.ShippingBin) {
            cell.setType(CellType.Building);
            ShippingBin shippingBin = new ShippingBin(cell);
            cell.setBuilding(shippingBin);
            ((FarmMap) cell.getMap()).addShippingBin(shippingBin);
            return new Result(true, "Shipping Bin Placed!");
        } else {
            Artisan artisan = new Artisan(artisanType);
            cell.setObject(artisan);
            return new Result(true, "Artisan placed successfully!");
        }
    }

    public Result sellItem(String itemName, String amountString) {
        int amount = (amountString == null? 10000000: Integer.parseInt(amountString));
        Player player = App.getCurrentGame().getCurrentPlayer();
        Item item = player.getItemFromBackpack(itemName);
        ShippingBin shippingBin = null;
        for (Cell cell: player.getCurrentCell().getAdjacentCells())
            if (cell.getType() == CellType.Building && cell.getBuilding() instanceof ShippingBin)
                shippingBin = (ShippingBin) cell.getBuilding();
        if (shippingBin == null)
            return new Result(false, "There is no shipping bin near you");
        ArrayList<Stacks> removedStacks = new ArrayList<>();
        int removedCount = 0;
        for (Stacks stack: player.getBackpack().getItems()) {
            if (stack.getItem() == item) {
                int val = min(amount, stack.getQuantity());
                amount -= val;
                stack.addQuantity(-val);
                removedCount += val;
                if (stack.getQuantity() == 0)
                    removedStacks.add(stack);
                shippingBin.addItem(new Stacks(item, stack.getStackLevel(), stack.getQuantity()));
            }
        }
        for (Stacks stack: removedStacks) {
            player.getBackpack().getItems().remove(stack);
        }

        return new Result(true, "You added " + removedCount + " of " + item.getName()
        + " to the shipping bin!");
    }

    public Result inventoryShow() {
        StringBuilder result = new StringBuilder("Your inventory:\n");
        Player player = App.getCurrentGame().getCurrentPlayer();
        for (Stacks slot : player.getBackpack().getItems()) {
            if (!(slot.getItem() instanceof ToolType))
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

    // Cheats :

    public Result cheatAddItem(String itemName, int count) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        Item item = Game.getItemByName(itemName);
        if (item == null)
            return new Result(false, "Item not found!");
        if (count < 0)
            return new Result(false, "Invalid quantity!");
        StackLevel level = item instanceof ToolType? ((ToolType) item).getLevel() : StackLevel.Basic;
        if (!player.getBackpack().canAdd(item, level, count))
            return new Result(false, "You don't have enough space in your backpack!");
        player.getBackpack().addItems(item, level, count);
        return new Result(true, count + " of " + itemName + "added to the backpack!");
    }

    public Result cheatSetWeather(String weatherString) {
        Weather weather = null;
        for (Weather w : Weather.values()) {
            if (w.toString().equals(weatherString)) {
                weather = w;
            }
        }
        if (weather == null) {
            return new Result(false, "Please a valid weather type from " + Weather.values());
        }
        Game game = App.getCurrentGame();
        game.setTomorrowWeather(game.getTime().getSeason().pickARandomWeather());
        return new Result(true, "Weather set to " + weather.toString() + " Weather!");
    }

    public Result cheatThor(String s, String t) {
        int i = Integer.parseInt(s), j = Integer.parseInt(t);
        FarmMap map = App.getCurrentGame().getCurrentPlayer().getFarmMap();
        Cell cell = map.getCell(i, j);
        if (cell.getType() == CellType.Building) {
            return new Result(false, "There is A Building!!");
        } else {
            cell.thor();
            return new Result(true, "The Cell [" + i + ", " + j + "] was hit by Thor!");
        }
    }

    public Result cheatSetEnergy(String energyString) {
        int energy = Integer.parseInt(energyString);

        App.getCurrentGame().getCurrentPlayer().setEnergy(energy);
        return new Result(true, "Energy Set to " + energy);
    }

    public Result cheatEnergyUnlimited() {
        Player player = App.getCurrentGame().getCurrentPlayer();
        player.setEnergy(100000000);
        player.setDayEnergy(100000000);
        player.setMaxEnergy(100000000);
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
        String oldTime = App.getCurrentGame().getTime().showDateTime();
        int hour = Integer.parseInt(hourString);
        App.getCurrentGame().getTime().cheatAdvanceTime(hour);
        return new Result(true, "The old time was " + oldTime + "\n" +
                "The new time is " + App.getCurrentGame().getTime().showDateTime());
    }

    public Result cheatAdvanceDate(String dayString) {
        String oldTime = App.getCurrentGame().getTime().showDateTime();
        int day = Integer.parseInt(dayString);
        App.getCurrentGame().getTime().cheatAdvanceDate(day);
        return new Result(true, "The old time was " + oldTime + "\n" +
                "The new time is " + App.getCurrentGame().getTime().showDateTime());
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
        player.consumeEnergy(player.getEnergy());

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
        Random random = new Random( );
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
            }
            else
                break;
        }
        player.fishXp(5);
        return new Result(false, result.toString());
    }

    public Result useArtisan(String artisanName, String itemList) {
        ArtisanTypes artisanType = ArtisanTypes.getArtisan(artisanName);
        if (artisanType == null)
            return new Result(false, "Artisan name is invalid!");
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

        ProcessedProductType finalProduct = (ProcessedProductType) recipe.getFinalProduct();
        if (finalProduct.getPrice() == null) {
            artisan.setFinalProduct(
                    finalProduct,
                    itemsList[0].equalsIgnoreCase("Coal")? itemsList[1] : itemsList[0]
            );
        }
        else
            artisan.setFinalProduct(finalProduct);
        if (finalProduct.getProcessingTime() == null)
            artisan.setTimeLeft(24 - App.getCurrentGame().getTime().getHour());
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
            return new Result(false, "This artisan currently has no product!");
        if (artisan.getTimeLeft() > 0)
            return new Result(false, "You should wait. The product is being prepared!");
        Player player = App.getCurrentGame().getCurrentPlayer();
        if (!player.getBackpack().canAdd(artisan.getFinalProduct(), StackLevel.Basic, 1))
            return new Result(false, "You don't have enough space in your backpack!");
        player.getBackpack().addItems(artisan.getFinalProduct(), StackLevel.Basic, 1);
        artisan.free();
        return new Result(true, artisan.getFinalProduct().getName() + " collected successfully!");
    }

    private void eraseGame() {
        Game game = App.getCurrentGame();
        for (Player player : game.getPlayers()) {
            player.setCurrentGame(null);
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
        }
        Menu newMenu = Menu.getMenu(shopName);
        App.setCurrentMenu(newMenu);
        player.setCurrentMenu(newMenu);
        return new Result(true, "Redirecting to " + shopName + " ...");
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
        Random random = new Random( );
        return (int) Math.ceil(
                App.getCurrentGame().getCurrentWeather().getFishingModifier() *
                random.nextInt() *
                (App.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Fishing).getLevel() + 2)
        );
    }

    private double getFishCoefficient(ToolType type) {
        Random random = new Random( );
        return (ToolType.getFishPoleModifier(type) *
                (App.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Fishing).getLevel() + 2) *
                random.nextInt(2)) / (7.0 - App.getCurrentGame().getCurrentWeather().getFishingModifier());
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
}
