package org.example.common.models.NPCs;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.google.gson.internal.LinkedTreeMap;
import org.example.common.models.Stacks;

import java.util.HashMap;

public class Quest {

    private Stacks reward;
    private Stacks request;
    private String playerName;
    private boolean isDone;
    private int index;

    private Label rewardLabel;
    private Label requestLabel;

    public Quest(Stacks request, Stacks reward , int index) {
        this.reward = reward;
        this.request = request;
        this.playerName = "";
        this.isDone = false;
        this.index = index;
    }

    public HashMap<String, Object> getInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("reward", reward.getInfo());
        info.put("request", request.getInfo());
        info.put("playerName", playerName);
        info.put("isDone", isDone);
        info.put("index", index);
        return info;
    }

    public Quest (LinkedTreeMap<String, Object> info) {
        this.request = new Stacks((LinkedTreeMap<String, Object>) info.get("request"));
        this.reward = new Stacks((LinkedTreeMap<String, Object>) info.get("reward"));
        this.playerName = (String) info.get("playerName");
        this.isDone = (boolean) info.get("isDone");
        this.index = ((Number) info.get("index")).intValue();
    }

    public Stacks getReward() {
        return reward;
    }

    public Stacks getRequest() {
        return request;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

//    public void initLabels(Stage stage){
//
//        rewardLabel
//
//        stage.addActor(rewardLabel);
//        stage.addActor(requestLabel);
//
//    }
//
//    public void deleteLabels(){
//
//        rewardLabel.remove();
//        requestLabel.remove();
//
//    }
//
//    public Label getRequestLabel() {
//        return requestLabel;
//    }
//
//    public Label getRewardLabel() {
//        return rewardLabel;
//    }

}
