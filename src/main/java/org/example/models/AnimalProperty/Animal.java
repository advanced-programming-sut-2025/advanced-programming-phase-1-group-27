package org.example.models.AnimalProperty;

import org.example.models.Stacks;
import org.example.models.enums.StackLevel;
import org.example.models.enums.items.AnimalType;
import org.example.models.Position;
import org.example.models.enums.items.products.AnimalProduct;

import java.util.Random;
import java.util.random.RandomGenerator;

public class Animal {
    private AnimalType type; // Animal price is obtained using this field
    private String name;
    private Position position;
    private int friendship = 0, tillNextProduction;
    private boolean wasFeed = false, wasPet = false, isOut = false;

    public Animal(AnimalType type, String name) {
        this.type = type;
        this.name = name;
        tillNextProduction = type.getYieldRate();
    }

    public void passADay() {
        tillNextProduction = Integer.max(tillNextProduction - 1, 0);
    }

    public Stacks getProduct() {
        Random generator = new Random(System.currentTimeMillis());
        AnimalProduct product;

        if (type.getProducts().size() > 1 && friendship >= 100) {
            int randInt = generator.nextInt(1500);
            if (randInt < (generator.nextInt(3) + 1) * 75 + friendship) {
                product = type.getProducts().get(1);
            }
            else {
                product = type.getProducts().get(0);
            }
        }
        else {
            product = type.getProducts().get(0);
        }
        double d = (double) friendship / 1000.0;
        d *= (0.5 + 0.5 * RandomGenerator.getDefault().nextDouble());
        if (d <= 0.5) {
            return new Stacks(product, StackLevel.Basic, 1);
        } else if (d <= 0.7) {
            return new Stacks(product, StackLevel.Silver, 1);
        } else if (d <= 0.9) {
            return new Stacks(product, StackLevel.Gold, 1);
        } else {
            return new Stacks(product, StackLevel.Iridium, 1);
        }
    }

    public void cheatSetFriendShip(int friendship) {
        this.friendship = friendship;
    }

    public void addFriendShip(int friendship) {
        this.friendship = Integer.max(0, Integer.min(this.friendship + friendship, 1000));
    }

    public void shepard() {
        isOut = !isOut;
    }

    public String showDetails() {
        return "Animal Name: " + name + "\n" +
                (wasFeed? "Was Feed Today": "Wasn't Feed Today!!") + "\n" +
                (wasPet? "Was Pet Today": "Wasn't Pet Today!!") + "\n";
    }

}
