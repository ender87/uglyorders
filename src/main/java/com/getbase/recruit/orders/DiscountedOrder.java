package com.getbase.recruit.orders;

import com.getbase.recruit.SeriousEnterpriseEventBus;
import com.getbase.recruit.SeriousEnterpriseEventBusLookup;
import com.getbase.recruit.TaxCalculationsHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DiscountedOrder extends Order {
	
	private BigDecimal newPrice;

    public DiscountedOrder(int itemId, int customerId, BigDecimal price) {
        super(itemId, customerId, price);
    }

    @Override
    public BigDecimal getPrice() {
        //subtracting standard discount - 11%
        this.newPrice = TaxCalculationsHelper.subtractPercentage(super.getPrice(), new BigDecimal("11"));
        return this.newPrice.setScale(2, RoundingMode.UP);
    }
    
    @Override
    public BigDecimal getTotalAmount() {
        return this.newPrice.add(getTax()).setScale(2, RoundingMode.UP);
    }

    public void process() {
        SeriousEnterpriseEventBus seeb = SeriousEnterpriseEventBusLookup.seeb;
        seeb.sendEvent("Order processing started");

        seeb.sendEvent("Run fraud detection and revenue integrity check");

        seeb.sendEvent("Initiate shipment");
        seeb.sendEvent("Order processing finished");
    }
}
