package com.getbase.recruit;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.getbase.recruit.orders.Order;

@RunWith(MockitoJUnitRunner.class)
public class OrdersManagementSystemImplTest {

	@Mock
	private TaxOfficeAdapter taxOfficeAdapter;
	@Mock
	private ItemsRepository itemsRepository;

	@InjectMocks
	private OrdersManagementSystemImpl ordersManagementSystem = new OrdersManagementSystemImpl(taxOfficeAdapter,
			itemsRepository);

	@Before
	public void SetUp() {
		given(itemsRepository.fetchItemPrice(1)).willReturn(new BigDecimal("3.33"));
		given(itemsRepository.fetchItemPrice(2)).willReturn(new BigDecimal("10.00"));
	}

	@Test
	public void priority_order_should_be_returned_first() {

		// when
		ordersManagementSystem.createOrder(1, 1, OrderFlag.STANDARD);
		ordersManagementSystem.createOrder(2, 1, OrderFlag.PRIORITY);
		Order nextOrder = ordersManagementSystem.fetchNextOrder();

		// then
		assertThat(nextOrder).isNotNull();
		assertThat(nextOrder.getItemId()).isEqualTo(2);
	}

	@Test
	public void tax_amount_sent_to_tax_office_should_be_correct() {

		// when
		ordersManagementSystem.createOrder(1, 1, OrderFlag.PRIORITY);
		Order nextOrder = ordersManagementSystem.fetchNextOrder();

		// then
		assertThat(nextOrder).isNotNull();

		// should be 0.77 tax because:
		// 3.33 + 1.5% = 3.38 3.38 * 23.5% = 0.80
		verify(taxOfficeAdapter).registerTax(new BigDecimal("0.80"));
	}

	@Test
	public void combined_priority_order_should_be_returned_first() {

		// when
		ordersManagementSystem.createOrder(1, 1, OrderFlag.STANDARD);
		ordersManagementSystem.createOrder(2, 1, OrderFlag.PRIORITY, OrderFlag.DISCOUNTED);
		Order nextOrder = ordersManagementSystem.fetchNextOrder();

		// then
		assertThat(nextOrder).isNotNull();
		assertThat(nextOrder.getItemId()).isEqualTo(2);
	}

}