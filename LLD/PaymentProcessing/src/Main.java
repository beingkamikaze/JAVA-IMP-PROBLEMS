public class Main {
    public static void main(String[] args) {

        // -------- Case 1: Credit Card + Razorpay --------
        PaymentStrategy creditCard = new CreditCardPay();
        PaymentGatewayFactory razorpay = new RazorpayFactory();

        PaymentService service1 =
                new PaymentService(creditCard, razorpay);

        service1.makePayment(5000);

        System.out.println("--------------------------------");

        // -------- Case 2: UPI + Stripe --------
//        PaymentStrategy upi = new UPIPay();
//        PaymentGatewayFactory stripe = new StripeFactory();
//
//        PaymentService service2 =
//                new PaymentService(upi, stripe);
//
//        service2.makePayment(1500);
    }
}