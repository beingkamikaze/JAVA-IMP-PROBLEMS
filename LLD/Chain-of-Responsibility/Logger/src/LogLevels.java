public enum LogLevels {
    DEBUG(1),
    INFO(2),
    ERROR(3);

    private final int priority;

    //constructorcall
    LogLevels(int priority)
    {
        this.priority=priority;
    }

    public int getPriority(){
        return priority;
    }
}
