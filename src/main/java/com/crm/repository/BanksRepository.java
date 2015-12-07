package com.crm.repository;

import com.crm.model.Banks;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BanksRepository extends PagingAndSortingRepository<Banks, Long> {
}
