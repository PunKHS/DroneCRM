package com.crm.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "customers", schema = "public", catalog = "crm")
public class Customers implements Serializable {
    private Integer id;
    private String name;

    @Id
    @GeneratedValue(generator = "customers_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "customers_seq", sequenceName = "customers_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", unique = true, nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
