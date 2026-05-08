package Pricing;

import Entity.Ticket;

public class CostComputation {
    private final PricingStrategy pricingStrategy;

    public CostComputation(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public double compute(Ticket ticket)
    {
        return pricingStrategy.calcultae(ticket);
    }
}
