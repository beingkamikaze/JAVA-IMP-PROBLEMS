package Entity;

import Enums.VehicleType;

public class Vehicle {
    String VehicleNumber;
    VehicleType Type;

    public Vehicle(String vehicleNumber, VehicleType type) {
        this.VehicleNumber = vehicleNumber;
        this.Type = type;
    }

    //getters and setter
    public String getVehicleNumber() {
        return VehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        VehicleNumber = vehicleNumber;
    }

    public VehicleType getType() {
        return Type;
    }

    public void setType(VehicleType type) {
        Type = type;
    }
}
