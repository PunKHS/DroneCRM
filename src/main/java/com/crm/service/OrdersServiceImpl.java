package com.crm.service;

import com.crm.model.Customers;
import com.crm.model.Orders;
import com.crm.repository.CustomersRepository;
import com.crm.repository.OrdersRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {
    private OrdersRepository ordersRepository;
    private CustomersRepository customersRepository;

    @Transactional(readOnly = true)
    public List<Orders> findAll() {
        return Lists.newArrayList(ordersRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Orders findById(Long id) {
        return ordersRepository.findOne(id);
    }

    public Orders save(Orders orders) {
        return ordersRepository.save(orders);
    }

    public void delete(Long id) {
        ordersRepository.delete(id);
    }

    public Page<Orders> findAllByPage(Pageable pageable) {
        return ordersRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Customers> getAllCustomers() {
        return Lists.newArrayList(customersRepository.findAll());
    }

    @Autowired
    public void setOrdersRepository(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Autowired
    public void setCustomersRepository(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }
}
