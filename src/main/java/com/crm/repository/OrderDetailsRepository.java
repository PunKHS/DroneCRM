package com.crm.repository;

import com.crm.model.OrderDetails;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OrderDetailsRepository extends PagingAndSortingRepository<OrderDetails, Long> {
    List<OrderDetails> findByOrdersId(Long id);
}
