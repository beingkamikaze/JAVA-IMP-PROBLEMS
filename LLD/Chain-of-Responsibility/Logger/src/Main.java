public class Main {
    public static void main(String[] args) {

        // Access the single Logger instance
        LOGGER logger = LOGGER.INSTANCE;
        logger.log(LogLevels.INFO,"Hello");
    }
}