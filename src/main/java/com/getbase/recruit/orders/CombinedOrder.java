/**
 * 
 */
package com.getbase.recruit.orders;

import java.math.BigDecimal;

import com.getbase.recruit.OrderFlag;
import com.getbase.recruit.TaxCalculationsHelper;

/**
 * @author ender
 *
 */
public class CombinedOrder extends Order {

	/**
	 * @param itemId
	 * @param customerId
	 * @param price
	 */
	public CombinedOrder(int itemId, int customerId, BigDecimal price,
			OrderFlag[] flags) {
		super(itemId, customerId, price);
	}
	
	public void process(){
		super.process();
	}
	
	public BigDecimal getTax(){
		return null;
		
	}
	
	@Override
	public BigDecimal getPrice(){
		return TaxCalculationsHelper.addPercentage(super.getPrice(),new BigDecimal("1.5"));
	}

}
