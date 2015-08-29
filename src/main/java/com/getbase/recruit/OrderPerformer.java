package com.getbase.recruit;

import com.getbase.recruit.orders.Order;

public class OrderPerformer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		final TaxOfficeAdapter taxOfficeAdapter = new TaxOfficeAdapterImpl();
		final ItemsRepository itemsRepository = new ItemsRepoImpl();
		
		OrdersManagementSystemImpl oms = new OrdersManagementSystemImpl(taxOfficeAdapter,
				itemsRepository);
		
		oms.createOrder(0, 2, OrderFlag.PRIORITY);
		oms.createOrder(0, 1, OrderFlag.INTERNATIONAL);
		oms.createOrder(0, 3, OrderFlag.DISCOUNTED);
		oms.createOrder(1, 4, OrderFlag.STANDARD);
		oms.createOrder(1, 2, OrderFlag.PRIORITY);
		oms.createOrder(2, 55, OrderFlag.DISCOUNTED);
		
//		all combined orders cases
		oms.createOrder(1, 66, OrderFlag.INTERNATIONAL, OrderFlag.PRIORITY);
		oms.createOrder(1, 66, OrderFlag.INTERNATIONAL, OrderFlag.DISCOUNTED);
		oms.createOrder(1, 66, OrderFlag.PRIORITY, OrderFlag.DISCOUNTED);
		oms.createOrder(1, 66, OrderFlag.PRIORITY, OrderFlag.DISCOUNTED, OrderFlag.INTERNATIONAL);
		
		for (Order o : oms.getOrdersQueue())
			System.out.println("item: " + o.getItemId() +
					" customer: " + o.getCustomerId() +
					" price: " + o.getPrice() + " tax: " + o.getTax() +
					" full price: " + o.getTotalAmount());
	}

}
