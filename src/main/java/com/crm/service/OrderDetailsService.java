package com.crm.service;

import com.crm.model.OrderDetails;
import com.crm.model.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderDetailsService {
    List<OrderDetails> findAll();

    OrderDetails findById(Long id);

    OrderDetails save(OrderDetails orderDetails);

    void delete(Long id);

    Page<OrderDetails> findAllByPage(Pageable pageable);

    List<OrderDetails> findByOrdersId(Long id);

    List<Products> getAllProducts();
}
