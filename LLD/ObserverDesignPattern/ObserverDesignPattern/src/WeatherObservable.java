//Observable (Subject) Interface
public interface WeatherObservable {
    void addObserver(WeatherObserver obj);
    void removeObserver(WeatherObserver obj);
    void notifySystem();
    void setData(float temp,float humidity);
}
