public class MushroomTopping extends ToppingDecorator{
    public MushroomTopping(BasePizza basePizza) {
        super(basePizza);
    }

    @Override
    public String getDescription() {
        return basePizza.getDescription() + " " +"MushroomTopping";
    }

    @Override
    public double getCost() {
        return basePizza.getCost()+40;
    }
}
