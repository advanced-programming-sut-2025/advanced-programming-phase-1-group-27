package org.example.server.models;

public interface Item {
    public abstract Integer getPrice();
//    public String getAddress();

    default String getAddress() {
        return null;
    }

    default String getName() {
//        return this.toString().replaceAll("([A-Z])", " $1").trim();
        return this.toString();
    }
}
