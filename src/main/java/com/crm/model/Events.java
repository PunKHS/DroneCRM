package com.crm.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "contracts", schema = "public", catalog = "crm")
//@Table(name = "events", schema = "public", catalog = "crm")
public class Events implements Serializable {
    private Integer id;
    private String name;

    @Id
    @GeneratedValue(generator = "events_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "events_seq", sequenceName = "events_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
