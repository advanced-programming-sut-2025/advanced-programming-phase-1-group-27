package org.example.models;

import org.example.models.enums.items.products.ProcessedProductType;

import java.util.Objects;

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

    public void setPrice(int price) {
        this.price = price;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public boolean isEdible() {
        return type != ProcessedProductType.IronMetalBar &&
                type != ProcessedProductType.CopperMetalBar &&
                type != ProcessedProductType.GoldMetalBar &&
                type != ProcessedProductType.IridiumMetalBar;
    }

    @Override
    public String toString() {
        return type.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProcessedProduct that = (ProcessedProduct) o;
        return price == that.price && energy == that.energy && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, price, energy);
    }
}
