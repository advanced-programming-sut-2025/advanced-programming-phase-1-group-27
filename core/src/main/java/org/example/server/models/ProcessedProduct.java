package org.example.server.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.google.gson.internal.LinkedTreeMap;
import org.example.server.models.enums.items.products.ProcessedProductType;

import java.util.HashMap;
import java.util.Objects;

public class ProcessedProduct implements Item, Edible { // for all processed products
    private ProcessedProductType type;
    private int price;
    private int energy;

    public ProcessedProduct(ProcessedProductType type, int price, int energy) {
        this.type = type;
        this.price = price;
        this.energy = energy;
    }

    public ProcessedProduct(LinkedTreeMap<String, Object> info) {
        this.type = ProcessedProductType.getItem((String) info.get("type"));
        this.price = ((Number) info.get("price")).intValue();
        this.energy = ((Number) info.get("energy")).intValue();
    }

    public HashMap<String, Object> getInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("type", type.name());
        info.put("price", price);
        info.put("energy", energy);
        return info;
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

    @Override
    public boolean isEdible() {
        return type.isEdible();
    }

    @Override
    public Texture getTexture() {
        return type.getTexture();
    }

    @Override
    public Image getItemImage() {
        return type.getItemImage();
    }

    @Override
    public String getName() {
        return type.getName();
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
