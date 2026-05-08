public class ShoppingCart {
    PaymentStrategy paymentStrategy;

//    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
//        this.paymentStrategy = paymentStrategy;
//    }
    public ShoppingCart(PaymentStrategy paymentStrategy){
        this.paymentStrategy=paymentStrategy;
    }

    public void checkout(double amt){
        System.out.println(this.paymentStrategy.getClass().getSimpleName() +":");
        paymentStrategy.pay(amt);
    }
}
