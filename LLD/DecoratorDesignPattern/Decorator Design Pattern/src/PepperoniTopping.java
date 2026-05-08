public class PepperoniTopping extends ToppingDecorator{
    public PepperoniTopping(BasePizza basePizza) {
        super(basePizza);
    }

    @Override
    public String getDescription() {
        return basePizza.getDescription() + " "+ "PepperoniTopping";
    }

    @Override
    public double getCost() {
        return basePizza.getCost()+50;
    }
}
