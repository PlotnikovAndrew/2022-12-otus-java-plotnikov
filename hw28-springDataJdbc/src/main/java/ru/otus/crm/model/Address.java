package ru.otus.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "address")
public class Address {

    @Id
    private Long id;

    private String street;

    @Column("client_id")
    private Long clientId;

    @PersistenceCreator
    public Address(Long id, String street, Long clientId) {
        this.id = id;
        this.street = street;
        this.clientId = clientId;
    }

    public Address (String street, Long clientId){
        this.id = null;
        this.street = street;
        this.clientId = clientId;
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", clientId=" + clientId +
                '}';
    }
}
