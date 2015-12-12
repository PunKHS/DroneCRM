package com.crm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "orders", schema = "public", catalog = "crm")
public class Orders implements Serializable {
    private Long id;
    private Timestamp date; // ?
    private String number;
    private Customers customers;
    private Users users;
    private BigDecimal total; // ?
    private String note;
    public Set<OrderDetails> orderDetails = new HashSet<OrderDetails>();
    public Set<Contracts> contracts = new HashSet<Contracts>();

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date", unique = true, nullable = true)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "number", unique = true, nullable = true, length = 20)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @ManyToOne
    @JsonBackReference("orders")
    @JoinColumn(name = "customers_id", nullable = false)
    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    @ManyToOne
    @JsonBackReference("orders")
    @JoinColumn(name = "users_id", nullable = false)
    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Basic
    @Column(name = "total", nullable = true, precision = 2)
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Basic
    @Column(name = "note", nullable = true, length = 255)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // Связь с таблицей OrdersDetails
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("orderDetails")
    public Set<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Set<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    // Связь с таблицей Contracts
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("contracts")
    public Set<Contracts> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contracts> contracts) {
        this.contracts = contracts;
    }
}
