package com.crm.service;

import com.crm.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsersService {
    List<Users> findAll();

    Users findById(Long id);

    Users save(Users users);

    void delete(Long id);

    Page<Users> findAllByPage(Pageable pageable);
}
