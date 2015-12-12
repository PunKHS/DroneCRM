package com.crm.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "events", schema = "public", catalog = "crm")
public class Events implements Serializable {
    private Long id;
    private String name;
    public Set<CustomerEvents> customerEvents = new HashSet<CustomerEvents>();

    @Id
    @GeneratedValue(generator = "events_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "events_seq", sequenceName = "events_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", unique = true, nullable = false, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Связь с таблицей CustomerEvents
    @OneToMany(mappedBy = "events", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("customerEvents")
    public Set<CustomerEvents> getCustomerEvents() {
        return customerEvents;
    }

    public void setCustomerEvents(Set<CustomerEvents> customerEvents) {
        this.customerEvents = customerEvents;
    }
}
