package ParkingLot;

import Entity.ParkingSpot;
import Entity.Ticket;
import Entity.Vehicle;

import java.util.List;

public class ParkingBuilding {
    private final List<ParkingLevel> levels;

    public ParkingBuilding(List<ParkingLevel> levels) {
        this.levels = levels;
    }

    Ticket allocate(Vehicle vehicle){
        for(ParkingLevel level:levels){
            if(level.hasAvailability(vehicle.getType())){
                ParkingSpot spot =  level.park(vehicle.getType());
                if(spot!=null)
                {
                    Ticket ticket = new Ticket(vehicle,level,spot);
                    System.out.println("Parking allocated at level: "
                            + level.getLevelNumber()
                            + " spot: " + spot.getSpotId());
                }
            }
        }
        throw new RuntimeException("Parking Full");
    }
    void  release(Ticket ticket){
        ticket.getLevel().unpark(ticket.getVehicle().getType(),ticket.getSpot());
    }
}
