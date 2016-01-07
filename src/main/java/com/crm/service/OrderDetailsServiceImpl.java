package com.crm.service;

import com.crm.model.OrderDetails;
import com.crm.model.Products;
import com.crm.repository.OrderDetailsRepository;
import com.crm.repository.ProductsRepository;
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
@Service("orderDetailsService")
public class OrderDetailsServiceImpl implements OrderDetailsService {
    private OrderDetailsRepository orderDetailsRepository;
    private ProductsRepository productsRepository;

    @Transactional(readOnly = true)
    public List<OrderDetails> findAll() {
        return Lists.newArrayList(orderDetailsRepository.findAll());
    }

    @Transactional(readOnly = true)
    public OrderDetails findById(Long id) {
        return orderDetailsRepository.findOne(id);
    }

    public OrderDetails save(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    public void delete(Long id) {
        orderDetailsRepository.delete(id);
    }

    public Page<OrderDetails> findAllByPage(Pageable pageable) {
        return orderDetailsRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<OrderDetails> findByOrdersId(Long id) {
        return orderDetailsRepository.findByOrdersId(id);
    }

    @Transactional(readOnly = true)
    public List<Products> getAllProducts() {
        return Lists.newArrayList(productsRepository.findAll());
    }

    @Autowired
    public void setOrderDetailsRepository(OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }

    @Autowired
    public void setProductsRepository(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }
}

