package ParkingLot;

import Entity.ParkingSpot;
import Enums.VehicleType;
import SpotManagers.ParkingSpotManager;

import java.util.Map;

public class ParkingLevel {
    private final int levelNumber;
    //map{VehileType-->spotManger(2w/4w)}
    private final Map<VehicleType, ParkingSpotManager> mangers;

    public ParkingLevel(int levelNumber, Map<VehicleType, ParkingSpotManager> mangers) {
        this.levelNumber = levelNumber;
        this.mangers = mangers;
    }

    public boolean hasAvailability(VehicleType type){
        ParkingSpotManager manager = mangers.get(type);
        return manager!=null && manager.hasFreeSpot();
    }

    public ParkingSpot park(VehicleType type){
        ParkingSpotManager manager = mangers.get(type);
        if (manager==null)
        {
            throw new IllegalArgumentException("No parking manager available for Vehivle type : "+type);

        }
        return manager.park();
    }

    public void unpark(VehicleType type,ParkingSpot spot){
        ParkingSpotManager manager = mangers.get(type);
        if(manager!=null)
        {
            manager.unpark(spot);
        }
    }
    public int getLevelNumber()
    {
        return levelNumber;
    }
}
