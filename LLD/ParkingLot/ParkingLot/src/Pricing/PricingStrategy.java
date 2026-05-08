package Pricing;

import Entity.Ticket;

public interface PricingStrategy {
    double calcultae(Ticket ticket);
}
