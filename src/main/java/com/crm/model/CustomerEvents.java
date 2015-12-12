package com.crm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "customer_events", schema = "public", catalog = "crm")
public class CustomerEvents implements Serializable {
    private Long id;
    private Contacts contacts;
    private Events events;
    private Users users;
    private Timestamp date;
    private String note;

    @Id
    @GeneratedValue(generator = "customer_events_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "customer_events_seq", sequenceName = "customer_events_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JsonBackReference("customerEvents")
    @JoinColumn(name = "contacts_id", nullable = false)
    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    @ManyToOne
    @JsonBackReference("customerEvents")
    @JoinColumn(name = "events_id", nullable = false)
    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
        this.events = events;
    }

    @ManyToOne
    @JsonBackReference("measures")
    @JoinColumn(name = "users_id", nullable = false)
    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
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
