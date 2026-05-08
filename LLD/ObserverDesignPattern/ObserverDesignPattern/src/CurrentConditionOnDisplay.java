public class CurrentConditionOnDisplay implements WeatherObserver{
    //final to make it immutable
    private final WeatherObservable weatherStation;

    public CurrentConditionOnDisplay(WeatherObservable weatherStation) {
        this.weatherStation = weatherStation;
        weatherStation.addObserver(this);
    }

    @Override
    public void update() {
        System.out.println("Saving weather Data");
        display();
    }

    public void display(){
        System.out.println("Current Weather Condition : " +
                weatherStation.toString());
    }
}
