package org.example.models;

import org.example.models.enums.items.products.ProcessedProductType;

public class ProcessedProduct implements Item { // for all processed products
    private ProcessedProductType type;
    private int price;
    private int energy;

    public ProcessedProduct(ProcessedProductType type, int price, int energy) {
        this.type = type;
        this.price = price;
        this.energy = energy;
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
