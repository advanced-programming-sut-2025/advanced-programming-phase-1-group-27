package org.example.models.enums.items;

import org.example.models.Item;

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
