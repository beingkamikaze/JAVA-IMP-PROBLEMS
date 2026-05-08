public class DebugLogger extends LogHandler{
    DebugLogger()
    {
        this.loglevel=LogLevels.DEBUG;
    }
    @Override
    void write(String message) {
        System.out.println("DEBUG " + message);
    }
}
