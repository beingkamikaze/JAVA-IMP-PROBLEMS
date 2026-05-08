public class VeggiesTopping extends ToppingDecorator{
    public VeggiesTopping(BasePizza basePizza) {
        super(basePizza);
    }

    @Override
    public String getDescription() {
        return basePizza.getDescription() + " " + "VeggiesTopping";
    }

    @Override
    public double getCost() {
        return basePizza.getCost()+30;
    }
}
