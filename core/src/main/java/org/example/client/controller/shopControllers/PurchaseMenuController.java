package org.example.client.controller.shopControllers;

import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.controller.menus.MenuController;
import org.example.client.model.ClientApp;
import org.example.client.view.AppMenu;
import org.example.client.view.menu.LobbyMenuView;
import org.example.client.view.shopview.PurchaseMenuView;
import org.example.common.models.GameAssetManager;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.server.models.*;
import org.example.server.models.enums.AbilityType;
import org.example.server.models.enums.NPCType;
import org.example.server.models.enums.items.Recipe;
import org.example.server.models.enums.items.ToolType;

import java.util.HashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class PurchaseMenuController extends MenuController {
    private final PurchaseMenuView passwordMenuView;
    private final AppMenu shopMenuView;

    public PurchaseMenuController(PurchaseMenuView passwordMenuView ,  AppMenu shopMenuView) {
        this.passwordMenuView = passwordMenuView;
        this.shopMenuView = shopMenuView;
    }

    public GraphicalResult purchase(Stock stock, int num, NPCType npc) {
        int sum = stock.getSalePrice() * num;
        Player currentPlayer = ClientApp.getCurrentGame().getCurrentPlayer();
        if (sum > currentPlayer.getMoney()) {
            return new GraphicalResult("Sorry, you don't have enough money!",
                    GameAssetManager.getGameAssetManager().getErrorColor());
        }
        if (stock.getItem() instanceof Recipe) {

        } else if (stock.getItem().equals(ToolType.DeluxeBackpack)) {
            if (currentPlayer.getBackpackType() == ToolType.BasicBackpack) {
                return new GraphicalResult("You should first unlock large backpack!",
                        GameAssetManager.getGameAssetManager().getErrorColor());
            }
        } else {
            if (stock.getItem() == ToolType.FiberglassRod) {
                if (currentPlayer.getAbility(AbilityType.Fishing).getLevel() < 2) {
                    return new GraphicalResult("Not enough fishing skill level!",
                            GameAssetManager.getGameAssetManager().getErrorColor());
                }
            } else if (stock.getItem() == ToolType.IridiumRod) {
                if (currentPlayer.getAbility(AbilityType.Fishing).getLevel() < 4) {
                    return new GraphicalResult("Not enough fishing skill level!",
                            GameAssetManager.getGameAssetManager().getErrorColor());
                }
            }
            if (!App.getCurrentGame().getCurrentPlayer().getBackpack().canAdd(
                    stock.getItem(),
                    stock.getStackLevel(),
                    num)) {
                return new GraphicalResult("Not enough space in backPack!",
                        GameAssetManager.getGameAssetManager().getErrorColor());
            }
        }
        String shopName = npc.getJob().toString();
        String itemName = stock.getItem().getName();
        int quantity = stock.getQuantity();
        int lobbyId = ClientApp.getCurrentGame().getLobbyId();
        Message message = new Message(new HashMap<String, Object>() {{
            put("lobbyId", lobbyId);
            put("shopName", shopName);
            put("itemName", itemName);
            put("quantity", quantity);
        }}, Message.Type.purchase_from_shop);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, TIMEOUT_MILLIS);
        if (response == null || response.getType() != Message.Type.response) {
            return new GraphicalResult(
                    "Failed to purchase!",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        GraphicalResult result = new GraphicalResult(response.<LinkedTreeMap<String, Object>>getFromBody("GraphicalResult"));
        if(result.hasError()){
            return new GraphicalResult(
                    result.getMessage().toString(),
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        if(stock.getItem().equals(ToolType.DeluxeBackpack) || stock.getItem().equals(ToolType.LargeBackpack)){
            ToolType backpack = (ToolType) stock.getItem();
            currentPlayer.setBackpack(backpack);
        }else if(stock.getItem() instanceof Recipe){
            currentPlayer.getAvailableCookingRecipes().add((Recipe) stock.getItem());
        }else {
            currentPlayer.getBackpack().addItems(
                    stock.getItem(),
                    stock.getStackLevel(),
                    quantity);
        }
        currentPlayer.spendMoney(sum);
        return new GraphicalResult(
                result.getMessage().toString(),
                GameAssetManager.getGameAssetManager().getAcceptColor(),
                false
        );
    }

    public GraphicalResult purchaseAnimal() {
        // TODO : Sobhan
        return null;
    }

    public GraphicalResult build(){
        //TODO : Rassa
        return null;
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
