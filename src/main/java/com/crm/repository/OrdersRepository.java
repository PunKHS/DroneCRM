package com.crm.repository;

import com.crm.model.Orders;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrdersRepository extends PagingAndSortingRepository<Orders, Long> {
}
