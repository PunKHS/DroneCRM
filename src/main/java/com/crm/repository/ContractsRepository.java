package com.crm.repository;

import com.crm.model.Contracts;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContractsRepository extends PagingAndSortingRepository<Contracts, Long> {
}
