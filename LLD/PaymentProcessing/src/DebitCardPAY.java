public class DebitCardPAY implements PaymentStrategy{
    @Override
    public void processPayment(int amt) {
        System.out.println("Payment had been made by Debit-Card of amount : "+ amt);
    }
}
