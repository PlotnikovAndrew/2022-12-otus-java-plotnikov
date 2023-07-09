package ru.otus.crm.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Table("client")
public class Client implements Cloneable, Persistable<Long> {

    @Id
    private Long id;

    private String name;

    @MappedCollection(idColumn = "client_id")
    private Address address;

    @MappedCollection(idColumn = "client_id")
    private Set<Phone> phoneSet;

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    @PersistenceCreator
    public Client(Long id, String name, Address address, Set<Phone> phoneSet) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneSet = phoneSet;
        for (Phone phone : this.phoneSet) {
            phone.setClientId(this.getId());
        }
    }

    public Client(Long id, String name, Address address, Phone phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneSet.add(phone);
    }

    private Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getStreetString() {
        return this.address.getStreet();
    }

    public String getPhonesString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Phone phone : this.phoneSet) {
            stringBuilder.append(phone.getPhoneNumber()).append(" ");
        }
        return stringBuilder.toString();
    }

    @Override
    public Client clone() {
        Client newClient = new Client(this.id, this.name);
        if (this.address != null) {
            newClient.setAddress(new Address(this.address.getId(), this.address.getStreet(), this.id));
        }
        if (this.phoneSet != null) {
            newClient.setPhoneSet(this.getPhoneSet().stream().map(oldPhoneList -> new Phone(oldPhoneList.getId(), oldPhoneList.getPhoneNumber(), newClient.getId())).collect(Collectors.toSet()));
        }
        return newClient;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + '\'' + '}';
    }

    @Override
    public boolean isNew() {
        return false;
    }
}
