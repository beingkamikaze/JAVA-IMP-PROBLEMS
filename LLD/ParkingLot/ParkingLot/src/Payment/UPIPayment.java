package Payment;

public class UPIPayment implements Payment{
    @Override
    public boolean pay(double amount) {
        System.out.println("UPI PAID : " +amount);
        return true;
    }
}
