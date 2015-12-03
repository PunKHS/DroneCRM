package com.crm.service;

import com.crm.model.Banks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BanksService {
    List<Banks> findAll();

    Banks findById(long id);

    Banks save(Banks banks);

    Page<Banks> findAllByPage(Pageable pageable);
}
