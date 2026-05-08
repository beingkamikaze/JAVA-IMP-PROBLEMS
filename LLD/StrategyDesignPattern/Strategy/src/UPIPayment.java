import java.util.UUID;

public class UPIPayment implements PaymentStrategy{

    UUID transaction_id = new UUID(4,6);
    @Override
    public void pay(double amt) {
        System.out.println("Payment made via UPI with transaction_id : " + transaction_id);
    }
}
