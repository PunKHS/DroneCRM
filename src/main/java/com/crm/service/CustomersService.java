package com.crm.service;

import com.crm.model.Customers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomersService {
    List<Customers> findAll();

    Customers findById(Long id);

    Customers save(Customers customers);

    void delete(Long id);

    Page<Customers> findAllByPage(Pageable pageable);
}
