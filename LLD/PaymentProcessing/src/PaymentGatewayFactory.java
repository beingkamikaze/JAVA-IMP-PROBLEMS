public interface PaymentGatewayFactory {
    PaymentProcessor createPaymentProcessor();
    RefundProcessor createRefundProcessor();
    VerificationProcessor createVerificationProcessor();
}
