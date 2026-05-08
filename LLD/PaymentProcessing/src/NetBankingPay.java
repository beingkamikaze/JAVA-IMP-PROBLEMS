public class NetBankingPay implements PaymentStrategy{
    @Override
    public void processPayment(int amt) {
        System.out.println("Payment had been made by NetBanking of amount : "+ amt);
    }
}
