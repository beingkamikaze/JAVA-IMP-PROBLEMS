public class RazorpayRefundProcessor implements RefundProcessor{
    @Override
    public void refundProcessor(int amt) {
        System.out.println("Razorpay refund of " + amt);
    }
}
