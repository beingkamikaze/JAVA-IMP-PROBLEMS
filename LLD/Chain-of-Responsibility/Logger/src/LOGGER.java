//Facacde Implementation so that client should not have to care of obj creation and chaining
public enum LOGGER {
    INSTANCE;
    private final LogHandler chain;
    private LOGGER(){
        LogHandler debug = new DebugLogger();
        LogHandler info = new InfoLogger();
        LogHandler error = new ErrorLogger();
        debug.setNextLogHandler(info);
        info.setNextLogHandler(error);

        this.chain=debug;
        //entry point
    }
    public void log(LogLevels levels,String message)
    {
        chain.logMessage(levels,message);
    }

}
