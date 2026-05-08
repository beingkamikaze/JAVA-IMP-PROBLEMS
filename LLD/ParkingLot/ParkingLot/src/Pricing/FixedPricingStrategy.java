package Pricing;

import Entity.Ticket;

public class FixedPricingStrategy implements PricingStrategy{
    @Override
    public double calcultae(Ticket ticket) {
        return 100;
    }
}
