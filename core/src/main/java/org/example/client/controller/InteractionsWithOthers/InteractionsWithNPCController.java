package org.example.client.controller.InteractionsWithOthers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.controller.CheatController;
import org.example.client.controller.PopUpController;
import org.example.client.controller.ResultController;
import org.example.client.model.ClientApp;
import org.example.client.model.PopUpTexture;
import org.example.client.view.OutsideView;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.server.models.*;
import org.example.server.models.NPCs.NPC;
import org.example.server.models.NPCs.Quest;
import org.example.server.models.Relations.Gift;
import org.example.server.models.Relations.Relation;
import org.example.server.models.enums.ArtisanTypes;
import org.example.server.models.enums.Plants.CropType;
import org.example.server.models.enums.StackLevel;
import org.example.server.models.enums.items.Recipe;
import org.example.server.models.enums.items.ShopItems;
import org.example.server.models.enums.items.ToolType;
import org.example.server.models.enums.items.products.CookingProduct;
import org.example.server.models.tools.Backpack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class InteractionsWithNPCController {

    public void meetNPC(String npcName) {
        NPC npc = InteractionsWithNPCController.findNPC(npcName);
        if (firstTimeMet(npc, ClientApp.getCurrentGame().getCurrentPlayer())) {
            npc.getRelations().computeIfAbsent(ClientApp.getCurrentGame().getCurrentPlayer(), k -> new Relation());
            npc.addXP(ClientApp.getCurrentGame().getCurrentPlayer(), 20);
            int lobbyId = ClientApp.getCurrentGame().getLobbyId();
            Message message = new Message(new HashMap<>() {{
                put("mode", "meet");
                put("lobbyId", lobbyId);
                put("username", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
                put("npcName", npcName);
                put("XP", 20);
            }}, Message.Type.interaction_p2npc);
            ClientApp.getServerConnectionThread().sendMessage(message);
        }
    }

    public GraphicalResult giftNPC(String npcName, String itemName) {
        Stacks stack = findItem(itemName);
        NPC npc = findNPC(npcName);
        if (stack.getItem() instanceof ToolType) {
            return new GraphicalResult("You can't gift any tool!");
        }
        if (ArtisanTypes.getArtisan(stack.getItem().getName()) != null) {
            return new GraphicalResult("You can't gift any artisan!");
        }
        ClientApp.getCurrentGame().getCurrentPlayer().getBackpack().reduceItems(stack.getItem(), 1);
        int xp = 0;
        if (firstTimeGift(npc, ClientApp.getCurrentGame().getCurrentPlayer())) {
            boolean addItem = false;
            int lobbyId = ClientApp.getCurrentGame().getLobbyId();
            for (Item item : npc.getFavorites()) {
                if (item.getName().equalsIgnoreCase(stack.getItem().getName())) {
                    npc.addXP(ClientApp.getCurrentGame().getCurrentPlayer(), 200);
                    xp = 200;
                    addItem = true;
                }
            }
            if (!addItem) {
                npc.addXP(ClientApp.getCurrentGame().getCurrentPlayer(), 50);
                xp = 50;
            }
            int finalXp = xp;
            Message message = new Message(new HashMap<>() {{
                put("mode", "gift");
                put("lobbyId", lobbyId);
                put("username", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
                put("npcName", npcName);
                put("XP", finalXp);
            }}, Message.Type.interaction_p2npc);
            ClientApp.getServerConnectionThread().sendMessage(message);
        }
        // TODO : Rassa dialogue
        OutsideView newOutsideView = new OutsideView();
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(newOutsideView);
        ClientApp.setCurrentMenu(newOutsideView);
        ResultController.addSuccess(npcName + " : Thank you! (You get " + xp + " xp)");

        float x = OutsideView.getGraphicalPositionInNPCMap(npc.getCurrentCell().getPosition().getX(),
                npc.getCurrentCell().getPosition().getY()).getX();
        float y = OutsideView.getGraphicalPositionInNPCMap(npc.getCurrentCell().getPosition().getX(),
                npc.getCurrentCell().getPosition().getY()).getY();

        Sprite itemSprite = new Sprite(stack.getItem().getTexture());
        itemSprite.setSize(72, 62);

        System.out.println(x + " " + y);
        System.out.println(newOutsideView.getPlayerController().getX() + " " +
                newOutsideView.getPlayerController().getY());

        PopUpController.addPopUp(new PopUpTexture(itemSprite
                ,newOutsideView.getPlayerController().getX(),newOutsideView.getPlayerController().getY(),
                x, y, 4
        ));


        return new GraphicalResult(npcName + " : Thank you! (You get " + xp + " xp)", false);
    }

    public Result questList() {
        StringBuilder result = new StringBuilder();
        result.append("Active Quests : ");
        result.append("\n");
        for (NPC npc : App.getCurrentGame().getNPCs()) {
            if (npc.getQuests() != null) {//Some NPCs doesn't have quests
                result.append(npc.getName()).append(" : \n");
                Quest[] quests = npc.getQuests();
                if (!quests[0].isDone()) {
                    if (quests[0].getRequest() == null) {
                        result.append("1. ").append("12*Plant");
                    } else {
                        result.append("1. ").append(quests[0].getRequest().getQuantity());
                        result.append("*").append(quests[0].getRequest().getItem().getName());
                    }
                    result.append(" -> ");
                    if (quests[0].getReward() != null) {
                        result.append(quests[0].getReward().getQuantity());
                        result.append("*").append(quests[0].getReward().getItem().getName());
                    } else { // Abigail give Friendship level
                        if (npc.getName().equalsIgnoreCase("Abigail")) {
                            result.append("+1 Friendship level");
                        }
                    }
                    result.append("\n");
                }
                if (!quests[1].isDone()
                        && npc.getRelations().get(App.getCurrentGame().getCurrentPlayer()).getLevel() > 0) {
                    result.append("2. ").append(quests[1].getRequest().getQuantity());
                    result.append("*").append(quests[1].getRequest().getItem().getName());
                    result.append(" -> ");
                    if (quests[1].getReward() != null) {
                        result.append(quests[1].getReward().getQuantity());
                        result.append("*").append(quests[1].getReward().getItem().getName());
                    } else { // Harvey give Friendship level
                        if (npc.getName().equalsIgnoreCase("Harvey")) {
                            result.append("+1 Friendship level");
                        }
                    }
                    result.append("\n");
                }
                if (!quests[2].isDone()
                        && npc.getRelations().get(App.getCurrentGame().getCurrentPlayer()).getLevel()
                        > 0 && canActivateThirdQuest(npc)) {
                    result.append("3. ").append(quests[2].getRequest().getQuantity());
                    result.append("*").append(quests[2].getRequest().getItem().getName());
                    result.append(" -> ");
                    result.append(quests[2].getReward().getQuantity());
                    result.append("*").append(quests[2].getReward().getItem().getName());
                    result.append("\n");
                }
                result.append("---------------------------------\n");
            }
        }
        return new Result(true, result.toString());
    }

    public Result questsFinish(String npcName, String stringIndex) {
        NPC npc = findNPC(npcName);
        int index = Integer.parseInt(stringIndex);
        index--;
        if (index < 0 || index > 2) {
            return new Result(false, "Invalid quest index!");
        }
        if (npc == null) {
            return new Result(false, "NPC not found!");
        }
        if (!isNPCNear(npc, App.getCurrentGame().getCurrentPlayer())) {
            return new Result(false, "NPC is not near you!");
        }
        Quest[] quests = npc.getQuests();
        if (quests[index].isDone()) {
            return new Result(false, "This quest is already done!");
        }
        Stacks stacks = quests[index].getRequest();
        Backpack backpack = App.getCurrentGame().getCurrentPlayer().getBackpack();
        if (index == 0 && npc.getName().equalsIgnoreCase("Harvey")) { // It gets 12 * any plant
            Ingredient ingredient = new Ingredient(new ArrayList<>(), 12);
            for (CropType cropType : CropType.values()) {
                ingredient.addPossibleIngredients(cropType.getFruit());
            }
            Player player = App.getCurrentGame().getCurrentPlayer();
            Item item = player.getAvailableIngredient(ingredient);
            if (item == null) {
                return new Result(false, "There is no such plant!");
            }
            player.getBackpack().reduceItems(item, 12);
        } else {
            if (!backpack.reduceItems(stacks.getItem(), stacks.getQuantity())) {
                return new Result(false, "You do not have enough item in backpack!");
            }
        }
        quests[index].setDone(true);
        int ratio = 1;
        if (npc.getRelations().get(App.getCurrentGame().getCurrentPlayer()).getLevel() > 1) {
            //If level is > 1 we get double reward
            ratio = 2;
        }
        if (index == 0 && npc.getName().equalsIgnoreCase("Abigail")) {
            Relation relation = npc.getRelations().get(App.getCurrentGame().getCurrentPlayer());
            relation.setLevel(Math.min(relation.getLevel() + ratio, 799));
            return new Result(true, npcName + " : Thank you! (You get " + ratio + " friendship level)");
        }
        if (index == 1 && npc.getName().equalsIgnoreCase("Harvey")) {
            Relation relation = npc.getRelations().get(App.getCurrentGame().getCurrentPlayer());
            relation.setLevel(Math.min(relation.getLevel() + ratio, 799));
            return new Result(true, npcName + " : Thank you! (You get " + ratio + " friendship level)");
        }
        if (index == 1 && npc.getName().equalsIgnoreCase("Lia")) {
            App.getCurrentGame().getCurrentPlayer().getAvailableCookingRecipes().add(Recipe.SalmonDinnerRecipe);
            return new Result(true, npcName + " : Thank you! (You get 1 * dinner salmon recipe!)");
        }
        if (index == 2 && npc.getName().equalsIgnoreCase("Abigail")) {
            Stacks deletedStack = null;
            for (Stacks stacks1 : backpack.getItems()) {
                if (stacks1.getItem().getName().equalsIgnoreCase("IridiumWateringCan")) {
                    backpack.addItems(stacks.getItem(), stacks.getStackLevel(), stacks.getQuantity());
                    quests[index].setDone(false);
                    return new Result(true, npcName + " : You have Iridium watering can already!");
                } else {
                    deletedStack = stacks1;
                }
            }
            if (deletedStack != null) {
                backpack.getItems().remove(deletedStack);
                backpack.addItems(ToolType.IridiumWateringCan, StackLevel.Iridium, 1);
            }
        }
        Stacks reward = quests[index].getReward();
        if (reward.getItem() == ShopItems.Coin) {
            App.getCurrentGame().getCurrentPlayer().addMoney(reward.getQuantity() * ratio);
            int amount = reward.getQuantity() * ratio;
            return new Result(true, npcName + " : Thank you! ( You get " + amount + " money)");
        }
        //TODO : momken hast ja nadashte bashim
        int amount = backpack.addItems(reward.getItem(), reward.getStackLevel(), reward.getQuantity() * ratio);
        return new Result(true, npcName + " : Thank you! ( You get " + reward.getQuantity() *
                ratio + "*" + reward.getItem().getName() + " )");
    }

    private boolean isNPCNear(NPC npc, Player player) {
        return player.getCurrentCell().getAdjacentCells().contains(npc.getCurrentCell());
    }

    public static NPC findNPC(String npcName) {
        for (NPC npc : ClientApp.getCurrentGame().getNPCs()) {
            if (npc.getName().equalsIgnoreCase(npcName)) {
                return npc;
            }
        }
        return null;
    }

    private boolean firstTimeMet(NPC npc, Player player) {
        if (player.getNpcMetToday().get(npc) == null || player.getNpcMetToday().get(npc) == Boolean.FALSE) {
            player.getNpcMetToday().put(npc, Boolean.TRUE);
            return true;
        } else {
            return false;
        }
    }

    private boolean firstTimeGift(NPC npc, Player player) {
        if (player.getNpcGiftToday().get(npc) == null || player.getNpcGiftToday().get(npc) == Boolean.FALSE) {
            player.getNpcGiftToday().put(npc, Boolean.TRUE);
            return true;
        } else {
            return false;
        }
    }

    private Stacks findItem(String itemName) {
        for (Stacks stack : ClientApp.getCurrentGame().getCurrentPlayer().getBackpack().getItems()) {
            if (stack.getQuantity() != 0) {
                if (stack.getItem().getName().equalsIgnoreCase(itemName)) {
                    return stack;
                }
            }
        }
        return null;
    }

    private boolean canActivateThirdQuest(NPC npc) {
        int daysPassed = ClientApp.getCurrentGame().getTime().getDaysPassed();
        return npc.getDaysForThirdQuest() <= daysPassed;
    }


    public ArrayList<Quest> getQuests(String npcName) {
        Message message = new Message(new HashMap<>() {{
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
            put("npcName", npcName);
        }}, Message.Type.get_npc_quests);

        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, 2 * TIMEOUT_MILLIS);
        if (response == null || response.getType() != Message.Type.response) {
            System.out.println("quests response is null");
            return new ArrayList<>();
        }
        ArrayList<Quest> quests = new ArrayList<>();
        for (LinkedTreeMap<String, Object> ti : response.<ArrayList<LinkedTreeMap<String, Object>>>getFromBody("quests")) {
            quests.add(new Quest(ti));
        }
        return quests;
    }

    public static boolean doIHaveThisQuest(Quest quest) {
        for (Quest quest1 : ClientApp.getCurrentGame().getCurrentPlayer().getActiveQuests()) {
            if ((Objects.equals(quest1.getRequest().getItem().getName(), quest.getRequest().getItem().getName())) &&
                    (Objects.equals(quest1.getReward().getItem().getName(), quest.getReward().getItem().getName()))) {
                return true;
            }
        }
        return false;
    }

    public static GraphicalResult finish(Quest quest, String npcName) {
        if (quest.isDone()) {
            return new GraphicalResult("Quest has been finished");
        }
        if (!doIHaveThisQuest(quest)) {
            return new GraphicalResult("You should get quest!");
        }
        Player currentPlayer = ClientApp.getCurrentGame().getCurrentPlayer();
        Backpack backpack = currentPlayer.getBackpack();
        if (!backpack.hasEnoughItem(quest.getRequest().getItem(), quest.getRequest().getQuantity())) {
            return new GraphicalResult("You don't have enough items!");
        }
        backpack.reduceItems(quest.getRequest().getItem(), quest.getRequest().getQuantity());
        if (quest.getReward().getItem() == ShopItems.RelationLevel) {
            CheatController controller = new CheatController();
            controller.cheatAddLevel(npcName, "1");
        } else if (quest.getReward().getItem() == Recipe.SalmonDinnerRecipe) {
            currentPlayer.getAvailableCookingRecipes().add(Recipe.SalmonDinnerRecipe);
        } else if (quest.getReward().getItem() == ShopItems.Coin) {
            currentPlayer.addMoney(quest.getReward().getQuantity());
        } else {
            backpack.addItems(quest.getReward().getItem(), quest.getReward().getStackLevel(), quest.getReward().getQuantity());
        }
        currentPlayer.getActiveQuests().remove(quest);
        Message message = new Message(new HashMap<>() {{
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
            put("quest", quest.getInfo());
            put("self", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
            put("npcName", npcName);
        }}, Message.Type.finish_quest);
        ClientApp.getServerConnectionThread().sendMessage(message);
        Main.getMain().getScreen().dispose();
        OutsideView newOutsideView = new OutsideView();
        ClientApp.setNonMainMenu(newOutsideView);
        Main.getMain().setScreen(newOutsideView);
        return new GraphicalResult("");
    }

    public static GraphicalResult addQuest(Quest quest) {
        if (ClientApp.getCurrentGame().getCurrentPlayer().getActiveQuests().contains(quest)) {
            return new GraphicalResult("You already have this quest!");
        } else if (quest.isDone()) {
            return new GraphicalResult("Quest has been finished");
        }
        ClientApp.getCurrentGame().getCurrentPlayer().getActiveQuests().add(quest);
        Main.getMain().getScreen().dispose();
        OutsideView newOutsideView = new OutsideView();
        ClientApp.setNonMainMenu(newOutsideView);
        Main.getMain().setScreen(newOutsideView);
        return new GraphicalResult("Quest added!");
    }

    public static ArrayList<Quest> getQuestsForJournal() {
        Message message = new Message(new HashMap<>() {{
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
        }}, Message.Type.get_quests_journal);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, TIMEOUT_MILLIS);
        if (response == null || response.getType() != Message.Type.response) {
            return new ArrayList<>();
        }
        ArrayList<Quest> quests = new ArrayList<>();
        ArrayList<Quest> deleted = new ArrayList<>();
        for (LinkedTreeMap<String, Object> ti : response.<ArrayList<LinkedTreeMap<String, Object>>>getFromBody("quests")) {
            quests.add(new Quest(ti));
        }
        for (Quest quest : quests) {
            if (quest.isDone()) {
                for (Quest quest1 : ClientApp.getCurrentGame().getCurrentPlayer().getActiveQuests()) {
                    if (Objects.equals(quest.getRequest().getItem().getName(), quest1.getRequest().getItem().getName())) {
                        if (Objects.equals(quest1.getReward().getItem().getName(), quest.getReward().getItem().getName())) {
                            deleted.add(quest);
                        }
                    }
                }
            }
        }
        for(Quest quest : deleted) {
            ClientApp.getCurrentGame().getCurrentPlayer().getActiveQuests().remove(quest);
        }
        return ClientApp.getCurrentGame().getCurrentPlayer().getActiveQuests();
    }

    public static void exit(){
        Main.getMain().getScreen().dispose();
        OutsideView newOutsideView = new OutsideView();
        ClientApp.setNonMainMenu(newOutsideView);
        Main.getMain().setScreen(newOutsideView);
    }
}
