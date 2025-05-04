package org.example.models.Map;

public class FarmMapDirector {

    public void BuildMap(FarmMapBuilder builder, int index) {
        builder.setDimensions(55, 75);
        builder.createHut();
        builder.createGreenHouse();
        if (index == 1) {
            builder.createSmallLake(30, 64);
            builder.createBigLake(45, 30);
        } else if (index == 2) {
            builder.createBigLake(25, 7);
        } else if (index == 3) {
            builder.createSmallLake(47, 8);
            builder.createSmallLake(32, 40);
            builder.createSmallLake(21, 21);
        } else {
            builder.createSmallLake(45, 30);
            builder.createBigLake(35, 4);
        }
        builder.generateForagingItems();
    }

}
