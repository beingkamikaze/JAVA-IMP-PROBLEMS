package SpotManagers;

import Entity.ParkingSpot;
import LookupStrategy.ParkingSpotLookupStrategy;

import java.util.List;

public class TwoWheelerSpotManger extends ParkingSpotManager{
    public TwoWheelerSpotManger(List<ParkingSpot> spots, ParkingSpotLookupStrategy strategy) {
        super(spots, strategy);
    }
}
