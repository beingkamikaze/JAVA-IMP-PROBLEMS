public class TwoThousandHandler extends ATMHandler{
    TwoThousandHandler(ATMHandler nextATMHandler) {
        super(nextATMHandler,HandlerLevel.TWO_THOUSAND.value());
    }
}
