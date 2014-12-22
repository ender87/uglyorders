package com.getbase.recruit.orders;

import com.getbase.recruit.SeriousEnterpriseEventBus;
import com.getbase.recruit.SeriousEnterpriseEventBusLookup;
import com.getbase.recruit.TaxCalculationsHelper;

import java.math.BigDecimal;

public class PriorityOrder extends Order {

    public PriorityOrder(int itemId, int customerId, BigDecimal price) {
        super(itemId, customerId, price);
    }

    public void process() {
        SeriousEnterpriseEventBus seeb = SeriousEnterpriseEventBusLookup.seeb;
        seeb.sendEvent("Order processing started");

        seeb.sendEvent("*** This is priority order, hurry up! ***");

        seeb.sendEvent("Initiate shipment");
        seeb.sendEvent("Order processing finished");
    }

    @Override
    public BigDecimal getPrice() {
        //adding priority order fee - 1.5%
        return TaxCalculationsHelper.addPercentage(super.getPrice(),new BigDecimal("1.5"));
    }
}
