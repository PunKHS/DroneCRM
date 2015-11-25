package com.crm.model;

import java.io.Serializable;
import java.math.BigDecimal;  // А надо ли?
import javax.persistence.*;

@Entity
@Table(name = "products", schema = "public", catalog = "crm")
public class Products implements Serializable {
    private Integer id;
    private String name;
    private Integer measuresId;
    private BigDecimal price;

    @Id
    @GeneratedValue(generator = "products_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "products_seq", sequenceName = "products_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    @Column(name = "measures_id", nullable = false)
    public int getMeasuresId() {
        return measuresId;
    }

    public void setMeasuresId(int measuresId) {
        this.measuresId = measuresId;
    }

    @Basic
    @Column(name = "price", nullable = true, precision = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
