package models.tools;

public class MilkPail extends Tool{

    private int price;

    public MilkPail() {
        int level = 0;
        this.price = 1000;
        int energyUsage = 4;
        super(level, energyUsage , "Milk pail");
    }

    @Override
    public void use() {

    }
}
