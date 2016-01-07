package com.crm.repository;

import com.crm.model.Products;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductsRepository extends PagingAndSortingRepository<Products, Long> {
}
