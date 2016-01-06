package com.crm.service;

import com.crm.model.Contacts;
import com.crm.model.Customers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactsService {
    List<Contacts> findAll();

    Contacts findById(Long id);

    Contacts save(Contacts contacts);

    void delete(Long id);

    Page<Contacts> findAllByPage(Pageable pageable);

    List<Customers> getAllCustomers();
}
