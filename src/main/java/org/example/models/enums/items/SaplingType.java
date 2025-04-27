package org.example.models.enums.items;

public enum SaplingType implements Item {
    AppleSapling,
    ApricotSapling,
    CherrySapling,
    OrangeSapling,
    PeachSapling,
    PomegranateSapling,
    AcornSapling,
    MapleSapling,
    PineCone,
    MahoganySapling,
    MushroomTreeSapling;

    @Override
    public int getPrice() {
        return 0;
    }
}
