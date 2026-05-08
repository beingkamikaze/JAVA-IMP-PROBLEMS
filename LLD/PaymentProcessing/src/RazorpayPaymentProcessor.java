public class RazorpayPaymentProcessor implements PaymentProcessor{
    @Override
    public void pay(int amount) {
        System.out.println("Razorpay payment of " + amount);
    }
}
