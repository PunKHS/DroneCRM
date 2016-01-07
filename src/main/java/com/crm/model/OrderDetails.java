package com.crm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "order_details", schema = "public", catalog = "crm")
public class OrderDetails implements Serializable {
    private Long id;
    private Orders orders;
    private Products products;
    private Long qty;
    private BigDecimal price; // ?
    private BigDecimal total; // ?

    @Id
    @GeneratedValue(generator = "order_details_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "order_details_seq", sequenceName = "order_details_id_seq", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // Старый вариант
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JsonBackReference("orders")
    @JoinColumn(name = "orders_id", unique = true, nullable = false)
    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    @ManyToOne
    @JsonBackReference("products")
    @JoinColumn(name = "products_id", nullable = false)
    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    @Basic
    @Column(name = "qty", nullable = false)
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Basic
    @Column(name = "price", nullable = true, precision = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "total", nullable = true, precision = 2)
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
