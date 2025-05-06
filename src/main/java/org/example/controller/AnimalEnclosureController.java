package org.example.controller;

import org.example.models.App;
import org.example.models.Result;
import org.example.view.AnimalEnclosureView;

public class AnimalEnclosureController {
    private final AnimalEnclosureView view;

    public AnimalEnclosureController(AnimalEnclosureView view) {
        this.view = view;
    }

    public Result petAnimal(String name) {
        // TODO: function incomplete
        return null;
    }

    public Result shepherdAnimal(String name, int x, int y) {
        // TODO: function incomplete
        return null;
    }

    public Result feed(String name) {
        // TODO: function incomplete
        return null;
    }

    public Result showProduces() {
        // TODO: function incomplete
        return null;
    }

    public Result collectProduces(String name) {
        // TODO: function incomplete
        return null;
    }

    public Result sellAnimal(String name) {
        // TODO: function incomplete
        return null;
    }

    public boolean playerPassedOut() {
        return App.getCurrentGame().getCurrentPlayer().hasPassedOut();
    }
}
