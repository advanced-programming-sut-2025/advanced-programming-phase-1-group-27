package org.example.client.controller.InteractionsWithOthers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.controller.CheatController;
import org.example.client.controller.PopUpController;
import org.example.client.controller.ResultController;
import org.example.client.model.*;
import org.example.client.view.OutsideView;
import org.example.common.models.*;
import org.example.common.models.NPCs.NPC;
import org.example.common.models.NPCs.Quest;
import org.example.common.models.Relations.Relation;
import org.example.common.models.Plants.CropType;
import org.example.common.models.items.Recipe;
import org.example.common.models.items.ShopItems;
import org.example.common.models.items.ToolType;
import org.example.common.models.tools.Backpack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static org.example.client.model.ClientApp.TIMEOUT_MILLIS;

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
                    npc.getRelations().computeIfAbsent(ClientApp.getCurrentGame().getCurrentPlayer(), k -> new Relation());
                    npc.addXP(ClientApp.getCurrentGame().getCurrentPlayer(), 200);
                    xp = 200;
                    addItem = true;
                }
            }
            if (!addItem) {
                npc.getRelations().computeIfAbsent(ClientApp.getCurrentGame().getCurrentPlayer(), k -> new Relation());
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

        PopUpController.addPopUp(new PopUpTexture(itemSprite
                ,newOutsideView.getPlayerController().getX(),newOutsideView.getPlayerController().getY(),
                x, y, 4
        ));


        return new GraphicalResult(npcName + " : Thank you! (You get " + xp + " xp)", false);
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

        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, 4 * TIMEOUT_MILLIS);
        if (response == null || response.getType() != Message.Type.response) {
            System.out.println(response == null? "null" : response.getType());
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
        ClientApp.getCurrentGame().getCurrentPlayer().addNumberOfQuestsDone();

        Main.getMain().getScreen().dispose();
        OutsideView newOutsideView = new OutsideView();
        ClientApp.setNonMainMenu(newOutsideView);
        Main.getMain().setScreen(newOutsideView);

        Sprite itemSprite = new Sprite(GameAssetManager.getGameAssetManager().getQuestStar());
        itemSprite.setSize(72, 62);
        PopUpController.addPopUp(new PopUpTexture(itemSprite
                ,newOutsideView.getPlayerController().getX(),newOutsideView.getPlayerController().getY()+70,
                newOutsideView.getPlayerController().getX(),newOutsideView.getPlayerController().getY(), 4
        ));
        return new GraphicalResult("");
    }

    public static GraphicalResult addQuest(Quest quest) {
        if ( doIHaveThisQuest(quest) ) {
            return new GraphicalResult("You already have this quest!");
        } else if (quest.isDone()) {
            return new GraphicalResult("Quest has been finished");
        } else if(ClientApp.getCurrentGame().getCurrentPlayer().getActiveQuests().size() + 1 > 3) {
            return new GraphicalResult("You can't add more quests!");
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
                            deleted.add(quest1);
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
