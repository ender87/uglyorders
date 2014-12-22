package com.getbase.recruit;

import com.getbase.recruit.orders.Order;

public interface OrdersManagementSystem {

    public void createOrder(int itemId, int customerId,OrderFlag... flags);
    public Order fetchNextOrder();
}
