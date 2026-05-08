//Abstract Factory interface
public interface CarFactory {
    //Factory Method to craete object
    CarInterior createCarInterior();
    CarExterior createCarExterior();

    // Template method that uses all factory methods

    default void produceCompleteVehicle(){
        System.out.println("Starting complete vehicle production...");

        //create all component
        CarInterior carInterior = createCarInterior();
        CarExterior carExterior = createCarExterior();

        carInterior.addInteriorComponents();
        carExterior.addExteriorComponents();

        System.out.println("Vehicle production completed!");
    }
}
