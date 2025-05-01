package org.example.models.Map;

public class FarmMapDirector {

    public void BuildMap1(FarmMapBuilder builder) {
        builder.setDimensions(55, 75);
        builder.createHut();
        builder.createGreenHouse();
        builder.createSmallLake(30, 64);
        builder.createBigLake(45, 30);
        builder.generateForagingItems();
    }

    public void BuildMap2(FarmMapBuilder builder) {
        builder.setDimensions(55, 75);
        builder.createHut();
        builder.createGreenHouse();
        builder.createBigLake(25, 7);
        builder.generateForagingItems();
    }

    public void BuildMap3(FarmMapBuilder builder) {
        builder.setDimensions(55, 75);
        builder.createHut();
        builder.createGreenHouse();
        builder.createSmallLake(47, 8);
        builder.createSmallLake(32, 40);
        builder.createSmallLake(21, 21);
        builder.generateForagingItems();
    }

    public void buildMap4(FarmMapBuilder builder) {
        builder.setDimensions(55, 75);
        builder.createHut();
        builder.createGreenHouse();
        builder.createSmallLake(45, 30);
        builder.createBigLake(35, 4);
        builder.generateForagingItems();
    }
}
