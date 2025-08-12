package org.example.client.controller;

import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.model.enums.FishMovementType;
import org.example.client.model.enums.MiniGameDifficulty;
import org.example.client.view.MiniGame.MiniGameView;
import org.example.client.view.MiniGame.PostMiniGameMenuView;
import org.example.client.view.OutsideView;
import org.example.server.models.App;
import org.example.server.models.Stacks;
import org.example.server.models.enums.AbilityType;
import org.example.server.models.enums.Seasons.Season;
import org.example.server.models.enums.StackLevel;
import org.example.server.models.enums.items.FishType;
import org.example.server.models.enums.items.ToolType;
import org.example.server.models.tools.Tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import static java.lang.Math.min;

public class MiniGameController {

    private final ToolType fishingPole;
    private final FishType caughtFish;
    private final int numberOfFish;
    private StackLevel fishLevel;
    private final FishMovementType caughtFishMovement;
    private Integer previousDirection;
    private final MiniGameDifficulty difficulty;
    private float progress;
    private boolean isPerfect;

    public MiniGameController(ToolType fishingPole) {
        this.fishingPole = fishingPole;
        caughtFish = getFishType();
        numberOfFish = min(6, getNumberOfFish());
        fishLevel = getStackLevel(getFishCoefficient(fishingPole));
        caughtFishMovement = FishMovementType.values()[new Random().nextInt(FishMovementType.values().length)];
        System.out.println(caughtFishMovement.name());
        if (caughtFishMovement == FishMovementType.SMOOTH) {
            previousDirection = new Random().nextInt(-1, 3);
        } else {
            previousDirection = null;
        }
        difficulty = MiniGameDifficulty.RETARD;
        progress = 50;
        isPerfect = true;
        System.out.println(caughtFish.getName());
    }

    public void startMiniGame() {

        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MiniGameView(this));

    }

    public void finishGame(boolean caughtSuccessfully) {

        checkFishingProcessStatus();

        Stacks fishStack = new Stacks(caughtFish, fishLevel, numberOfFish);

        if (caughtSuccessfully) {

            if (ClientApp.getCurrentGame().getCurrentPlayer().getBackpack().canAdd(fishStack.getItem(), fishStack.getStackLevel(), fishStack.getQuantity())) {
                ClientApp.getCurrentGame().getCurrentPlayer().getBackpack().addItems(fishStack.getItem(), fishStack.getStackLevel(), fishStack.getQuantity());
            }

        }


        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new PostMiniGameMenuView(this, (caughtSuccessfully) ? caughtFish : null, isPerfect));

    }

    public void backToOutside() {

        ClientApp.setNonMainMenu(null);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new OutsideView());

    }

    public FishType getFishType() {

        Season currentSeason = ClientApp.getCurrentGame().getTime().getSeason();

        ArrayList<FishType> availableFish;

        if (fishingPole == ToolType.TrainingRod)

            availableFish = new ArrayList<>(List.of(FishType.getCheapestOfSeason().get(currentSeason)));

        else {

            availableFish = FishType.getAvailableFish(currentSeason);

        }

        if (ClientApp.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Fishing).getLevel() == 4) {

            availableFish.addAll(FishType.getAvailableLegendaryFish(currentSeason));

        }

        return availableFish.get(new Random().nextInt(availableFish.size()));

    }

    public FishType getCaughtFish() {
        return caughtFish;
    }

    private int getNumberOfFish() {
        Random random = new Random();
        return (int) Math.ceil(
                ClientApp.getCurrentGame().getCurrentWeather().getFishingModifier() *
                        random.nextDouble() *
                        (ClientApp.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Fishing).getLevel() + 2)
        );
    }

    private double getFishCoefficient(ToolType type) {
        Random random = new Random();
        return (ToolType.getFishPoleModifier(type) *
                (ClientApp.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Fishing).getLevel() + 2) *
                random.nextDouble()) / (7.0 - ClientApp.getCurrentGame().getCurrentWeather().getFishingModifier());
    }

    private StackLevel getStackLevel(double coefficient) {
        if (0 < coefficient && coefficient <= 0.5)
            return StackLevel.Basic;
        if (0.5 < coefficient && coefficient <= 0.7)
            return StackLevel.Silver;
        if (0.7 < coefficient && coefficient <= 0.9)
            return StackLevel.Gold;
        else
            return StackLevel.Iridium;
    }

    private int randomDirection() {

        if (previousDirection == null) {
            return new Random().nextInt(-1, 3);
        } else {       // 66% shans movement ghabli
            ArrayList<Integer> candidateDirections = new ArrayList<>(List.of(-1, 0, 1,
                    previousDirection, previousDirection, previousDirection));
            previousDirection = candidateDirections.get(new Random().nextInt(candidateDirections.size()));
            return previousDirection;
        }


    }

    public float getFishMovement(float currentY, float minY, float maxY) {


        int direction = randomDirection();
        int totalDY = (caughtFishMovement == FishMovementType.DART) ? difficulty.getDartDY() : difficulty.getNormalDY();
        int totalFrames = (caughtFishMovement == FishMovementType.SINKER) ? 15 : 30;

        if (caughtFishMovement == FishMovementType.FLOATER) {
            if (direction > 0) {
                totalDY = difficulty.getDartDY();
            }
        }

//        while ( ((totalDY * direction + currentY) > maxY) || ((totalDY * direction + currentY) < minY)){
//            direction = randomDirection();
//        }
        if ((totalDY * direction + currentY) > maxY) {
//            return (float) (maxY - currentY) / totalFrames;
            direction *= -1;
        } else if ((totalDY * direction + currentY) < minY) {
//            return (float) (currentY - minY) / totalFrames;
            direction *= -1;
        }


        return (float) (totalDY * direction) / totalFrames;


    }

    public void checkIfFishIsIn(float rectangleBottom, float fishBottom, float rectangleHeight, float fishHeight) {


        if (rectangleBottom < fishBottom && (rectangleBottom + rectangleHeight) > (fishBottom + fishHeight)) {
            progress = Math.min(progress + 0.1f, 100);
            if (progress >= 100) {
                finishGame(true);
            }
        } else {
            progress = Math.max(progress - 0.1f, 0);
            isPerfect = false;
            if (progress <= 0) {
                finishGame(false);
            }
        }

    }

    public void checkFishingProcessStatus() {

        if (fishLevel == StackLevel.Silver) {
            fishLevel = StackLevel.Gold;
        } else if (fishLevel == StackLevel.Gold) {
            fishLevel = StackLevel.Iridium;
        }

        ClientApp.getCurrentGame().getCurrentPlayer().fishXp(5);
        ClientApp.getCurrentGame().getCurrentPlayer().consumeEnergy(

                (ClientApp.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Fishing).getLevel() == 4) ?
                        ToolType.getFishPoleEnergy(fishingPole) - 1 : ToolType.getFishPoleEnergy(fishingPole)

        );

    }

    public FishMovementType getCaughtFishMovement() {
        return caughtFishMovement;
    }

    public float getProgress() {
        return progress;
    }

    public int getNumberOfCaughtFish() {
        return numberOfFish;
    }

}
