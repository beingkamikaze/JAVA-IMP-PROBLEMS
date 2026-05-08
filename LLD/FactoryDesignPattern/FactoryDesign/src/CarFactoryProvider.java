//Factory Provider

public class CarFactoryProvider {

    public CarFactory getFactory(String type,String brand){
        return switch (type) {
            case "ECONOMY" -> new EconomyCarFactory(brand);
            case "PREMIUM", "LUXURY" -> new LuxuryCarFactory(brand);
            default -> throw new IllegalArgumentException("Unknown Car Type : " + type);
        };

    }
}
