public class RazorpayFactory implements PaymentGatewayFactory{
    @Override
    public PaymentProcessor createPaymentProcessor() {
        return new RazorpayPaymentProcessor();
    }

    @Override
    public RefundProcessor createRefundProcessor() {
        return new RazorpayRefundProcessor();
    }

    @Override
    public VerificationProcessor createVerificationProcessor() {
        return new RazorpayVerificationProcessor();
    }
}
