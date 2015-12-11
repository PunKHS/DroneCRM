package com.crm.repository;

import com.crm.model.Customers;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomersRepository extends PagingAndSortingRepository<Customers, Long> {
}
