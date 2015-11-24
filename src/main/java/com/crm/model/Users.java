package com.crm.model;

import org.hibernate.annotations.Table;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "users", schema = "public", catalog = "crm")
public class Users implements Serializable {
    private Integer id;
    private String name;
    private String pass;
    private String fio;
    private Boolean enable;
    private String email;
    private Integer rolesId;

    @Id
    @GeneratedValue(generator = "users_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "users_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "pass", nullable = false, length = -1)
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Basic
    @Column(name = "fio", nullable = false, length = 100)
    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    @Basic
    @Column(name = "enable", columnDefinition = "boolean default true")
    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "roles_id", nullable = false)
    public int getRolesId() {
        return rolesId;
    }

    public void setRolesId(int rolesId) {
        this.rolesId = rolesId;
    }
}
