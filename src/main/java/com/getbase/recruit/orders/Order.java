package com.getbase.recruit.orders;

import com.getbase.recruit.SeriousEnterpriseEventBus;
import com.getbase.recruit.SeriousEnterpriseEventBusLookup;
import com.getbase.recruit.TaxCalculationsHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class Order {
    private final int itemId;
    private final int customerId;
    private final BigDecimal price;

    //for performance reasons lets pre-calculate it in constructor
    private int preCalculatedHashCode = 0;

    private static Random random = new Random();

    public Order(int itemId, int customerId, BigDecimal price) {
        this.itemId = itemId;
        this.customerId = customerId;
        this.price = price;
        preCalculatedHashCode = random.nextInt();
    }

    public void process() {
        SeriousEnterpriseEventBus seeb = SeriousEnterpriseEventBusLookup.seeb;
        seeb.sendEvent("Order processing started");
        seeb.sendEvent("Initiate shipment");
        seeb.sendEvent("Order processing finished");
    }

    public BigDecimal getTotalAmount() {
        return price.add(getTax()).setScale(2, RoundingMode.UP);
    }

    public int getItemId() {
        return itemId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public BigDecimal getPrice() {
        return price.setScale(2, RoundingMode.UP);
    }

    public BigDecimal getTax() {
        //calculating standard tax - 23.5%
        return TaxCalculationsHelper.getPercentagePart(getPrice(), new BigDecimal("23.5")).setScale(2, RoundingMode.UP);
    }

    @Override
    public int hashCode() {
        return preCalculatedHashCode;
    }

}
