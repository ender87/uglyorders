package com.getbase.recruit;

import java.math.BigDecimal;

public class ItemsRepoImpl implements ItemsRepository {
	
	private BigDecimal[] prices;
	
	public ItemsRepoImpl(){
		this.prices = new BigDecimal[]{BigDecimal.valueOf(1008768.28),
				BigDecimal.valueOf(100),
				BigDecimal.valueOf(150.00)};
	}

	public BigDecimal fetchItemPrice(int itemId) {
		return this.prices[itemId];
	}

}