package com.crm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.math.BigDecimal;  // А надо ли?
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "products", schema = "public", catalog = "crm")
public class Products implements Serializable {
    private Long id;
    private String name;
    private Measures measures;
    private BigDecimal price;
    public Set<OrderDetails> orderDetails = new HashSet<OrderDetails>();

    @Id
    @GeneratedValue(generator = "products_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "products_seq", sequenceName = "products_id_seq", allocationSize = 1)
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

    @ManyToOne
    @JsonBackReference("measures")
    @JoinColumn(name = "measures_id", nullable = false)
    public Measures getMeasures() {
        return measures;
    }

    public void setMeasures(Measures measures) {
        this.measures = measures;
    }

    @Basic
    @Column(name = "price", nullable = true, precision = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    // Связь с таблицей OrdersDetails
    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("orderDetails")
    public Set<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Set<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
