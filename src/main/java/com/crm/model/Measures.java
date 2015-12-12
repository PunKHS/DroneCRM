package com.crm.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "measures", schema = "public", catalog = "crm")
public class Measures implements Serializable {
    private Long id;
    private String sname;
    private String name;
    public Set<Products> products = new HashSet<Products>();

    @Id
    @GeneratedValue(generator = "measures_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "measures_seq", sequenceName = "measures_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "sname", unique = true, nullable = false, length = 5)
    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @Basic
    @Column(name = "name", unique = true, nullable = false, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Связь с таблицей OrdersDetails
    @OneToMany(mappedBy = "measures", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("products")
    public Set<Products> getProducts() {
        return products;
    }

    public void setProducts(Set<Products> products) {
        this.products = products;
    }
}
