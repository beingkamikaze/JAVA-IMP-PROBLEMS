public class PaymentService {
    private final PaymentStrategy paymentStrategy;
    private final PaymentGatewayFactory paymentGatewayFactory;

    public PaymentService(PaymentStrategy paymentStrategy, PaymentGatewayFactory paymentGatewayFactory) {
        this.paymentStrategy = paymentStrategy;
        this.paymentGatewayFactory = paymentGatewayFactory;
    }

    public void makePayment(int amount)
    {
        VerificationProcessor verificationProcessor = paymentGatewayFactory.createVerificationProcessor();
        PaymentProcessor paymentProcessor = paymentGatewayFactory.createPaymentProcessor();

        paymentStrategy.processPayment(amount);
        paymentProcessor.pay(amount);
    }
}
