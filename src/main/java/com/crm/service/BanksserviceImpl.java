package com.crm.service;

import com.crm.model.Banks;
import com.crm.repository.BanksRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Service("banksService")
public class BanksServiceImpl implements BanksService {
    private BanksRepository banksRepository;

    @Transactional(readOnly = true)
    public List<Banks> findAll() {
        return Lists.newArrayList(banksRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Banks findById(Long id) {
        return banksRepository.findOne(id);
    }

    public Banks save(Banks banks) {
        return banksRepository.save(banks);
    }

    public Page<Banks> findAllByPage(Pageable pageable) {
        return banksRepository.findAll(pageable);
    }

    @Autowired
    public void setBanksRepository(BanksRepository banksRepository) {
        this.banksRepository = banksRepository;
    }
}
