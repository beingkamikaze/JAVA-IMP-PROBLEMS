public class FiveHundredHandler extends ATMHandler{

    FiveHundredHandler(ATMHandler nextATMHandler) {
        super(nextATMHandler,HandlerLevel.FIVE_HUNDRED.value());
    }
}
