package com.crm.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "roles", schema = "public", catalog = "crm")
public class Roles implements Serializable {
    private Long id;
    private String name;
    public Set<Roles> roles = new HashSet<Roles>();

    @Id
    @GeneratedValue(generator = "roles_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "roles_seq", sequenceName = "roles_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    // Связь с таблицей Roles
    @OneToMany(mappedBy = "roles", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("users")
    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }
}
