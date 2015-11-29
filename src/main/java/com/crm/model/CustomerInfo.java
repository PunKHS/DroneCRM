package com.crm.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "customer_info", schema = "public", catalog = "crm")
public class CustomerInfo implements Serializable {
    private Integer customersId;
    private Integer banksId;
    private String account;
    private String bik;
    private String okpo;
    private String adress;

    @Basic
    @Column(name = "customers_id", nullable = false)
    public int getCustomersId() {
        return customersId;
    }

    public void setCustomersId(int customersId) {
        this.customersId = customersId;
    }

    @Basic
    @Column(name = "banks_id", nullable = false)
    public int getBanksId() {
        return banksId;
    }

    public void setBanksId(int banksId) {
        this.banksId = banksId;
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
