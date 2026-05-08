//Concrete Factories
public class LuxuryCarFactory implements CarFactory{
    private final String brand;

    public LuxuryCarFactory(String brand) {
        this.brand = brand;
    }

    @Override
    public CarInterior createCarInterior() {
        return new LuxuryCarInterior();
    }

    @Override
    public CarExterior createCarExterior() {
        return new LuxuryCarExterior();
    }
}
