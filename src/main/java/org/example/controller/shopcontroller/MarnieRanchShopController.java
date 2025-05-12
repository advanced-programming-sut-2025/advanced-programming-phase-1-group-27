package org.example.controller.shopcontroller;

import org.example.controller.MenuController;
import org.example.models.AnimalProperty.Animal;
import org.example.models.AnimalProperty.Barn;
import org.example.models.AnimalProperty.Coop;
import org.example.models.App;
import org.example.models.Player;
import org.example.models.Result;
import org.example.models.enums.items.AnimalType;
import org.example.view.shopview.MarnieRanch;

public class MarnieRanchShopController extends MenuController {
    private final MarnieRanch view;

    public MarnieRanchShopController(MarnieRanch view) {
        this.view = view;
    }

    public boolean playerPassedOut() {
        return App.getCurrentGame().getCurrentPlayer().hasPassedOut();
    }

    public Result buyAnimal(String animalString, String name) {
        AnimalType type = AnimalType.getItem(animalString);
        Animal animal = new Animal(type, name);
        Player player = App.getCurrentGame().getCurrentPlayer();

        if (player.getMoney() >= type.getPrice()) {
            for (Barn barn: player.getFarmMap().getBarns()) {
                if (type.getAppropriateFarmType().contains(barn.getType()) &&
                        barn.getType().getCapacity() > barn.getAnimals().size()) {
                    barn.addAnimal(animal);
                    player.getFarmMap().addAnimal(animal);
                    return new Result(true, "You have bought a " + animalString +
                            " and it is in the " + barn.getType().getName());
                }
            }
            for (Coop coop: player.getFarmMap().getCoops()) {
                if (type.getAppropriateFarmType().contains(coop.getType()) &&
                        coop.getType().getCapacity() > coop.getAnimals().size()) {
                    coop.addAnimal(animal);
                    player.getFarmMap().addAnimal(animal);
                    return new Result(true, "You have bought a " + animalString +
                            " and it is in the " + coop.getType().getName());
                }
            }
            return new Result(false, "There is No Space For This Animal in Your Farm!");
        } else {
            return new Result(false, "You dont Have Enough Money! You Have " + player.getMoney() +
                    " while you need " + type.getPrice());
        }
    }

    @Override
    public Result enterMenu(String menuName) {
        return new Result(false, "You can't enter any menu from here!");
    }

    @Override
    public Result exitMenu() {
        // TODO: rassa
        return null;
    }
}
