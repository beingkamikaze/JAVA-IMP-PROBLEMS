public class CreditCardPay implements PaymentStrategy{
    @Override
    public void processPayment(int amt) {
        System.out.println("Payment had been made by Credit of amount : "+ amt);
    }
}
