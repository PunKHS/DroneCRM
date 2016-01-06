package com.crm.service;

import com.crm.model.Contracts;
import com.crm.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContractsService {
    List<Contracts> findAll();

    Contracts findById(Long id);

    Contracts save(Contracts contracts);

    void delete(Long id);

    Page<Contracts> findAllByPage(Pageable pageable);

    List<Orders> getAllOrders();
}
