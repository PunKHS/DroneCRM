package com.crm.model;

import org.hibernate.annotations.Table;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "measures", schema = "public", catalog = "crm")
public class Measures implements Serializable {
    private Integer id;
    private String sname;
    private String name;

    @Id
    @GeneratedValue(generator = "measures_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "measures_seq", sequenceName = "measures_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "sname", nullable = false, length = 5)
    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
