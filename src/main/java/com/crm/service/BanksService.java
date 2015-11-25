package com.crm.service;

import com.crm.model.Banks;

import java.util.List;

public interface BanksService {
    List<Banks> findAll();

    Banks findByid(Long id);

    Banks save(Banks contact);
}
