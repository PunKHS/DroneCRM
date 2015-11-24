package com.crm.model;

import org.hibernate.annotations.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "customer_events", schema = "public", catalog = "crm")
public class CustomerEvents implements Serializable {
    private Integer id;
    private Integer contactsId;
    private Integer eventsId;
    private Integer usersId;
    private Timestamp date;
    private String note;

    @Id
    @GeneratedValue(generator = "customer_events_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "customer_events_seq", sequenceName = "customer_events_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    public id getId() {
        return id;
    }

    public void setId(id id) {
        this.id = id;
    }

    @Basic
    @Column(name = "contacts_id", nullable = false)
    public id getContactsId() {
        return contactsId;
    }

    public void setContactsId(id contactsId) {
        this.contactsId = contactsId;
    }

    @Basic
    @Column(name = "events_id", nullable = false)
    public id getEventsId() {
        return eventsId;
    }

    public void setEventsId(id eventsId) {
        this.eventsId = eventsId;
    }

    @Basic
    @Column(name = "users_id", nullable = false)
    public id getUsersId() {
        return usersId;
    }

    public void setUsersId(id usersId) {
        this.usersId = usersId;
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
