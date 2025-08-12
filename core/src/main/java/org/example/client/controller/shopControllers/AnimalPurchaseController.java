package org.example.client.controller.shopControllers;

import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.controller.GameMenuController;
import org.example.client.controller.ResultController;
import org.example.client.controller.menus.MenuController;
import org.example.client.model.ClientApp;
import org.example.client.view.AppMenu;
import org.example.client.view.GameView;
import org.example.client.view.menu.LobbyMenuView;
import org.example.client.view.shopview.AnimalPurchaseView;
import org.example.client.view.shopview.BuildMenuView;
import org.example.client.view.shopview.MarnieRanch;
import org.example.client.view.shopview.PurchaseMenuView;
import org.example.common.models.GameAssetManager;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.server.models.*;
import org.example.server.models.AnimalProperty.Animal;
import org.example.server.models.AnimalProperty.Barn;
import org.example.server.models.AnimalProperty.Coop;
import org.example.server.models.Shops.Shop;
import org.example.server.models.enums.AbilityType;
import org.example.server.models.enums.NPCType;
import org.example.server.models.enums.items.AnimalType;
import org.example.server.models.enums.items.BuildingType;
import org.example.server.models.enums.items.Recipe;
import org.example.server.models.enums.items.ToolType;
import org.example.server.models.enums.items.products.CookingProduct;
import org.example.server.models.enums.items.products.CraftingProduct;

import java.util.HashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class AnimalPurchaseController extends MenuController {
    private final AnimalPurchaseView passwordMenuView;
    private final AppMenu shopMenuView;

    public AnimalPurchaseController(AnimalPurchaseView passwordMenuView , AppMenu shopMenuView) {
        this.passwordMenuView = passwordMenuView;
        this.shopMenuView = shopMenuView;
    }

    public GraphicalResult purchaseAnimal(Stock stock, int num, NPCType npc, String name) {
        int sum = stock.getSalePrice(ClientApp.getCurrentGame().getTime().getSeason()) * num;
        Player currentPlayer = ClientApp.getCurrentGame().getCurrentPlayer();
        if (sum > currentPlayer.getMoney()) {
            return new GraphicalResult("Sorry, you don't have enough money!",
                    GameAssetManager.getGameAssetManager().getErrorColor());
        }

        if (!ClientApp.getCurrentGame().getCurrentPlayer().getBackpack().canAdd(
                stock.getItem(),
                stock.getStackLevel(),
                num)) {
            return new GraphicalResult("Not enough space in backPack!",
                    GameAssetManager.getGameAssetManager().getErrorColor());
        }

        String shopName = npc.getJob().toString();
        String itemName = stock.getItem().getName();
        int quantity = stock.getQuantity();
        int lobbyId = ClientApp.getCurrentGame().getLobbyId();
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(
                new Message(new HashMap<String, Object>() {{
                    put("lobbyId", lobbyId);
                    put("shopName", shopName);
                    put("itemName", itemName);
                    put("quantity", quantity);
                }}, Message.Type.purchase_from_shop),
                TIMEOUT_MILLIS
        );
        if (response == null || response.getType() != Message.Type.response) {
            return new GraphicalResult(
                    "Failed to purchase!",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        GraphicalResult result = new GraphicalResult(response.<LinkedTreeMap<String, Object>>getFromBody("GraphicalResult"));
        if(result.hasError())
            return result;


        Result res = buyAnimal((AnimalType) stock.getItem(), name);
        result = new GraphicalResult(res.message(), !res.success());
        if (res.success())
            currentPlayer.spendMoney(sum);
        return result;
    }

    private Result buyAnimal(AnimalType type, String name) {

        Animal animal = new Animal(type, name);
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();

        String animalString = animal.getType().getName();

        for (Barn barn : player.getFarmMap().getBarns()) {
            if (type.getAppropriateFarmType().contains(barn.getType()) &&
                    barn.getType().getCapacity() > barn.getAnimals().size()) {
                barn.addAnimal(animal);
                animal.setEnclosure(barn);
                player.getFarmMap().addAnimal(animal);
                return new Result(true, "You have bought a " + animalString +
                        " and it is in the " + barn.getType().getName());
            }
        }
        for (Coop coop : player.getFarmMap().getCoops()) {
            if (type.getAppropriateFarmType().contains(coop.getType()) &&
                    coop.getType().getCapacity() > coop.getAnimals().size()) {
                coop.addAnimal(animal);
                animal.setEnclosure(coop);
                player.getFarmMap().addAnimal(animal);
                return new Result(true, "You have bought a " + animalString +
                        " and it is in the " + coop.getType().getName());
            }
        }
        return new Result(false, "There is No Space For This Animal in Your Farm!");
    }



    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(shopMenuView);
        return null;
    }
}
