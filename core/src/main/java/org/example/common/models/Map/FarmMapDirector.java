package org.example.common.models.Map;

import org.example.common.models.Game;

public class FarmMapDirector {
    public void buildMapWithoutForaging(FarmMapBuilder builder, int index, Game game) {
        builder.setDimensions(55, 75);
        builder.createHut();
        builder.createGreenHouse();
        builder.createStoneQuarry();
        if (index == 0) {
            builder.createSmallLake(30, 64);
            builder.createBigLake(45, 30);
            builder.setPassageToNpcValley(54, 74, 6, 1, game.getNpcMap());
            game.getNpcMap().setPassageToFarmMap(6, 0, builder.getFinalProduct(), 54, 73);
        } else if (index == 1) {
            builder.createBigLake(25, 7);
            builder.setPassageToNpcValley(54, 0, 6, 32, game.getNpcMap());
            game.getNpcMap().setPassageToFarmMap(6, 33, builder.getFinalProduct(), 54, 1);

        } else if (index == 2) {
            builder.createSmallLake(47, 8);
            builder.createSmallLake(32, 40);
            builder.createSmallLake(21, 21);
            builder.setPassageToNpcValley(0, 74, 15, 1, game.getNpcMap());
            game.getNpcMap().setPassageToFarmMap(15, 0, builder.getFinalProduct(), 0, 73);
        } else {
            builder.createSmallLake(45, 30);
            builder.createBigLake(35, 4);
            builder.setPassageToNpcValley(0, 0, 15, 32, game.getNpcMap());
            game.getNpcMap().setPassageToFarmMap(15, 33, builder.getFinalProduct(), 0, 1);
        }
    }

    public void buildMap(FarmMapBuilder builder, int index, Game game) {
        buildMapWithoutForaging(builder, index, game);
        builder.generateForagingItems(game.getTime().getSeason());
    }

}
