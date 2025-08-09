package org.example.client.view;

import org.example.client.controller.GameMenuController;
import org.example.server.controller.InteractionsWithOthers.InteractionsWithNPCController;
import org.example.server.controller.InteractionsWithOthers.InteractionsWithUserController;
import org.example.server.controller.InteractionsWithOthers.MarriageController;
import org.example.server.controller.ToolController;
import org.example.server.models.App;
import org.example.server.models.Result;
import org.example.server.models.enums.Menu;
import org.example.server.models.enums.commands.*;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameView extends AppMenu {
    private final GameMenuController controller;
    private final InteractionsWithNPCController interactionsWithNPCController;
    private final InteractionsWithUserController interactionsWithUserController;
    private final MarriageController marriageController;

    public GameView() {
        controller = new GameMenuController(this);
        interactionsWithNPCController = new InteractionsWithNPCController();
        interactionsWithUserController = new InteractionsWithUserController();
        marriageController = new MarriageController();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

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

    public GameMenuController getController() {
        return controller;
    }

    public void executeCommands(Scanner scanner) {
        if (controller.playerPassedOut()) {
            System.out.println(App.getCurrentGame().getCurrentPlayer().getUsername() + " has passed out!");
            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().nextTurn(scanner));
            return;
        }
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        } else if (MainMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
        } else if (MainMenuCommands.ExitMenu.getMatcher(input) != null) {
            System.out.println(controller.exitMenu());
        } else if (GameMenuCommands.NextTurn.getMatcher(input) != null) {
            System.out.println(controller.nextTurn(scanner));
        } else if (GameMenuCommands.TerminateGame.getMatcher(input) != null) {
            System.out.println(controller.terminateGame(scanner));
        } else if (GameMenuCommands.ShowWeather.getMatcher(input) != null) {
            System.out.println(controller.showWeather());
        } else if (GameMenuCommands.ForecastWeather.getMatcher(input) != null) {
            System.out.println(controller.forecastWeather());
        } else if ((matcher = CheatCommands.CheatSetWeather.getMatcher(input)) != null) {
            System.out.println(controller.cheatSetWeather(matcher.group("weatherName").trim()));
        } else if ((matcher = CheatCommands.CheatThor.getMatcher(input)) != null) {
            System.out.println(controller.cheatThor(matcher.group("i"), matcher.group("j")));
        } else if ((matcher = GameMenuCommands.Walk.getMatcher(input)) != null) {
            System.out.println(controller.walk(matcher.group("i"), matcher.group("j"), scanner));
        } else if ((matcher = GameMenuCommands.PrintMap.getMatcher(input)) != null) {
            System.out.println(controller.printMap(matcher.group("i"), matcher.group("j"),
                    matcher.group("size")));
        } else if (GameMenuCommands.HelpReadingMap.getMatcher(input) != null) {
            System.out.println(controller.helpReadingMap());
        } else if (GameMenuCommands.ShowEnergy.getMatcher(input) != null) {
            System.out.println(controller.showEnergy());
        } else if (GameMenuCommands.Time.getMatcher(input) != null) {
            System.out.println(controller.showTime());
        } else if (GameMenuCommands.Date.getMatcher(input) != null) {
            System.out.println(controller.showDate());
        } else if (GameMenuCommands.DateTime.getMatcher(input) != null) {
            System.out.println(controller.showDateTime());
        } else if (GameMenuCommands.DayOfWeek.getMatcher(input) != null) {
            System.out.println(controller.showDayOfWeek());
        } else if (GameMenuCommands.Season.getMatcher(input) != null) {
            System.out.println(controller.showSeason());
        } else if ((matcher = CheatCommands.CheatSetEnergy.getMatcher(input)) != null) {
            System.out.println(controller.cheatSetEnergy(matcher.group("value")));
        } else if (CheatCommands.CheatEnergyUnlimited.getMatcher(input) != null) {
            System.out.println(controller.cheatEnergyUnlimited());
        } else if ((matcher = GameMenuCommands.PlaceItem.getMatcher(input)) != null) {
            System.out.println(controller.placeItem(
                    matcher.group("itemName").trim(),
                    Integer.parseInt(matcher.group("direction").trim())
            ));
        } else if ((matcher = GameMenuCommands.EatFood.getMatcher(input)) != null) {
            System.out.println(controller.eatFood(
                    matcher.group("itemName").trim()
            ));
        } else if ((matcher = CheatCommands.CheatAdvanceTime.getMatcher(input)) != null) {
            System.out.println(controller.cheatAdvanceTime(matcher.group("timeString")));
        } else if ((matcher = CheatCommands.CheatAdvanceDate.getMatcher(input)) != null) {
            System.out.println(controller.cheatAdvanceDate(matcher.group("dayString")));
        } else if ((matcher = CheatCommands.CheatAddItem.getMatcher(input)) != null) {
            System.out.println(controller.cheatAddItem(
                    matcher.group("itemName").trim(),
                    Integer.parseInt(matcher.group("count"))
            ));
        } else if ((matcher = CheatCommands.CheatSetFriendShip.getMatcher(input)) != null) {
            System.out.println(controller.cheatSetFriendship(matcher.group("name"), matcher.group("amount")));
        } else if ((matcher = CheatCommands.CheatAddMoney.getMatcher(input)) != null) {
            System.out.println(controller.cheatAddMoney(matcher.group("amount")));
        } else if ((matcher = CheatCommands.CheatSetAbility.getMatcher(input)) != null) {
            System.out.println(controller.cheatSetAbility(
                    matcher.group("abilityName").trim(),
                    Integer.parseInt(matcher.group("level").trim())
            ));
        } else if ((matcher = GameMenuCommands.CropInfo.getMatcher(input)) != null) {
            System.out.println(controller.cropInfo(matcher.group("cropName")));
        } else if ((matcher = GameMenuCommands.Fishing.getMatcher(input)) != null) {
            System.out.println(controller.fishing(
                    matcher.group("fishPole").trim()
            ));
        } else if (GameMenuCommands.BuildGreenHouse.getMatcher(input) != null) {
            System.out.println(controller.buildGreenHouse());
        } else if ((matcher = GameMenuCommands.Plant.getMatcher(input)) != null) {
            //System.out.println(controller.plant(matcher.group("seedName"), matcher.group("direction")));
        } else if ((matcher = GameMenuCommands.Fertilize.getMatcher(input)) != null) {
            System.out.println(controller.fertilize(matcher.group("itemName"), matcher.group("direction")));

        } else if ((matcher = GameMenuCommands.ShowPlant.getMatcher(input)) != null) {
            System.out.println(controller.showPlant(matcher.group("i"), matcher.group("j")));
        } else if ((matcher = GameMenuCommands.Pet.getMatcher(input)) != null) {
            System.out.println(controller.pet(matcher.group("name")));
        } else if (GameMenuCommands.ShowAnimals.getMatcher(input) != null) {
            System.out.println(controller.showAnimals());
        } else if ((matcher = GameMenuCommands.Shepherd.getMatcher(input)) != null) {
            System.out.println(controller.shepherd(matcher.group("name"),
                    matcher.group("i"), matcher.group("j")));
        } else if (GameMenuCommands.Products.getMatcher(input) != null) {
            System.out.println(controller.products());
        } else if ((matcher = GameMenuCommands.SellAnimal.getMatcher(input)) != null) {
            System.out.println(controller.sellAnimal(matcher.group("name")));
        } else if ((matcher = GameMenuCommands.CollectProducts.getMatcher(input)) != null) {
            System.out.println(controller.collectProduct(matcher.group("name")));
        } else if ((matcher = GameMenuCommands.FeedHay.getMatcher(input)) != null) {
            System.out.println(controller.feedHay(matcher.group("name")));
        } else if ((matcher = GameMenuCommands.UseArtisan.getMatcher(input)) != null) {
            System.out.println(controller.useArtisan(
                    matcher.group("artisanName").trim(),
                    matcher.group("itemList").trim()
            ));
        } else if ((matcher = GameMenuCommands.SellItem.getMatcher(input)) != null) {
            System.out.println(controller.sellItem(matcher.group("name"), matcher.group("amount")));
        } else if ((matcher = GameMenuCommands.GetArtisan.getMatcher(input)) != null) {
            System.out.println(controller.getArtisanProduct(
                    matcher.group("artisanName")
            ));
        } else if (GameMenuCommands.InventoryShow.getMatcher(input) != null) {
            System.out.println(controller.inventoryShow());
        } else if ((matcher = GameMenuCommands.InventoryTrash.getMatcher(input)) != null) {
            System.out.println(controller.inventoryTrash(
                    matcher.group("itemName").trim(),
                    Integer.parseInt(matcher.group("number").trim())
            ));
//        }
        // Interaction With NPCs
//        else if ((matcher = InteractionsWithNPCCommands.MeetNPC.getMatcher(input)) != null) {
//            System.out.println(interactionsWithNPCController.meetNPC(
//                    matcher.group("npcName").trim()
//            ));
//        } else if ((matcher = InteractionsWithNPCCommands.GiftNPC.getMatcher(input)) != null) {
//            System.out.println(interactionsWithNPCController.giftNPC(
//                    matcher.group("npcName").trim(),
//                    matcher.group("item").trim()
//            ));
//        } else if ((matcher = InteractionsWithNPCCommands.FriendshipNPCList.getMatcher(input)) != null) {
//            System.out.println(interactionsWithNPCController.friendshipNPC());
//        } else if ((matcher = InteractionsWithNPCCommands.QuestList.getMatcher(input)) != null) {
//            System.out.println(interactionsWithNPCController.questList());
//        } else if ((matcher = InteractionsWithNPCCommands.QuestFinish.getMatcher(input)) != null) {
//            System.out.println(interactionsWithNPCController.questsFinish(
//                    matcher.group("npcName").trim(),
//                    matcher.group("index").trim()
//            ));
        } else if ((matcher = InteractionsWithUserCommands.Friendships.getMatcher(input)) != null) {
            System.out.println(interactionsWithUserController.friendship());
        } else if ((matcher = InteractionsWithUserCommands.TalkToUser.getMatcher(input)) != null) {
            System.out.println(interactionsWithUserController.talk(
                    matcher.group("username").trim(),
                    matcher.group("message").trim()
            ));
        } else if ((matcher = InteractionsWithUserCommands.TalkHistory.getMatcher(input)) != null) {
            System.out.println(interactionsWithUserController.talkHistory(
                    matcher.group("username").trim()
            ));
        } else if ((matcher = InteractionsWithUserCommands.Gift.getMatcher(input)) != null) {
            System.out.println(interactionsWithUserController.gift(
                    matcher.group("username").trim(),
                    matcher.group("item").trim(),
                    matcher.group("amount").trim()
            ));
        } else if ((matcher = InteractionsWithUserCommands.GiftList.getMatcher(input)) != null) {
            System.out.println(interactionsWithUserController.giftList());
        } else if ((matcher = InteractionsWithUserCommands.GiftRate.getMatcher(input)) != null) {
            System.out.println(interactionsWithUserController.giftRate(
                    matcher.group("giftNumber").trim(),
                    matcher.group("rate").trim()
            ));
        } else if ((matcher = InteractionsWithUserCommands.GiftHistory.getMatcher(input)) != null) {
            System.out.println(interactionsWithUserController.giftHistory(
                    matcher.group("username").trim()
            ));
        } else if ((matcher = InteractionsWithUserCommands.Hug.getMatcher(input)) != null) {
            System.out.println(interactionsWithUserController.hug(
                    matcher.group("username").trim()
            ));
        } else if ((matcher = InteractionsWithUserCommands.Flower.getMatcher(input)) != null) {
            System.out.println(interactionsWithUserController.flower(
                    matcher.group("username").trim()
            ));
//        } else if ((matcher = InteractionsWithUserCommands.AskMarriage.getMatcher(input)) != null) {
//            System.out.println(marriageController.askMarriage(
//                    matcher.group("username").trim()
//            ));
//        } else if ((matcher = InteractionsWithUserCommands.Respond.getMatcher(input)) != null) {
//            System.out.println(marriageController.respond(
//                    matcher.group("respond").trim(),
//                    matcher.group("username").trim()
//            ));
        } else if ((matcher = GameMenuCommands.ShowMoney.getMatcher(input)) != null) {
            System.out.println(controller.showMoney());
        } else if ((matcher = CheatCommands.CheatAddLevelNPC.getMatcher(input)) != null) {
            System.out.println(interactionsWithNPCController.cheatAddLevel(
                    matcher.group("name").trim(),
                    matcher.group("level").trim()
            ));
        } else if ((matcher = InteractionsWithUserCommands.ShowCouple.getMatcher(input)) != null) {
            System.out.println(marriageController.showCouple());
        } else if ((matcher = CheatCommands.CheatAddLevelPlayer.getMatcher(input)) != null) {
            System.out.println(interactionsWithUserController.cheatAddPlayerLevel(
                    matcher.group("name").trim(),
                    matcher.group("level").trim()
            ));
        } else if ((matcher = InteractionsWithUserCommands.StartTrade.getMatcher(input)) != null) {
            System.out.println(controller.startTrade());
        } else if ((matcher = ToolCommands.ToolsEquip.getMatcher(input)) != null) {
            System.out.println(new ToolController().equipTool(matcher.group("toolName").trim()));
        } else if (ToolCommands.ToolsShowCurrent.getMatcher(input) != null) {
            System.out.println(new ToolController().showCurrentTool());
        } else if (ToolCommands.ToolsShowAvailable.getMatcher(input) != null) {
            System.out.println(new ToolController().showAvailableTools());
        } else if ((matcher = ToolCommands.ToolsUse.getMatcher(input)) != null) {
            System.out.println(new ToolController().ToolsUse(matcher.group("direction")));
        } else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }

    public Result askToVote(Scanner scanner) {
        String input = scanner.nextLine().trim();
        if (!input.equals("accept") && !input.equals("reject")) {
            System.out.println("Invalid response!");
            return new Result(false, "invalid command!");
        }
        System.out.println("You have voted successfully!");
        return new Result(true, input);
    }
}
