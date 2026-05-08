package ParkingLot;

import Entity.Ticket;
import Payment.Payment;
import Pricing.CostComputation;

public class ExitGate {
    private final CostComputation costComputation;

    public ExitGate(CostComputation costComputation) {
        this.costComputation = costComputation;
    }

    public void completeExit(ParkingBuilding building, Ticket ticket, Payment payment){
        double amount = calculatePrice(ticket);

        boolean success = payment.pay(amount);
        if(!success){
            throw new RuntimeException("Payment failed --> Exit Denied");
        }
        building.release(ticket);
        System.out.println("Exit SuccessFull; Gate Opened");
    }

    private double calculatePrice(Ticket ticket) {
        return costComputation.compute(ticket);
    }
}
