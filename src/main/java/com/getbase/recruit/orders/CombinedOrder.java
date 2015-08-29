/**
 * 2015-08-10
 * @author ender
 */
package com.getbase.recruit.orders;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import com.getbase.recruit.OrderFlag;
import com.getbase.recruit.TaxCalculationsHelper;

/**
 * Order's child class. Contains methods for handling combined orders with types:
 * discounted priority,
 * discounted international,
 * priority international and
 * discounted priority international.
 * Order type is recognized on the basis of given com.getbase.recruit.OrderFlag array.
 */
public class CombinedOrder extends Order {

	private List<OrderFlag> flags;
	private BigDecimal newPrice;

	public CombinedOrder(int itemId, int customerId, BigDecimal price, OrderFlag[] flags) {
		super(itemId, customerId, price);
		this.flags = Arrays.asList(flags);
	}

	@Override
	public void process() {
		super.process();
	}

	@Override
	public BigDecimal getTax() {
		if (flags.contains(OrderFlag.INTERNATIONAL))
			return TaxCalculationsHelper.getPercentagePart(getPrice(), new BigDecimal("15.0")).setScale(2,
					RoundingMode.UP);
		else
			return super.getTax();
	}

	@Override
	public BigDecimal getPrice() {
		this.newPrice = super.getPrice();
		if (this.flags.contains(OrderFlag.PRIORITY))
			this.newPrice = TaxCalculationsHelper.addPercentage(newPrice, new BigDecimal("1.5"));
		if (this.flags.contains(OrderFlag.DISCOUNTED))
			this.newPrice = TaxCalculationsHelper.subtractPercentage(newPrice, new BigDecimal("11"));

		return this.newPrice; //.setScale(2, RoundingMode.UP);
	}

	@Override
	public BigDecimal getTotalAmount() {
		return this.newPrice ;//.add(getTax()).setScale(2, RoundingMode.UP);
	}

}
