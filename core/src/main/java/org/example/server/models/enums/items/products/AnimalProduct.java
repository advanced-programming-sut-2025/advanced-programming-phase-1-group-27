package org.example.server.models.enums.items.products;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.example.server.models.Item;

public enum AnimalProduct implements Item {
    Egg(50 , "Items/Animal_products/Egg.png"),
    LargeEgg(95 , "Items/Animal_products/Large_Egg.png"),
    DuckEgg(95 , "Items/Animal_products/Duck_Egg.png"),
    DuckFeather(250 , "Items/Animal_products/Duck_Feather.png"),
    Wool(340 , "Items/Animal_products/Wool.png"),
    RabbitLeg(565 , "Items/Animal_products/Rabbit_Leg.png"),
    DinosaurEgg(350 ,  "Items/Animal_products/Dinosaur_Egg.png"),
    CowMilk(125 ,  "Items/Animal_products/Milk.png"),
    LargeCowMilk(190 , "Items/Animal_products/Large_Milk.png"),
    GoatMilk(225 , "Items/Animal_products/Goat_Milk.png"),
    LargeGoatMilk(345 , "Items/Animal_products/Large_Goat_Milk.png"),
    Truffle(625 , "Items/Animal_products/Truffle.png"); // haram

    private final int price;
    private final Texture texture;

    AnimalProduct(int price , String address) {
        this.price = price;
        this.texture = new Texture(Gdx.files.internal(address));
    }

    public static AnimalProduct getItem(String itemName) {
        for (AnimalProduct animalProduct : AnimalProduct.values()) {
            if (animalProduct.getName().equalsIgnoreCase(itemName)) {
                return animalProduct;
            }
        }
        return null;
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    public boolean isFood() {
        return this != DuckFeather && this != Wool;
    }
}
