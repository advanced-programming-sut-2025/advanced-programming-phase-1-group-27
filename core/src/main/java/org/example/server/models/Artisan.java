package org.example.server.models;

import com.google.gson.internal.LinkedTreeMap;
import org.example.server.models.enums.AbilityType;
import org.example.server.models.enums.ArtisanTypes;
import org.example.server.models.enums.Plants.FruitType;
import org.example.server.models.enums.items.FishType;
import org.example.server.models.enums.items.products.ProcessedProductType;

import java.util.HashMap;

public class Artisan {
    private final ArtisanTypes type;
    private ProcessedProduct finalProduct = null;
    private int timeLeft = 0;

    public Artisan(ArtisanTypes type) {
        this.type = type;
    }

    public Artisan(LinkedTreeMap<String, Object> info) {
        this.type = ArtisanTypes.getArtisan((String) info.get("type"));
        this.finalProduct = new ProcessedProduct((LinkedTreeMap<String, Object>) info.get("processedProduct"));
        this.timeLeft = ((Number) info.get("timeLeft")).intValue();
    }

    public HashMap<String, Object> getInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("type", type.name());
        info.put("processedProduct", finalProduct.getInfo());
        info.put("timeLeft", timeLeft);
        return info;
    }

    public ArtisanTypes getType() {
        return type;
    }

    public ProcessedProduct getFinalProduct() {
        return finalProduct;
    }

    public void setFinalProduct(ProcessedProductType finalProduct) {
        if (finalProduct.getPrice() != null)
            this.finalProduct = new ProcessedProduct(finalProduct, finalProduct.getPrice(), finalProduct.getEnergy());
        else
            this.finalProduct = new ProcessedProduct(finalProduct);
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public void setFinalProduct(ProcessedProductType finalProductType, String ingredientName) {
        ProcessedProduct processedProduct = new ProcessedProduct(finalProductType);
        if (finalProductType.getPrice() != null) {
            processedProduct.setPrice(finalProductType.getPrice());
            processedProduct.setEnergy(finalProductType.getEnergy());
            return;
        }
        FruitType fruit = FruitType.getItem(ingredientName);
        FishType fish = FishType.getItem(ingredientName);
        if (finalProductType == ProcessedProductType.Juice) {
            assert fruit != null;
            processedProduct.setPrice((int) (2.25 * fruit.getPrice()));
            processedProduct.setEnergy(2 * fruit.getEnergy());
        } else if (finalProductType == ProcessedProductType.Wine) {
            assert fruit != null;
            processedProduct.setPrice(3 * fruit.getPrice());
            processedProduct.setEnergy((int) (1.75 * fruit.getEnergy()));
        } else if (finalProductType == ProcessedProductType.DriedMushroom) {
            assert fruit != null;
            processedProduct.setPrice((int) (7.5 * fruit.getPrice()) + 25);
            processedProduct.setEnergy(50);
        } else if (finalProductType == ProcessedProductType.DriedFruit) {
            assert fruit != null;
            processedProduct.setPrice((int) (7.5 * fruit.getPrice()) + 25);
            processedProduct.setEnergy(75);
        } else if (finalProductType == ProcessedProductType.Pickle) {
            assert fruit != null;
            processedProduct.setPrice(2 * fruit.getPrice() + 50);
            processedProduct.setEnergy((int) (1.75 * fruit.getEnergy()));
        } else if (finalProductType == ProcessedProductType.Jelly) {
            assert fruit != null;
            processedProduct.setPrice(2 * fruit.getPrice() + 50);
            processedProduct.setEnergy(2 * fruit.getEnergy());
        } else if (finalProductType == ProcessedProductType.SmokedFish) {
            assert fish != null;
            processedProduct.setPrice(2 * fish.getPrice());
            processedProduct.setEnergy((int) (1.5 * fish.getEnergy()));
        }
        finalProduct = processedProduct;
    }

    public void free() {
        finalProduct = null;
        timeLeft = 0;
    }

    public void passHour() {
        if (finalProduct != null) {
            timeLeft = Math.max(0, timeLeft - 1);
        }
    }
}
