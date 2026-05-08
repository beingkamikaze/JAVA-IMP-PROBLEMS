package SpotManagers;

import Entity.ParkingSpot;
import LookupStrategy.ParkingSpotLookupStrategy;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public abstract class ParkingSpotManager {

    protected final List<ParkingSpot> spots;
    protected final ParkingSpotLookupStrategy strategy;

    public ParkingSpotManager(List<ParkingSpot> spots, ParkingSpotLookupStrategy strategy) {
        this.spots = spots;
        this.strategy = strategy;
    }

    private final ReentrantLock lock = new ReentrantLock(true);

    //Manger have method to park
    public ParkingSpot park(){
        lock.lock();
        try{
            //select spot NOTE:
            ParkingSpot spot = strategy.selectSpot(spots); //can return null as well
            if(spot==null)
            {
                return null;
            }
            //occupy the spot
            spot.occupySpot();
            return spot;
        } finally {
            lock.unlock();
        }
    }
    //Manger have method for unpark() as well
    //release occupied spot
    public void unpark(ParkingSpot spot){
        lock.lock();
        try{
            spot.releaseSpot();
        }finally {
            lock.unlock();
        }
    }
    //method to check any spot is free ?
    public boolean hasFreeSpot(){
        lock.lock();
        try{
            return spots.stream().anyMatch(ParkingSpot::isSpotFree);
        }finally {
            lock.unlock();
        }
    }
}
