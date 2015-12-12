package com.crm.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "banks", schema = "public", catalog = "crm")
public class Banks implements Serializable {
    private Long id;
    private String name;
    public Set<CustomerInfo> customerInfo = new HashSet<CustomerInfo>();

    @Id
    @GeneratedValue(generator = "banks_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "banks_seq", sequenceName = "banks_id_seq", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // Старый вариант
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @NotEmpty(message = "{validation.name.NotEmpty.message}")
    @Size(min = 1, max = 20, message = "{validation.name.Size.message}")
    @Column(name = "name", unique = true, nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Связь с таблицей CustomerInfo
    @OneToMany(mappedBy = "banks", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("customerInfo")
    public Set<CustomerInfo> getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(Set<CustomerInfo> customerInfo) {
        this.customerInfo = customerInfo;
    }
}