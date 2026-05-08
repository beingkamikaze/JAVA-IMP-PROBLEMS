public class Main {
    public static void main(String[] args) {

        System.out.println("======= Decorator Design Pattern ======");
        // Create a plain pizza
        BasePizza pizza1 = new PlainPizza();
        System.out.println("Order 1: " + pizza1.getDescription() + " = Rs." + pizza1.getCost());

        //add topping (Decorate the pizza) PlainPizza + Extra Cheese Only
        BasePizza pizza2 = new ExtraCheeseTopping(new PlainPizza());
        System.out.println("Order 2: " + pizza2.getDescription() + " = Rs." + pizza2.getCost());

        //mULTIPLE LAYERS OF DECORATORS
        // Add toppings to the PlainPizza + Extra Cheese, Mushroom and
        //Pepperoni

        BasePizza pizza3 = new PepperoniTopping(new ExtraCheeseTopping(new MushroomTopping(new PlainPizza())));
        System.out.println("Order 3: " + pizza3.getDescription() + " = Rs." + pizza3.getCost());

    }
}