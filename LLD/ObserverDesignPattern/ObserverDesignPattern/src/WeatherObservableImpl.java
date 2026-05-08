import java.util.ArrayList;
import java.util.List;

public class WeatherObservableImpl implements WeatherObservable{
    //List of Observer Ready for Upadtes
    private final List<WeatherObserver> observers;
    //List Initialization using Constructor
    //Observable Data
    private float temp;
    private float humidity;
    public WeatherObservableImpl(){
        observers = new ArrayList<>();
    }
    @Override
    public void addObserver(WeatherObserver obj) {
        observers.add(obj);
        System.out.println("Observer had been had to List Observers : "
        +obj.getClass().getSimpleName());
    }

    @Override
    public void removeObserver(WeatherObserver obj) {
        observers.remove(obj);
        System.out.println("observer had been removed from list : "+
                obj.getClass().getSimpleName());
    }

    @Override
    public void notifySystem() {
        for(WeatherObserver observer : observers)
        {
            observer.update();
        }
    }

    @Override
    public void setData(float temp, float humidity) {
        this.temp=temp;
        this.humidity=humidity;
        notifySystem();
    }

    //getter for observer
    public float getTemp() {
        return temp;
    }

    public float getHumidity() {
        return humidity;
    }

    @Override
    public String toString() {
        return "WeatherObservableImpl{" +
                "observers=" + observers +
                ", temp=" + temp +
                ", humidity=" + humidity +
                '}';
    }
}
