package org.example.client.controller;

import org.example.client.Main;
import org.example.client.controller.menus.MenuController;
import org.example.client.model.*;
import org.example.client.view.HomeView;
import org.example.client.view.shopview.*;
import org.example.common.models.*;
import org.example.common.models.AnimalProperty.Animal;
import org.example.common.models.Map.*;
import org.example.common.models.Map.Map;
import org.example.common.models.Shops.Shop;
import org.example.common.models.Plants.*;
import org.example.common.models.Seasons.Season;
import org.example.common.models.Weathers.Weather;
import org.example.common.models.items.*;
import org.example.common.models.items.products.AnimalProduct;
import org.example.common.models.items.products.CookingProduct;
import org.example.common.models.items.products.CraftingProduct;
import org.example.common.models.items.products.ProcessedProductType;
import org.example.client.view.GameView;

import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class GameMenuController extends MenuController {
    private GameView view;

    public GameMenuController(GameView view) {
        this.view = view;
    }


    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {
        return null;
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
                                Main.getMain().getScreen().dispose();
                                ClientApp.setCurrentMenu(new HomeView());
                                Main.getMain().setScreen(ClientApp.getCurrentMenu());
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


    public Result buildGreenHouse() {
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();

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


    private boolean subRectCouldBeGiant(int i, int j, Cell cell) {
        Cell[][] cells = cell.getMap().getCells();
        Crop crop = (Crop) cell.getObject();
        CropType type = (CropType) crop.getType();
        cell = cells[i][j];

        if (i == cells.length - 1 || j == cells[0].length - 1)
            return false;

        if (cells[i][j + 1].getObject() instanceof Crop crop1 && crop1.getType() == type &&
                cells[i + 1][j].getObject() instanceof Crop crop2 && crop2.getType() == type &&
                cells[i + 1][j + 1].getObject() instanceof Crop crop3 && crop3.getType() == type &&
                cells[i][j].getObject() instanceof Crop crop4 && crop4.getType() == type) {
            crop.setGiant(true);
            crop.setCell(cells[i + 1][j]);
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
        if (!crop.getType().canBecomeGiant()) {
            return false;
        }
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



    public Result plant(SeedType source, Cell cell) {
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
        if (cell.getType() == CellType.Quarry)
            return new Result(false, "This cell is in the quarry!");
        else if (cell.getType() != CellType.Plowed)
            return new Result(false, "Cell Not Plowed");
        else if (cell.getObject() != null || (cell.getBuilding() != null && !(cell.getBuilding() instanceof GreenHouse)))
            return new Result(false, "Cell is Occupied");
        else if (source.getPlant() == null) {
            player.getBackpack().reduceItems((Item) source, 1);
            Season season = ClientApp.getCurrentGame().getTime().getSeason();
            ArrayList<CropType> cropTypes = CropType.getMixedSeedPossibilitiesBySeason().get(season);
            CropType cropType = cropTypes.get((new Random()).nextInt(cropTypes.size()));

            cell.plant(new Crop(cropType));
            if (checkForGiantCrop(cell))
                return new Result(true, "You planted A Mixed Seed and it became A " +
                        cropType.getName() + ". And It also Became GIANT!!!!");
            else
                return new Result(true, "You planted A Mixed Seed and it became A " +
                        cropType.getName() + ".");
        } else if (source.getPlant() instanceof CropType cropType) {
            player.getBackpack().reduceItems((Item) source, 1);

            cell.plant(new Crop(cropType));
            if (checkForGiantCrop(cell))
                return new Result(true, "You planted A Crop. A " + cropType.getName() +
                        ". And It Became GIANT!!!!");
            else
                return new Result(true, "You planted A Crop. A " + cropType.getName() + ".");
        } else if (source.getPlant() instanceof TreeType treeType) {
            player.getBackpack().reduceItems((Item) source, 1);

            cell.plant(new Tree(treeType));
            return new Result(true, "You planted A Tree. A " + treeType.getName() + ".");
        } else {
            return new Result(false, "WTF in plant/GameController\n");
        }

    }



    public Result pet(String animalName) {
        for (Cell cell : ClientApp.getCurrentGame().getCurrentPlayer().getCurrentCell().getAdjacentCells()) {
            if (cell != null && cell.getObject() instanceof Animal animal && animal.getName().equals(animalName)) {
                animal.addFriendShip(15);
                animal.setWasPet(true);
                return new Result(true, animalName + " " + animal.getType().getName() +
                        " was Pet!");
            }
        }
        return new Result(false, "No animal Found!");
    }

    public Result feedHay(String name) {
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
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


    public Result collectProduct(String name) {
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
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
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
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
        if (animal.isOut())
            animal.getCurrentCell().setObject(null);
        return new Result(true, "You Sold " + animal.getName() + " " +
                animal.getType().getName() + " for " + animal.getPrice() + " Coins!");
    }



    public Result eatFood(String foodName) {
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
        Stacks slot = player.getBackpack().getSlotByItemName(foodName);
        if (slot == null)
            return new Result(false, "This item doesn't exist in your inventory!");
        if (!slot.getItem().isEdible())
            return new Result(false, "This item is not edible!");
        player.getBackpack().reduceItems(slot.getItem(), slot.getStackLevel(), 1);
        if (slot.getItem() instanceof CookingProduct cookingProduct) {
            player.addEnergy(cookingProduct.getEnergy());
            if (cookingProduct.getBuff() != null) {
                player.removeBuff();
                player.addBuff(cookingProduct.getBuff());
            }
        } else if (slot.getItem() instanceof ProcessedProduct) {
            player.addEnergy(((ProcessedProduct) slot.getItem()).getEnergy());
        } else if (slot.getItem() instanceof FruitType) {
            player.addEnergy(((FruitType) slot.getItem()).getEnergy());
        }
        return new Result(true, "Yum Yum!");
    }


}
