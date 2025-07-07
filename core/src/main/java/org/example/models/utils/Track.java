package org.example.models.utils;

public enum Track {
   THEME_1("Theme 1", "Sounds/music/theme1.mp3"),
   THEME_2("Theme 2", "Sounds/music/theme2.mp3");

   private final String name;
   private final String address;

   Track(String name, String address) {
       this.name = name;
       this.address = address;
   }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
