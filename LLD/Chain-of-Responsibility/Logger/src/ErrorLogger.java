public class ErrorLogger extends LogHandler{
    ErrorLogger(){
        this.loglevel=LogLevels.ERROR;
    }
    @Override
    void write(String message) {
        System.out.println("ERROR :" + message);
    }
}
