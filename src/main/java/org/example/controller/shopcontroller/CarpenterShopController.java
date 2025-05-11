package org.example.controller.shopcontroller;

import org.example.models.*;
import org.example.models.AnimalProperty.Barn;
import org.example.models.AnimalProperty.Coop;
import org.example.models.Map.FarmMap;
import org.example.models.enums.items.BuildingType;
import org.example.models.enums.items.Recipe;
import org.example.view.shopview.CarpenterShop;

public class CarpenterShopController {
    private final CarpenterShop view;

    public CarpenterShopController(CarpenterShop view) {
        this.view = view;
    }

    public boolean playerPassedOut() {
        return App.getCurrentGame().getCurrentPlayer().hasPassedOut();
    }

    public Result buildBuilding(String buildingName, int x, int y) {
        BuildingType type = BuildingType.getEnclosureType(buildingName);
        if (type == null)
            return new Result(false, "Enclosure type is invalid!");
        Player player = App.getCurrentGame().getCurrentPlayer();
        FarmMap farmMap = player.getFarmMap();
        Recipe recipe = Recipe.getRecipe(type);
        if (!player.hasEnoughIngredients(recipe))
            return new Result(false, "You don't have enough ingredients!");
        if (!farmMap.freeRectangle(x, y, type.getHeight(), type.getWidth()))
            return new Result(false, "There is no free space to place this animal enclosure!");
        player.useRecipe(recipe);
        if (type.isBarn())
            farmMap.placeBarn(x, y, new Barn(type, new Cell(new Position(x, y), farmMap))); // TODO: sobhan. okaye?
        else
            farmMap.placeCoop(x, y, new Coop(type, new Cell(new Position(x, y), farmMap)));
        return new Result(true, "Animal enclosure successfully placed!");
    }

}
