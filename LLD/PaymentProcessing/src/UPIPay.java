public class UPIPay implements PaymentStrategy{
    @Override
    public void processPayment(int amt) {
        System.out.println("Payment had been made by UPI of amount : "+ amt);
    }
}
