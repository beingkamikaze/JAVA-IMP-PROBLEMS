public class  OneHundredHandler extends ATMHandler{

    OneHundredHandler(ATMHandler nextATMHandler) {
        super(nextATMHandler,HandlerLevel.ONE_HUNDRED.value());
    }
}
