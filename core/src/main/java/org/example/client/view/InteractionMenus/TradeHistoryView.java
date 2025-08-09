package org.example.client.view.InteractionMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.InteractionsWithOthers.TradeController;
import org.example.client.view.AppMenu;
import org.example.server.models.Relations.Trade;
import org.example.server.models.Stacks;
import java.util.ArrayList;
import java.util.Scanner;

public class TradeHistoryView extends AppMenu {
    private final TradeController controller;

    private final String username;
    private final ArrayList<Trade> trades;

    private Table table;
    private ScrollPane scrollPane;

    private final TextButton exitButton;

    private Stage stage;

    public TradeHistoryView(String username) {
        controller = new TradeController();
        this.username = username;
        trades = controller.getTradeHistory(username);

        exitButton = new TextButton("Exit" , skin);
        table = new Table();
        scrollPane = new ScrollPane(table, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        stage = new Stage(new ScreenViewport());


        setListeners();
    }

    private void displayButtons(){
        exitButton.setPosition(0 , 0);
        stage.addActor(exitButton);
    }

    private void displayItems() {
        table.clear();

        Table firstRow = new Table();
        Label fromLabel = new Label("From:", skin);
        Label toLabel = new Label("To:", skin);
        Label items1 = new Label("Sent:", skin);
        Label items2 = new Label("Received:", skin);

        fromLabel.setColor(Color.BLACK);
        toLabel.setColor(Color.BLACK);
        items1.setColor(Color.BLACK);
        items2.setColor(Color.BLACK);

        firstRow.add(fromLabel).width(200).left();
        firstRow.add(toLabel).width(200).left();
        firstRow.add(items1).width(200).right();
        firstRow.add(items2).width(200).right();
        table.add(firstRow).width(1000).padBottom(5);
        table.row();

        for (Trade trade : trades) {
            Table row = new Table();
            Label fromLabel1 = new Label(trade.getStarter(), skin);
            Label toLabel1 = new Label(trade.getOther(), skin);
            StringBuilder starter = new StringBuilder();
            for(Stacks stacks : trade.getStarterSelected()){
                starter.append(stacks.getItem().getName()).append(" * ").append(stacks.getQuantity());
                starter.append("\n");
            }
            StringBuilder other = new StringBuilder();
            for(Stacks stacks : trade.getOthersSelected()){
                other.append(stacks.getItem().getName()).append(" * ").append(stacks.getQuantity());
                other.append("\n");
            }
            Label items11 = new Label(starter, skin);
            Label items22 = new Label(other, skin);

            fromLabel1.setColor(Color.BLACK);
            toLabel1.setColor(Color.BLACK);
            items11.setColor(Color.BLACK);
            items22.setColor(Color.BLACK);


            row.add(fromLabel1).width(200).left();
            row.add(toLabel1).width(200).left();
            row.add(items11).width(200).right();
            row.add(items22).width(200).right();

            float itemHeight = 50f;
            row.setHeight(itemHeight);
            table.add(row).width(800).padBottom(5);
            table.row();
        }

        scrollPane.setSize(1200, Gdx.graphics.getHeight() - 400);
        scrollPane.setPosition(300, 200);
    }


    private void displayBackground() {
        stage.addActor(menuBackground);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        displayBackground();
        displayButtons();

        stage.addActor(scrollPane);
        displayItems();
    }

    @Override
    public void render(float delta) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        Main.getBatch().begin();
        Main.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void executeCommands(Scanner scanner) {

    }

    private void setListeners() {
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.exit(username);
            }
        });
    }
}
