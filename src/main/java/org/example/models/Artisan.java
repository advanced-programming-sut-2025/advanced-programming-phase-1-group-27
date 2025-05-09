package org.example.models;

import org.example.models.enums.ArtisanTypes;
import org.example.models.enums.items.products.ProcessedProductType;

public class Artisan {
    private ArtisanTypes type;
    private ProcessedProductType finalProduct;
    private int timeLeft;

    public Artisan(ArtisanTypes type) {
        this.type = type;
        this.finalProduct = null;
    }
}
