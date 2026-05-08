public class Main {
    public static void main(String[] args) {

        System.out.println("=====Abstract Factory Design Pattern=====");

        //getFactoryProvide
        CarFactoryProvider carFactoryProvider = new CarFactoryProvider();

        //getFactoryProvide-->Eco
        CarFactory economyCar=carFactoryProvider.getFactory("ECONOMY","Maruti");
        CarExterior ex= economyCar.createCarExterior();
        ex.addExteriorComponents();
    }
}