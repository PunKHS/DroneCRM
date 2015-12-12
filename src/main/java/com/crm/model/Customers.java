package com.crm.model;

import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "customers", schema = "public", catalog = "crm")
public class Customers implements Serializable {
    private Long id;
    private String name;
    public Set<Contacts> contacts = new HashSet<Contacts>();

    @Id
    @GeneratedValue(generator = "customers_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "customers_seq", sequenceName = "customers_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @NotEmpty(message = "{validation.name.NotEmpty.message}")
    @Size(min = 1, max = 20, message = "{validation.name.Size.message}")
    @Column(name = "name", unique = true, nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Связь с таблицей Contacts
    @OneToMany(mappedBy = "customers", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("contacts")
    public Set<Contacts> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contacts> contacts) {
        this.contacts = contacts;
    }

    // Добавление нового контактного лица
    public void addContacts(Contacts contacts) {
        contacts.setCustomers(this);
        getContacts().add(contacts);
    }

    // Удаление контакта
    public void removeContacts(Contacts contacts) {
        getContacts().remove(contacts);
    }
}
