package ParkingLot;

import Entity.Ticket;
import Entity.Vehicle;
import Payment.Payment;

public class ParkingLotC {
    private final ParkingBuilding building;
    private final EntranceGate entranceGate;
    private final ExitGate exitGate;

    public ParkingLotC(ParkingBuilding building, EntranceGate entranceGate, ExitGate exitGate) {
        this.building = building;
        this.entranceGate = entranceGate;
        this.exitGate = exitGate;
    }

    public Ticket vehicleArrives(Vehicle vehicle)
    {
       return entranceGate.enter(building,vehicle);
    }

    public void vehicleExits(Ticket ticket, Payment payment){
        exitGate.completeExit(building,ticket,payment);
    }


}
