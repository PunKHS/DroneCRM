package com.crm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "customer_info", schema = "public", catalog = "crm")
public class CustomerInfo implements Serializable {
    private Customers customers;
    private Banks banks;
    private String account;
    private String bik;
    private String okpo;
    private String adress;

    @ManyToOne
    @JsonBackReference("customerInfo")
    @JoinColumn(name = "customers_id", nullable = false)
    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    @ManyToOne
    @JsonBackReference("customerInfo")
    @JoinColumn(name = "banks_id", nullable = false)
    public Banks getBanks() {
        return banks;
    }

    public void setBanks(Banks banks) {
        this.banks = banks;
    }

    @Id
    @Column(name = "account", unique = true, nullable = false, length = 20)
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Basic
    @Column(name = "bik", nullable = false, length = 9)
    public String getBik() {
        return bik;
    }

    public void setBik(String bik) {
        this.bik = bik;
    }

    @Basic
    @Column(name = "okpo", nullable = false, length = 13)
    public String getOkpo() {
        return okpo;
    }

    public void setOkpo(String okpo) {
        this.okpo = okpo;
    }

    @Basic
    @Column(name = "adress", nullable = false, length = 255)
    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
