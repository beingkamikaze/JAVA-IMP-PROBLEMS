package SpotManagers;

import Entity.ParkingSpot;
import LookupStrategy.ParkingSpotLookupStrategy;

import java.util.List;

public class FourWheelerSpotManager extends ParkingSpotManager{
    public FourWheelerSpotManager(List<ParkingSpot> spots, ParkingSpotLookupStrategy strategy) {
        super(spots, strategy);
    }
}
