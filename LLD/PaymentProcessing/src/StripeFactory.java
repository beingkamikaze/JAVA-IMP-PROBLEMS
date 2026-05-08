public class StripeFactory implements PaymentGatewayFactory{
    @Override
    public PaymentProcessor createPaymentProcessor() {
        return null;
    }

    @Override
    public RefundProcessor createRefundProcessor() {
        return null;
    }

    @Override
    public VerificationProcessor createVerificationProcessor() {
        return null;
    }
}
