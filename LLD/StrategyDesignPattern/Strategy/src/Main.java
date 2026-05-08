public class Main {
    public static void main(String[] args) {

        ShoppingCart shoppingCart = new ShoppingCart(new UPIPayment());
        //shoppingCart.setPaymentStrategy(new UPIPayment());

        shoppingCart.checkout(200);
    }
}