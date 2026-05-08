//Concrete Factories
public class EconomyCarFactory implements CarFactory{
    private final String brand;

    public EconomyCarFactory(String brand) {
        this.brand = brand;
    }

    @Override
    public CarInterior createCarInterior() {
        return new EconomyCarInterior();
    }

    @Override
    public CarExterior createCarExterior() {
        return new EconomyCarExterior();
    }
}
