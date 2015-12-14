package com.crm.service;

import com.crm.model.Contacts;
import com.crm.repository.ContactsRepository;
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
@Service("contactsService")
public class ContactsServiceImpl implements ContactsService {
    private ContactsRepository contactsRepository;

    @Transactional(readOnly = true)
    public List<Contacts> findAll() {
        return Lists.newArrayList(contactsRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Contacts findById(Long id) {
        return contactsRepository.findOne(id);
    }

    public Contacts save(Contacts contacts) {
        return contactsRepository.save(contacts);
    }

    public void delete(Long id) {
        contactsRepository.delete(id);
    }

    public Page<Contacts> findAllByPage(Pageable pageable) {
        return contactsRepository.findAll(pageable);
    }

    @Autowired
    public void setContactsRepository(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }
}
