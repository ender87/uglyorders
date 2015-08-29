package com.getbase.recruit.orders;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.getbase.recruit.SeriousEnterpriseEventBus;
import com.getbase.recruit.SeriousEnterpriseEventBusLookup;
import com.getbase.recruit.TaxCalculationsHelper;

public class PriorityOrder extends Order {

	private BigDecimal newPrice;

	public PriorityOrder(int itemId, int customerId, BigDecimal price) {
		super(itemId, customerId, price);
	}

	@Override
	public void process() {
		SeriousEnterpriseEventBus seeb = SeriousEnterpriseEventBusLookup.seeb;
		seeb.sendEvent("Order processing started");

		seeb.sendEvent("*** This is priority order, hurry up! ***");

		seeb.sendEvent("Initiate shipment");
		seeb.sendEvent("Order processing finished");
	}

	@Override
	public BigDecimal getPrice() {
		// adding priority order fee - 1.5%
		this.newPrice = TaxCalculationsHelper.addPercentage(super.getPrice(), new BigDecimal("1.5"));
		return this.newPrice; //.setScale(2, RoundingMode.UP);
	}

	@Override
	public BigDecimal getTotalAmount() {
		return this.newPrice.add(getTax()); //.setScale(2, RoundingMode.UP);
	}
}
