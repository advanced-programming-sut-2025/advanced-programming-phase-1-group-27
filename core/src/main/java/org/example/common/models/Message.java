package org.example.common.models;

import java.util.HashMap;

public class Message {
    private Type type;
    private HashMap<String, Object> body;

    /*
     * Empty constructor needed for JSON Serialization/Deserialization
     */
    public Message() {
    }

    public Message(HashMap<String, Object> body, Type type) {
        this.body = body;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public <T> T getFromBody(String fieldName) {
        return (T) body.get(fieldName);
    }

    public int getIntFromBody(String fieldName) {
        return (int) ((double) ((Double) body.get(fieldName)));
    }

    public enum Type {
        command,
        response,
        register_request,
        login_request,
        get_user,
        add_user,
        set_password,
        set_online_user,
        change_profile,
        update_avatar,
        get_lobbies_list,
        create_lobby,
        join_lobby,
        find_lobby,
        get_online_users,
        create_game,
        start_game,
        choose_map,
        leave_lobby,
        purchase_from_shop, // clients use this to buy from shop
        update_shop,
        pass_an_hour,
        advance_time,
        set_weather,
        crows_attack,
        foraging_updates,
        enter_npc,
        leave_npc,
        walk_update,
        get_player_inventory
    }
}
