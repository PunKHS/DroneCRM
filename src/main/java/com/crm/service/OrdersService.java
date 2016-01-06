package com.crm.service;

import com.crm.model.Customers;
import com.crm.model.Orders;
import com.crm.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrdersService {
    List<Orders> findAll();

    Orders findById(Long id);

    Orders save(Orders orders);

    void delete(Long id);

    Page<Orders> findAllByPage(Pageable pageable);

    List<Customers> getAllCustomers();

    List<Users> getAllUsers();
}
