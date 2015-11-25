package com.crm.service;

import com.crm.model.Banks;
import com.crm.repository.BanksRepository;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Service("banksService")
public class BanksserviceImpl implements BanksService {
    private BanksRepository bankRepository;

//    @Override
    @Transactional(readOnly = true)
    public List<Banks> findAll() {
        return Lists.newArrayList(bankRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Banks findByid(Long id) {
        return bankRepository.findOne(id);
    }

    public Banks save(Banks contact) {
        return bankRepository.save(contact) ;
    }
}
