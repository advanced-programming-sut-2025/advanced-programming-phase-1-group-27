package org.example.models;

public interface Item {
    public abstract Integer getPrice();

    default String getName() {
        return this.toString().replaceAll("([A-Z])", " $1").trim();
    }
}
