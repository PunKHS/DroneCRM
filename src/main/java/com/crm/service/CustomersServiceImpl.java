package com.crm.service;

import com.crm.model.Banks;
import com.crm.model.Customers;
import com.crm.repository.BanksRepository;
import com.crm.repository.CustomersRepository;
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
@Service("customersService")
public class CustomersServiceImpl implements CustomersService {
    private CustomersRepository customersRepository;

    @Transactional(readOnly = true)
    public List<Customers> findAll() {
        return Lists.newArrayList(customersRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Customers findById(Long id) {
        return customersRepository.findOne(id);
    }

    public Customers save(Customers customers) {
        return customersRepository.save(customers);
    }

    public Page<Customers> findAllByPage(Pageable pageable) {
        return customersRepository.findAll(pageable);
    }

    @Autowired
    public void setCustomersRepository(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }
}
