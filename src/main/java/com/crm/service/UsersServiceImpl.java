package com.crm.service;

import com.crm.model.Users;
import com.crm.repository.UsersRepository;
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
@Service("usersService")
public class UsersServiceImpl implements UsersService {
    private UsersRepository usersRepository;

    @Transactional(readOnly = true)
    public List<Users> findAll() {
        return Lists.newArrayList(usersRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Users findById(Long id) {
        return usersRepository.findOne(id);
    }

    public Users save(Users users) {
        return usersRepository.save(users);
    }

    public void delete(Long id) {
        usersRepository.delete(id);
    }

    public Page<Users> findAllByPage(Pageable pageable) {
        return usersRepository.findAll(pageable);
    }

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
}
