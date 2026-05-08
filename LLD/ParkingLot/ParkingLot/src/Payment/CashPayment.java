package Payment;

public class CashPayment implements Payment{
    @Override
    public boolean pay(double amount) {
        System.out.println("CASH PAID : "+amount);
        return true;
    }
}
