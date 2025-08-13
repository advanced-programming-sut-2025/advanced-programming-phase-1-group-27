package org.example.server.models.connections;

import org.example.common.database.DataBaseHelper;
import org.example.common.models.ConnectionThread;
import org.example.common.models.Message;
import org.example.server.controller.*;
import org.example.server.controller.InteractionsWithOthers.InteractionsWithNPCController;
import org.example.server.controller.InteractionsWithOthers.InteractionsWithUserController;
import org.example.server.controller.InteractionsWithOthers.MarriageController;
import org.example.server.controller.InteractionsWithOthers.TradeController;
import org.example.server.models.Lobby;
import org.example.server.models.ServerApp;
import org.example.server.models.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.UUID;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class ClientConnectionThread extends ConnectionThread {
    private static final String directoryPath = "music_uploads/";

    private User user = null;
    private String songId; // the song which is uploading
    private long bytesUploaded;

    public ClientConnectionThread(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    public boolean initialHandshake() {
        try {
            getClientAddress();
        } catch (Exception e) {
            return false;
        }
        ServerApp.addConnection(this);
        return true;
    }

    private void getClientAddress() {
        Message message = new Message(new HashMap<>() {{
            put("command", "get_client_address");
        }}, Message.Type.command);
        Message response = sendAndWaitForResponse(message, TIMEOUT_MILLIS);
        if (response == null || response.getType() != Message.Type.response) return;
        setOtherSideIP(response.getFromBody("ip"));
        setOtherSidePort(response.<Number>getFromBody("port").intValue());
    }

    @Override
    protected boolean handleMessage(Message message) {
        if (message.getType() == Message.Type.register_request) {
            sendMessage(RegisterMenuController.register(message));
            return true;
        } else if (message.getType() == Message.Type.login_request) {
            sendMessage(LoginMenuController.login(message, this));
            return true;
        } else if (message.getType() == Message.Type.update_user_info) {
            ServerApp.updateUser(message);
            return true;
        } else if (message.getType() == Message.Type.add_user) {
            RegisterMenuController.addUser(message);
            return true;
        } else if (message.getType() == Message.Type.set_online_user) {
            this.setUser(new User(message.getFromBody("userInfo")));
            return true;
        } else if (message.getType() == Message.Type.get_user) {
            sendMessage(ServerApp.getUserByUsername(message));
            return true;
        } else if (message.getType() == Message.Type.set_password) {
            ForgetPasswordMenuController.setPassword(message);
            return true;
        } else if (message.getType() == Message.Type.change_profile) {
            sendMessage(ProfileMenuController.change(message, this));
            return true;
        } else if (message.getType() == Message.Type.update_avatar) {
            ProfileMenuController.updateAvatar(message, user);
            return true;
        } else if (message.getType() == Message.Type.create_lobby) {
            sendMessage(LobbyController.createLobby(message));
            return true;
        } else if (message.getType() == Message.Type.join_lobby) {
            sendMessage(LobbyController.joinLobby(message));
            return true;
        } else if (message.getType() == Message.Type.get_lobbies_list) {
            sendMessage(LobbyController.getLobbiesList(message));
            return true;
        } else if (message.getType() == Message.Type.find_lobby) {
            sendMessage(LobbyController.findLobbyById(message));
            return true;
        } else if (message.getType() == Message.Type.create_game) {
            PregameMenuController.createGame(message);
            return true;
        } else if (message.getType() == Message.Type.get_online_users) {
            sendMessage(LobbyController.getOnlineUsersUsernames(message));
            return true;
        } else if (message.getType() == Message.Type.choose_map) {
            sendMessage(PregameMenuController.chooseMap(message));
            return true;
        } else if (message.getType() == Message.Type.leave_lobby) {
            PregameMenuController.leaveLobby(message);
            return true;
        } else if (message.getType() == Message.Type.advance_time) {
            TimeController.cheatAdvanceTime(message);
            sendMessage(new Message(null, Message.Type.response));
            return true;
        } else if (message.getType() == Message.Type.set_weather) {
            GameController.setTomorrowWeather(message);
            return true;
        } else if (message.getType() == Message.Type.enter_npc ||
                message.getType() == Message.Type.leave_npc ||
                message.getType() == Message.Type.walk_update) {
            GameController.notifyExcept(message);
            return true;
        } else if (message.getType() == Message.Type.purchase_from_shop) {
            sendMessage(GameController.purchase(message));
            return true;
        } else if (message.getType() == Message.Type.get_player_inventory) {
            sendMessage(GameController.getPlayerInventory(message));
            return true;
        } else if (message.getType() == Message.Type.interaction_p2npc) {
            InteractionsWithNPCController.handleMessage(message);
            return true;
        } else if (message.getType() == Message.Type.get_player_relation) {
            sendMessage(GameController.getPlayerRelation(message));
            return true;
        } else if (message.getType() == Message.Type.interaction_p2p) {
            GameController.handleP2P(message);
            return true;
        } else if (message.getType() == Message.Type.upload_song_request) {
            handleUploadRequest(message);
            return true;
        } else if (message.getType() == Message.Type.upload_complete) {
            handleUploadComplete();
            return true;
        } else if (message.getType() == Message.Type.play_song_request) {
            handlePlaySongRequest(message);
            return true;
        } else if (message.getType() == Message.Type.get_player_music) {
            sendMessage(GameController.getPlayerMusicInfo(message));
            return true;
        } else if (message.getType() == Message.Type.save_and_exit_game) {
            GameController.saveAndExit(message);
            return true;
        } else if (message.getType() == Message.Type.voting) {
            GameController.handleVote(message);
            return true;
        } else if (message.getType() == Message.Type.chat) {
            GameController.handleChat(message);
            return true;
        } else if (message.getType() == Message.Type.update_mini_player) {
            GameController.handleMiniPlayerUpdate(message);
            return true;
        } else if (message.getType() == Message.Type.get_trade_history) {
            sendMessage(TradeController.getTradesBetweenUsers(message));
            return true;
        } else if (message.getType() == Message.Type.can_marry) {
            sendMessage(MarriageController.canMarry(message));
            return true;
        } else if (message.getType() == Message.Type.marriage_request) {
            MarriageController.sendMarriageRequest(message);
            return true;
        } else if (message.getType() == Message.Type.marriage_response) {
            MarriageController.sendMarriageResponse(message);
            return true;
        } else if (message.getType() == Message.Type.reaction) {
            GameController.handleReaction(message);
            return true;
        } else if (message.getType() == Message.Type.get_gift_history) {
            sendMessage(InteractionsWithUserController.getGiftHistory(message));
            return true;
        } else if (message.getType() == Message.Type.rate_gift) {
            InteractionsWithUserController.rate(message);
            return true;
        } else if (message.getType() == Message.Type.client_game_info) {
            SaveController.handleInfo(message);
            return true;
        } else if (message.getType() == Message.Type.add_player_level) {
            sendMessage(InteractionsWithUserController.cheatAddPlayerLevel(message));
            return true;
        } else if (message.getType() == Message.Type.add_npc_level) {
            InteractionsWithNPCController.cheatAddLevel(message);
            return true;
        } else if (message.getType() == Message.Type.get_npc_quests){
            sendMessage(InteractionsWithNPCController.getQuests(message));
            return true;
        } else if (message.getType() == Message.Type.do_i_have_quest){
            sendMessage(InteractionsWithNPCController.doIHaveThisQuest(message));
            return true;
        } else if (message.getType() == Message.Type.finish_quest){
            InteractionsWithNPCController.finishQuest(message);
            return true;
        } else if(message.getType() == Message.Type.get_quests_journal){
            sendMessage(InteractionsWithNPCController.getQuestsJournal(message));
            return true;
        } else if (message.getType() == Message.Type.meetP2P){
            InteractionsWithUserController.meet(message);
            return true;
        } else if (message.getType() == Message.Type.load_game) {
            LoadGameController.handleLoadRequest(message);
            return true;
        }
        return false;
    }

    @Override
    protected void handleBinaryPacket(byte[] packet) {
        if (this.songId == null) {
            System.err.println("No active song. packet ignored ...");
            return;
        }
        try {
            Files.write(Paths.get(directoryPath + this.songId + ".ogg"), packet, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            bytesUploaded += packet.length;

        } catch (IOException e) {
            System.err.println("failed to write chunk for song " + songId);
            e.printStackTrace();
            this.songId = null;
        }
    }

    @Override
    public void run() {
        super.run();
        ServerApp.removeConnection(this);
        // TODO: hazf kon bebin chi mishe
        this.end();
    }

    private void handleUploadRequest(Message message) {
        this.songId = UUID.randomUUID().toString();
        this.bytesUploaded = 0;
    }

    private void handleUploadComplete() {
        sendMessage(new Message(new HashMap<>() {{
            put("songId", songId);
        }}, Message.Type.response));
        songId = null;
    }

    private void handlePlaySongRequest(Message message) {
        String songId = message.getFromBody("songId");
        String songName = message.getFromBody("songName");
        Lobby lobby = ServerApp.getLobbyById(message.getIntFromBody("lobbyId"));
        assert lobby != null;
        lobby.getGame().setPlayerMusic(message.getFromBody("username"), songId, songName);

        File file = new File(directoryPath + songId + ".ogg");
        if (!file.exists()) {
            System.err.println("song " + songId + " does not exist");
            return;
        }
        new Thread(() -> {
            sendMessage(new Message(new HashMap<>() {{
                put("songId", songId);
            }}, Message.Type.start_download));
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    byte[] packet = new byte[bytesRead];
                    System.arraycopy(buffer, 0, packet, 0, bytesRead);
                    sendBinaryPacket(packet);
                }
                sendMessage(new Message(null, Message.Type.download_complete));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
