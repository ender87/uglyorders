package com.getbase.recruit;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import com.getbase.recruit.orders.CombinedOrder;
import com.getbase.recruit.orders.DiscountedOrder;
import com.getbase.recruit.orders.InternationalOrder;
import com.getbase.recruit.orders.Order;
import com.getbase.recruit.orders.PriorityOrder;

public class OrdersManagementSystemImpl implements OrdersManagementSystem {

	// external systems
	private final TaxOfficeAdapter taxOfficeAdapter;
	private final ItemsRepository itemsRepository;

	private Set<Order> ordersQueue = new LinkedHashSet<Order>();
	private Order newOrder = null;
	private Order nextOrder = null;

	public OrdersManagementSystemImpl(TaxOfficeAdapter taxOfficeAdapter, ItemsRepository itemsRepository) {
		this.taxOfficeAdapter = taxOfficeAdapter;
		this.itemsRepository = itemsRepository;
	}

	@Override
	public void createOrder(int itemId, int customerId, OrderFlag... flags) {

		// fetch price and calculate discount and taxes
		BigDecimal itemPrice = itemsRepository.fetchItemPrice(itemId);

		// create and queue order
		if (flags.length > 1)
			newOrder = new CombinedOrder(itemId, customerId, itemPrice, flags);
		else { // usual order
			OrderFlag flag = flags[0];
			switch (flag) {
			case STANDARD:
				newOrder = new Order(itemId, customerId, itemPrice);
				break;
			case PRIORITY:
				newOrder = new PriorityOrder(itemId, customerId, itemPrice);
				break;
			case INTERNATIONAL:
				newOrder = new InternationalOrder(itemId, customerId, itemPrice);
				break;
			case DISCOUNTED:
				newOrder = new DiscountedOrder(itemId, customerId, itemPrice);
				break;
			}
		}

		ordersQueue.add(newOrder);

		// Priority orders are always fetched immediately (shifted to queue's
		// beginning)
		if (Arrays.asList(flags).contains(OrderFlag.PRIORITY)) {
			while (!fetchNextOrder().equals(newOrder)) {
				nextOrder = fetchNextOrder();
				ordersQueue.remove(nextOrder);
				ordersQueue.add(newOrder);
				ordersQueue.add(nextOrder);
			}
		}

		// send tax due amount
		taxOfficeAdapter.registerTax(newOrder.getTax());
	}

	public Set<Order> getOrdersQueue() {
		return ordersQueue;
	}

	@Override
	public Order fetchNextOrder() {
		return ordersQueue.iterator().next();
	}
}
