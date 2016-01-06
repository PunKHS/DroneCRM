package com.crm.service;

import com.crm.model.Contracts;
import com.crm.model.Orders;
import com.crm.repository.ContractsRepository;
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
@Service("contractsService")
public class ContractsServiceImpl implements ContractsService {
    private ContractsRepository contractsRepository;
    private OrdersRepository ordersRepository;

    @Transactional(readOnly = true)
    public List<Contracts> findAll() {
        return Lists.newArrayList(contractsRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Contracts findById(Long id) {
        return contractsRepository.findOne(id);
    }

    public Contracts save(Contracts contracts) {
        return contractsRepository.save(contracts);
    }

    public void delete(Long id) {
        contractsRepository.delete(id);
    }

    public Page<Contracts> findAllByPage(Pageable pageable) {
        return contractsRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Orders> getAllOrders() {
        return Lists.newArrayList(ordersRepository.findAll());
    }

    @Autowired
    public void setContractsRepository(ContractsRepository contractsRepository) {
        this.contractsRepository = contractsRepository;
    }

    @Autowired
    public void setOrdersRepository(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }
}
