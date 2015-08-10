package com.getbase.recruit.orders;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.getbase.recruit.SeriousEnterpriseEventBus;
import com.getbase.recruit.SeriousEnterpriseEventBusLookup;
import com.getbase.recruit.TaxCalculationsHelper;

//TODO will have more taxes calculated
public class InternationalOrder extends Order {

	public InternationalOrder(int itemId, int customerId, BigDecimal price) {
		super(itemId, customerId, price);
	}

	@Override
	public BigDecimal getTax() {
		// calculating international tax - 15.0%
		return TaxCalculationsHelper.getPercentagePart(getPrice(), new BigDecimal("15.0")).setScale(2, RoundingMode.UP);
	}

	@Override
	public void process() {
		SeriousEnterpriseEventBus seeb = SeriousEnterpriseEventBusLookup.seeb;
		seeb.sendEvent("Order processing started");

		seeb.sendEvent("Dispatch translated order confirmation email");

		seeb.sendEvent("Initiate shipment");
		seeb.sendEvent("Order processing finished");
	}
}
