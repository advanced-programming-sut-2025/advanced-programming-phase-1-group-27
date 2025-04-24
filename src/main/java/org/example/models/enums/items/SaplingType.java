package org.example.models.enums.items;

public enum SaplingType implements Item {
    AppleSapling,
    ApricotSapling,
    CherrySapling,
    OrangeSapling,
    PeachSapling,
    PomegranateSapling;

    @Override
    public int getPrice() {
        return 0;
    }
}
