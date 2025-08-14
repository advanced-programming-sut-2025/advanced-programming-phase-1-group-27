package org.example.client.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import org.example.client.Main;
import org.example.client.controller.InteractionsWithOthers.InteractionsWithUserController;
import org.example.client.controller.menus.MenuController;
import org.example.client.model.*;
import org.example.client.model.enums.Emoji;
import org.example.client.view.HUDView;
import org.example.client.view.InteractionMenus.InteractionMenu;
import org.example.client.view.InteractionMenus.Trade.PreTradeMenuView;
import org.example.common.models.*;
import org.example.common.models.NPCs.NPC;
import org.example.common.models.Relations.Relation;
import org.example.common.models.Seasons.Season;
import org.example.common.models.Weathers.Weather;
import org.example.common.models.commands.CheatCommands;
import org.example.common.models.items.Recipe;
import org.example.common.models.items.products.CookingProduct;
import org.example.common.models.items.products.CraftingProduct;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;

import static org.example.client.model.ClientApp.TIMEOUT_MILLIS;

public class HUDController extends MenuController {

    private final HUDView view;

    public HUDController(HUDView view) {
        this.view = view;
    }

    public void reactWithText(String reactionText) {
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
            put("username", ClientApp.getLoggedInUser().getUsername());
            put("reaction", new Reaction(reactionText).getInfo());
        }}, Message.Type.reaction));
    }

    public void reactWithEmoji(Emoji emoji) {
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
            put("username", ClientApp.getLoggedInUser().getUsername());
            put("reaction", new Reaction(emoji).getInfo());
        }}, Message.Type.reaction));
    }

    public boolean gotGiftedToday(NPCType type) {

        NPC npc = ClientApp.getCurrentGame().getNPCByType(type);
        Boolean bool = ClientApp.getCurrentGame().getCurrentPlayer().getNpcGiftToday().get(npc);
        if (bool == null) {
            ClientApp.getCurrentGame().getCurrentPlayer().getNpcGiftToday().put(npc, Boolean.FALSE);
            return false;
        }
        return bool;

    }

    public boolean metToday(NPCType type) {

        NPC npc = ClientApp.getCurrentGame().getNPCByType(type);
        Boolean bool = ClientApp.getCurrentGame().getCurrentPlayer().getNpcMetToday().get(npc);
        if (bool == null) {
            ClientApp.getCurrentGame().getCurrentPlayer().getNpcMetToday().put(npc, Boolean.FALSE);
            return false;
        }
        return bool;

    }

    public String getNPCInfo(NPCType type) {

        NPC npc = ClientApp.getCurrentGame().getNPCByType(type);
        Relation relation = npc.getRelations().computeIfAbsent(
                ClientApp.getCurrentGame().getCurrentPlayer(), k -> new Relation()
        );
        StringBuilder sb = new StringBuilder();

        sb.append("Level: ").append(relation.getLevel()).append("    ").
                append("XP: ").append(relation.getXp());


        return sb.toString();

    }

    public int digitCount() {

        return String.valueOf(Math.abs(ClientApp.getCurrentGame().getCurrentPlayer().getMoney())).length();

    }

    public void setMoneyInfo(Label label) {

        String money = Integer.toString(ClientApp.getCurrentGame().getCurrentPlayer().getMoney());

        String output = String.join(" ", money.split(""));

        label.setText(output);

    }

    public void setTimeInfo(Label label) {

        int time = ClientApp.getCurrentGame().getTime().getHour();

        String timeFormat = ((time > 12) ? time - 12 : time) + ((time > 12) ? "  pm" : "  am");
        label.setText(timeFormat);

    }

    public void setDayInfo(Label label) {

        String day = ClientApp.getCurrentGame().getTime().getDayOfWeek().substring(0, 3) + ". " + ClientApp.getCurrentGame().getTime().getDate();
        label.setText(day);

    }

    public float getSlotPosition() {

        Integer slotIndex = ClientApp.getCurrentGame().getCurrentPlayer().getCurrentInventorySlotIndex();

        float imageSize = GameAssetManager.getGameAssetManager().getInventorySelectSlot().getWidth();

        if (slotIndex == 0 || slotIndex == 1 || slotIndex == 2) {

            return imageSize * slotIndex;

        } else {

            return (imageSize * slotIndex + slotIndex);

        }


    }

    public float getItemPosition(Integer slotIndex) {

        float imageSize = GameAssetManager.getGameAssetManager().getInventorySelectSlot().getWidth();

        if (slotIndex == 0 || slotIndex == 1 || slotIndex == 2) {

            return imageSize * slotIndex;

        } else {

            return (imageSize * slotIndex + slotIndex);

        }


    }

    public void updateSlotIndex(Integer slotChange) {

        Player player = ClientApp.getCurrentGame().getCurrentPlayer();

        Integer currentSlot = player.getCurrentInventorySlotIndex();

        if (currentSlot + slotChange > 11) {
            player.setCurrentInventorySlotIndex(0);
        } else if (currentSlot + slotChange < 0) {
            player.setCurrentInventorySlotIndex(11);
        } else {
            player.setCurrentInventorySlotIndex(currentSlot + slotChange);
        }

    }

    public void quickSetHotBarIndex(int index) {
        ClientApp.getCurrentGame().getCurrentPlayer().setCurrentInventorySlotIndex(index);
    }

    private Image getClockByGameState() {

        ClientGame currentGame = ClientApp.getCurrentGame();

        if (currentGame.getTime().getSeason().equals(Season.Spring)) {

            if (currentGame.getCurrentWeather().equals(Weather.Rainy)) {
                return GameAssetManager.getGameAssetManager().getRainySpring();
            } else if (currentGame.getCurrentWeather().equals(Weather.Sunny)) {
                return GameAssetManager.getGameAssetManager().getSunnySpring();
            } else if (currentGame.getCurrentWeather().equals(Weather.Stormy)) {
                return GameAssetManager.getGameAssetManager().getStormySpring();
            } else {
                return GameAssetManager.getGameAssetManager().getSnowySpring();
            }

        } else if (ClientApp.getCurrentGame().getTime().getSeason().equals(Season.Summer)) {

            if (currentGame.getCurrentWeather().equals(Weather.Rainy)) {
                return GameAssetManager.getGameAssetManager().getRainySummer();
            } else if (currentGame.getCurrentWeather().equals(Weather.Sunny)) {
                return GameAssetManager.getGameAssetManager().getSunnySummer();
            } else if (currentGame.getCurrentWeather().equals(Weather.Stormy)) {
                return GameAssetManager.getGameAssetManager().getStormySummer();
            } else {
                return GameAssetManager.getGameAssetManager().getSnowySummer();
            }

        } else if (ClientApp.getCurrentGame().getTime().getSeason().equals(Season.Fall)) {

            if (currentGame.getCurrentWeather().equals(Weather.Rainy)) {
                return GameAssetManager.getGameAssetManager().getRainyFall();
            } else if (currentGame.getCurrentWeather().equals(Weather.Sunny)) {
                return GameAssetManager.getGameAssetManager().getSunnyFall();
            } else if (currentGame.getCurrentWeather().equals(Weather.Stormy)) {
                return GameAssetManager.getGameAssetManager().getStormyFall();
            } else {
                return GameAssetManager.getGameAssetManager().getSnowyFall();
            }

        }

        if (currentGame.getCurrentWeather().equals(Weather.Rainy)) {
            return GameAssetManager.getGameAssetManager().getRainyWinter();
        } else if (currentGame.getCurrentWeather().equals(Weather.Sunny)) {
            return GameAssetManager.getGameAssetManager().getSunnyWinter();
        } else if (currentGame.getCurrentWeather().equals(Weather.Stormy)) {
            return GameAssetManager.getGameAssetManager().getStormyWinter();
        } else {
            return GameAssetManager.getGameAssetManager().getSnowyWinter();
        }

    }

    public float getClockArrowDegree() {

        float hour = (float) ClientApp.getCurrentGame().getTime().getHour();
        return -180 * (hour - 9f) / (22f - 9f);

    }

    public void updateClockImage() {
        view.setClockImage(getClockByGameState());
    }

    public GraphicalResult handleTextInput() {

        String inputText = view.getTextInputField().getText();

        GraphicalResult graphicalResult = handleCheat(inputText);

        closeTextInputField();

        return graphicalResult;

    }

    public GraphicalResult craft(CraftingProduct craftingProduct) {
        Recipe recipe = craftingProduct.getRecipe();
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
        if (!player.getBackpack().canAdd(craftingProduct, StackLevel.Basic, 1))
            return new GraphicalResult("Your backpack is full!");
        player.useRecipe(recipe);
        if (player.getEnergy() < 2) {
            player.consumeEnergy(player.getEnergy());
            player.getBackpack().reduceItems(recipe.getFinalProduct(), 1);
            return new GraphicalResult("Crafting failed! You don't have enough energy!");
        }
        player.consumeEnergy(2);
        return new GraphicalResult(craftingProduct.getName() + " crafted successfully", false);
    }

    public GraphicalResult cook(CookingProduct cookingProduct) {
        Recipe recipe = cookingProduct.getRecipe();
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
        if (!player.getBackpack().canAdd(recipe.getFinalProduct(), StackLevel.Basic, 1))
            return new GraphicalResult("Your backpack is full!");
        player.useRecipe(recipe);
        if (player.getEnergy() < 3) {
            player.consumeEnergy(player.getEnergy());
            player.getBackpack().reduceItems(recipe.getFinalProduct(), StackLevel.Basic, 1);
            return new GraphicalResult("Cooking failed! You don't have enough energy!");
        }
        player.consumeEnergy(3);
        return new GraphicalResult(cookingProduct.getName() + " cooked successfully!", false);
    }

    public GraphicalResult removeFromInventory(Stacks slot) {
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
        double modifier = player.getTrashCan() == null ? 0 : player.getTrashCan().getTrashCanModifier();
        int money = (int) (slot.getTotalPrice() * modifier);
        player.addMoney(money);
        player.getBackpack().reduceItems(slot.getItem(), slot.getQuantity());
        return new GraphicalResult(
                slot.getItem() + " (" + slot.getQuantity() + ")  removed successfully. You earned " + money,
                false
        );
    }

    public void openTradeMenu(int target) {

        String targetUser = ClientApp.getCurrentGame().getPlayers().get(target).getUsername();
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new PreTradeMenuView(targetUser));

    }

    public void openTradeMenu2(String targetUser) {

        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new PreTradeMenuView(targetUser));


    }

    public void openInteractionMenu(String targetUser) {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new InteractionMenu(targetUser));
    }

    private GraphicalResult handleCheat(String input) {

        Matcher matcher;
        CheatController controller = new CheatController();
        Result result = new Result(false, "Invalid Command");

        if ((matcher = CheatCommands.CheatSetWeather.getMatcher(input)) != null) {
            result = controller.cheatSetWeather(matcher.group("weatherName").trim());
        } else if ((matcher = CheatCommands.CheatThor.getMatcher(input)) != null) {
            result = controller.cheatThor(matcher.group("i"), matcher.group("j"));
        } else if ((matcher = CheatCommands.CheatSetEnergy.getMatcher(input)) != null) {
            result = controller.cheatSetEnergy(matcher.group("value"));
        } else if (CheatCommands.CheatEnergyUnlimited.getMatcher(input) != null) {
            result = controller.cheatEnergyUnlimited();
        } else if ((matcher = CheatCommands.CheatAdvanceTime.getMatcher(input)) != null) {
            result = controller.cheatAdvanceTime(matcher.group("timeString"));
        } else if ((matcher = CheatCommands.CheatAdvanceDate.getMatcher(input)) != null) {
            result = controller.cheatAdvanceDate(matcher.group("dayString"));
        } else if ((matcher = CheatCommands.CheatAddItem.getMatcher(input)) != null) {
            result = controller.cheatAddItem(
                    matcher.group("itemName").trim(),
                    Integer.parseInt(matcher.group("count")));
        } else if ((matcher = CheatCommands.CheatSetFriendShip.getMatcher(input)) != null) {
            result = controller.cheatSetFriendship(matcher.group("name"), matcher.group("amount"));
        } else if ((matcher = CheatCommands.CheatAddMoney.getMatcher(input)) != null) {
            result = controller.cheatAddMoney(matcher.group("amount"));
        } else if ((matcher = CheatCommands.CheatSetAbility.getMatcher(input)) != null) {
            result = controller.cheatSetAbility(
                    matcher.group("abilityName").trim(),
                    Integer.parseInt(matcher.group("level").trim()));
        } else if ((matcher = CheatCommands.CheatAddLevelNPC.getMatcher(input)) != null) {
            result = controller.cheatAddLevel(
                    matcher.group("name").trim(),
                    matcher.group("level").trim());
        } else if ((matcher = CheatCommands.CheatAddLevelPlayer.getMatcher(input)) != null) {
            result = controller.cheatAddPlayerLevel(
                    matcher.group("name").trim(),
                    matcher.group("level").trim());
        } else if ((matcher = CheatCommands.CheatAddXPPlayer.getMatcher(input)) != null) {
            result = controller.cheatAddPlayerXP(
                    matcher.group("name").trim(),
                    matcher.group("level").trim());
        } else if ((matcher = CheatCommands.ToAllChat.getMatcher(input)) != null) {
            ///  TODO PARSA MET TODAY ALL
            String text = trimMessage(matcher.group("chatText").trim());
            text = ClientApp.getLoggedInUser().getUsername() + ":\n" + text;
            for (MiniPlayer miniPlayer : ClientApp.getCurrentGame().getPlayers()) {
                if (miniPlayer.getUsername().equals(ClientApp.getCurrentGame().getCurrentPlayer().getUsername())) {
                    continue;
                } else {
                    InteractionsWithUserController.meet(miniPlayer.getUsername());
                }
            }
            result = sendToAll(text);
        } else if ((matcher = CheatCommands.PrivateChat.getMatcher(input)) != null) {
            ///  TODO PARSA MET TODAY USER
            String text = trimMessage(matcher.group("chatText").trim());
            text = ClientApp.getLoggedInUser().getUsername() + ":\n" + text;
            String targetUsername = matcher.group("targetPlayer").trim();
            InteractionsWithUserController.meet(targetUsername);
            result = sendToPerson(text, targetUsername);
        }
        return new GraphicalResult(result.message(), result.success() ? GameAssetManager.getGameAssetManager().getAcceptColor() : GameAssetManager.getGameAssetManager().getErrorColor(), result.success());
    }

    public Result sendToAll(String text) {
        if (calcMessageLines(text) > 6)
            return new Result(false, "Message too long!");
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("mode", "sendToAll");
            put("sender", ClientApp.getLoggedInUser().getUsername());
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
            put("message", text);
        }}, Message.Type.chat));
        return new Result(true, "Message sent successfully");
    }

    public Result sendToPerson(String text, String username) {
        if (calcMessageLines(text) > 6)
            return new Result(false, "Message too long!");
        if (ClientApp.getCurrentGame().getMiniPlayerByUsername(username) == null)
            return new Result(false, "Username not found");
        if (ClientApp.getLoggedInUser().getUsername().equals(username))
            return new Result(false, "You can't message to yourself");
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("mode", "sendToPerson");
            put("username", username);
            put("message", text);
        }}, Message.Type.chat));
        return new Result(true, "Message sent successfully");
    }

    private String trimMessage(String text) {
        return text.replaceAll("(.{18})", "$1-\n").trim();
    }

    private int calcMessageLines(String text) {
        int numberOfLines = 0;
        for (char c : text.toCharArray()) {
            if (c == '\n')
                ++numberOfLines;
        }
        return numberOfLines;
    }

    public void handleTabPressInTextInput() {

        String currentText = view.getTextInputField().getText().trim();
        if (currentText.charAt(0) != '@' || currentText.split("\\s").length > 1) {
            return;
        }

        String currentUsername = currentText.substring(1);
        for (MiniPlayer inGamePlayer : ClientApp.getCurrentGame().getPlayers()) {

            if (!ClientApp.getCurrentGame().getCurrentPlayer().getUsername().equals(inGamePlayer.getUsername())) {
                if (inGamePlayer.getUsername().startsWith(currentUsername)) {
                    view.getTextInputField().setText("@" + inGamePlayer.getUsername() + " ");
                    view.getTextInputField().setCursorPosition(view.getTextInputField().getText().length());
                }
            }

        }

    }

    public void closeTextInputField() {

        view.setInputFieldVisible(false);
        view.getTextInputField().setText("");

    }

    public GraphicalResult saveAndExit() {
        if (ClientApp.getCurrentGame().getAdmin().getUsername().equals(ClientApp.getLoggedInUser().getUsername())) {
            ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
                put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
            }}, Message.Type.save_and_exit_game));
            return new GraphicalResult("Redirecting to main menu ...", false);
        } else
            return new GraphicalResult("Only the host can exit the game.");
    }

    public void terminateGame() {
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("mode", "askToTerminate");
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
        }}, Message.Type.voting));
    }

    public void kickPlayer(String playerName) {
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("mode", "askToKick");
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
            put("playerName", playerName);
        }}, Message.Type.voting));
    }

    public void openFileChooser() {
        SwingUtilities.invokeLater(() -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "OGG Files", "ogg"
            ));

            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                uploadSong(file);
            }
        });
    }

    private void uploadSong(File file) {
        new Thread(() -> {
            ClientApp.getServerConnectionThread().sendMessage(new Message(null, Message.Type.upload_song_request));
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    byte[] chunk = new byte[bytesRead];
                    System.arraycopy(buffer, 0, chunk, 0, bytesRead);
                    ClientApp.getServerConnectionThread().sendBinaryPacket(chunk);
                }

                Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(
                        new Message(null, Message.Type.upload_complete),
                        TIMEOUT_MILLIS
                );
                String songId = response.getFromBody("songId");
                ClientApp.getCurrentGame().addSong(file.getName(), songId);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void playSong(String songId, String songName) {
        MusicInfo musicInfo = new MusicInfo(songId, songName);
        playSongFromServer(musicInfo);
    }

    public GraphicalResult listenWith(String playerName) {
        if (playerName == null)
            return new GraphicalResult("Please select a player to listen.");
        MusicInfo musicInfo = getPlayerMusicInfo(playerName);
        if (musicInfo == null)
            return new GraphicalResult("This player is not currently listening to any song!");
        playSongFromServer(musicInfo);
        return new GraphicalResult("");
    }

    private MusicInfo getPlayerMusicInfo(String playerName) {
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(
                new Message(new HashMap<>() {{
                    put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
                    put("playerName", playerName);
                }}, Message.Type.get_player_music),
                TIMEOUT_MILLIS
        );
        String songId = response.getFromBody("songId");
        if (songId == null)
            return null;
        String songName = response.getFromBody("songName");
        float offset = ((Number) response.getFromBody("offset")).floatValue();
        return new MusicInfo(songId, songName, offset);
    }

    private void playSongFromServer(MusicInfo musicInfo) {
        ClientApp.getCurrentGame().setCurrentMusicName(musicInfo.getSongName());
        ClientApp.getServerConnectionThread().setOnDownloadComplete(fileHandle -> {
            Music music = Gdx.audio.newMusic(fileHandle);
            ClientApp.getCurrentGame().setCurrentMusic(music, musicInfo.getOffset());
        });
        ClientApp.getServerConnectionThread().sendMessage(
                new Message(new HashMap<>() {{
                    put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
                    put("username", ClientApp.getLoggedInUser().getUsername());
                    put("songId", musicInfo.getSongId());
                    put("songName", musicInfo.getSongName());
                }}, Message.Type.play_song_request)
        );
    }

    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {
        return null;
    }
}
