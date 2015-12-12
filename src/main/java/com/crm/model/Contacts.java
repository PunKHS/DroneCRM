package com.crm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.*;

@Entity
@Table(name = "contacts", schema = "public", catalog = "crm")
public class Contacts implements Serializable {
    private Long id;
    private Customers customers;
    private String name;
    private String phone;
    private String email;
    private Date bdate;
    private String note;

    @Id
    @GeneratedValue(generator = "contacts_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "contacts_seq", sequenceName = "contacts_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    @ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("contacts")
    @JoinColumn(name = "customers_id", nullable = false)
    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    @Basic
    @Column(name = "name", unique = true, nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "phone", nullable = false, length = 20)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "email", unique = true, nullable = true, length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "bdate", nullable = true)
    public Date getBdate() {
        return bdate;
    }

    public void setBdate(Date bdate) {
        this.bdate = bdate;
    }

    @Basic
    @Column(name = "note", nullable = true, length = 255)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
