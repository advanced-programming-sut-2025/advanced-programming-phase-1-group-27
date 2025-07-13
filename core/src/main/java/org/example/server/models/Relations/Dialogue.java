package org.example.server.models.Relations;

import org.example.server.models.Player;
import org.example.server.models.enums.DialogueType;

public class Dialogue {
    private DialogueType type;
    private String Respond;
    private String input;
    private Player responder;
    private Player sender;

    public Dialogue(DialogueType type, String Respond, String input, Player responder, Player sender) {
        this.type = type;
        this.Respond = Respond;
        this.input = input;
        this.responder = responder;
        this.sender = sender;
    }

    public DialogueType getType() {
        return type;
    }

    public String getRespond() {
        return Respond;
    }

    public void setRespond(String Respond) {
        this.Respond = Respond;
    }

    public String getInput() {
        return input;
    }

    public Player getResponder() {
        return responder;
    }

    public Player getSender() {
        return sender;
    }

}
