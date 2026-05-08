public class FarmHouse implements  BasePizza{
    @Override
    public String getDescription() {
        return "FarmHouse Pizza";
    }

    @Override
    public double getCost() {
        return 300.0;
    }
}
