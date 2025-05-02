package org.example.models.enums.items;

import org.example.models.Item;

public enum SaplingType implements Item {
    ApricotSapling(),
    CherrySapling(),
    BananaSapling(),
    MangoSapling(),
    OrangeSapling,
    PeachSapling,
    AppleSapling(),
    PomegranateSapling,
    Acorn,
    MapleSapling,
    PineCone,
    MahoganySapling,
    MushroomTreeSapling,
    MysticTreeSapling;

    @Override
    public Integer getPrice() {
        return 0;
    }
}
