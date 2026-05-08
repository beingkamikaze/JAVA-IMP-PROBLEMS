public class InfoLogger extends LogHandler{
    InfoLogger(){
        this.loglevel=LogLevels.INFO;
    }
    @Override
    void write(String message) {
        System.out.println("INFO : "+message);
    }
}
