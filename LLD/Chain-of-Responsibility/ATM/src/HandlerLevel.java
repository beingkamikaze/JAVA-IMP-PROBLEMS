public enum HandlerLevel {
    TWO_THOUSAND(2000),
    FIVE_HUNDRED(500),
    ONE_HUNDRED(100);

    private final int value;

    HandlerLevel(int value) {
        this.value = value;
    }

    int value()
    {
        return value;
    }
}
