package org.example.client.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.example.client.model.ClientApp;
import org.example.client.model.PopUpTexture;
import org.example.client.view.GameView;
import org.example.client.view.HomeView;
import org.example.common.models.Item;
import org.example.common.models.Player;
import org.example.common.models.Result;

public class HomeGraphicalController {
    HomeView view;

    public HomeGraphicalController(HomeView view) {
        this.view = view;
    }

    private void handleEating() {
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {

            Item item = player.getBackpack().get(player.getCurrentInventorySlotIndex()).getItem();
            Result result = new GameMenuController(new GameView()).eatFood(item.getName());
            ResultController.addResult(result);
            if (result.success()) {
                PopUpController.addPopUp(new PopUpTexture(
                        item.getTexture(),
                        view.getPlayerController().getX(), view.getPlayerController().getY() + 30,
                        view.getPlayerController().getX(), view.getPlayerController().getY(),
                        1f
                ));
            }
        }
    }

    public void update() {
        handleEating();
    }
}
