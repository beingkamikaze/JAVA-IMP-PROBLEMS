public abstract class LogHandler {

    LogLevels loglevel;

    LogHandler nextLogHandler;

    public void setNextLogHandler(LogHandler nextLogHandler) {
        this.nextLogHandler = nextLogHandler;
    }

    public void logMessage(LogLevels loglevel,String message)
    {
        if(this.loglevel.getPriority() <= loglevel.getPriority())
        {
            write(message);
        }
        if (nextLogHandler != null) {
            nextLogHandler.logMessage(loglevel, message);
        }
    }
    abstract void write(String message);
}
