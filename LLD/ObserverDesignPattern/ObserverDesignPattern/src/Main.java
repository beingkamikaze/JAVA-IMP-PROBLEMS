public class Main {
    public static void main(String[] args) {

        //Observable
        WeatherObservable weatherObservable = new WeatherObservableImpl();

        //Observer
        WeatherObserver weatherObserver = new CurrentConditionOnDisplay(weatherObservable);

        System.out.println("====firt UPDATE====");
        weatherObservable.setData(0.1f,0.2f);

        System.out.println("===second Update===");
        weatherObservable.setData(0.3f,0.4f);

    }
}