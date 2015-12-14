package com.crm.repository;

import com.crm.model.Contacts;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContactsRepository extends PagingAndSortingRepository<Contacts, Long> {
}
