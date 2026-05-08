import Entity.ParkingSpot;
import Entity.Ticket;
import Entity.Vehicle;
import Enums.VehicleType;
import LookupStrategy.ParkingSpotLookupStrategy;
import LookupStrategy.RandomLookupStrategy;
import ParkingLot.*;
import Payment.CashPayment;
import Payment.UPIPayment;
import Pricing.CostComputation;
import Pricing.FixedPricingStrategy;
import SpotManagers.FourWheelerSpotManager;
import SpotManagers.ParkingSpotManager;
import SpotManagers.TwoWheelerSpotManger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //select which startegy to use for spot lookup
        ParkingSpotLookupStrategy parkingSpotLookupStrategy = new RandomLookupStrategy();

        Map<VehicleType, ParkingSpotManager> levelOneManager = new HashMap<>();
        levelOneManager.put(VehicleType.TWO_WHEELER,
                new TwoWheelerSpotManger(List.of(new ParkingSpot("L1-S1"),
                        new ParkingSpot("L1-S2")),parkingSpotLookupStrategy));
        levelOneManager.put(VehicleType.FOUR_WHEELER,
                new FourWheelerSpotManager(List.of(new ParkingSpot("L1-S3")),parkingSpotLookupStrategy));

        ParkingLevel level1 = new ParkingLevel(1,levelOneManager);

        Map<VehicleType, ParkingSpotManager> levelTwoManagers = new HashMap<>();
        levelTwoManagers.put(VehicleType.TWO_WHEELER,
                new TwoWheelerSpotManger(List.of(new ParkingSpot("L2-S1")), parkingSpotLookupStrategy));

        levelTwoManagers.put(VehicleType.FOUR_WHEELER,
                new FourWheelerSpotManager(List.of(new ParkingSpot("L2-S2"),
                        new ParkingSpot("L2-S3")), parkingSpotLookupStrategy));

        ParkingLevel level2 = new ParkingLevel(
                2, levelTwoManagers
        );

        ParkingBuilding parkingBuilding =
                new ParkingBuilding(List.of(level1, level2));

        ParkingLotC parkingLot = new ParkingLotC(
                parkingBuilding,
                new EntranceGate(),
                new ExitGate(new CostComputation(new FixedPricingStrategy()))
        );

        Vehicle bike = new Vehicle("BIKE-101",VehicleType.TWO_WHEELER);
        Vehicle car = new Vehicle("CAR-201", VehicleType.FOUR_WHEELER);
        Vehicle car1 = new Vehicle("CAR-201", VehicleType.FOUR_WHEELER);

        Ticket t1 = parkingLot.vehicleArrives(bike);
        Ticket t2 = parkingLot.vehicleArrives(car);
        Ticket t3 = parkingLot.vehicleArrives(car1);

//        parkingLot.vehicleExits(t1, new CashPayment());
//        parkingLot.vehicleExits(t2, new UPIPayment());

    }
}