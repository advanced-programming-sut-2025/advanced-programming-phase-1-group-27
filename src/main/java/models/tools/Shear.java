package models.tools;

public class Shear extends Tool {
    private int price;

    public Shear() {
        int level = 0;
        this.price = 1000;
        int energyUsage = 4;
        super(level, energyUsage , "Shear");
    }

    @Override
    public void use() {

    }
}
