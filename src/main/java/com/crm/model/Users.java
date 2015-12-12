package com.crm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "users", schema = "public", catalog = "crm")
public class Users implements Serializable {
    private Long id;
    private String name;
    private String pass;
    private String fio;
    private Boolean enable;
    private String email;
    private Roles roles;
    public Set<CustomerEvents> customerEvents = new HashSet<CustomerEvents>();
    public Set<Orders> orders = new HashSet<Orders>();

    @Id
    @GeneratedValue(generator = "users_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "users_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", unique = true, nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "pass", nullable = false, length = -1)
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Basic
    @Column(name = "fio", nullable = false, length = 100)
    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    @Basic
    @Column(name = "enable", columnDefinition = "boolean default true")
    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Basic
    @Column(name = "email", unique = true, nullable = true, length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToOne
    @JsonBackReference("users")
    @JoinColumn(name = "roles_id", nullable = false)
    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    // Связь с таблицей CustomerEvents
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("customerEvents")
    public Set<CustomerEvents> getCustomerEvents() {
        return customerEvents;
    }

    public void setCustomerEvents(Set<CustomerEvents> customerEvents) {
        this.customerEvents = customerEvents;
    }

    // Связь с таблицей Orders
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("orders")
    public Set<Orders> getOrders() {
        return orders;
    }

    public void setOrders(Set<Orders> orders) {
        this.orders = orders;
    }
}
