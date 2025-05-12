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

    public ProcessedProduct(ProcessedProductType type) {
        this.type = type;
    }

    public ProcessedProductType getType() {
        return type;
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    public int getEnergy() {
        return energy;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
