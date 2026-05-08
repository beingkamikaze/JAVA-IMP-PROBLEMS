public class CreditCardPayment implements PaymentStrategy{
    private  String CardNumber;
    @Override
    public void pay(double amt) {
        System.out.println("Payment Done via CreditCard with CardNumber : " + CardNumber);
    }
}
