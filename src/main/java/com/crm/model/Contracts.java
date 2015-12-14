package com.crm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.*;

@Entity
@Table(name = "contracts", schema = "public", catalog = "crm")
public class Contracts implements Serializable {
    private Long id;
    private Date date; //?
    private String number;
    private Orders orders;
    private String note;
//    private Serializable isSign;

    @Id
    @GeneratedValue(generator = "contracts_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "contracts_seq", sequenceName = "contracts_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "number", unique = true, nullable = false, length = 20)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    @Basic
    @Column(name = "note", nullable = true, length = 255)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

//    @Basic
//    @Column(name = "is_sign", nullable = false)
//    public Serializable getIsSign() {
//        return isSign;
//    }
//
//    public void setIsSign(Serializable isSign) {
//        this.isSign = isSign;
//    }
}
