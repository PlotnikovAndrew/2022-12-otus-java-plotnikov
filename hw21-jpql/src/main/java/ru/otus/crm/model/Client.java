package ru.otus.crm.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(targetEntity = Address.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Phone> phoneList;

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phoneList){
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneList = phoneList;
        for (Phone phone : this.phoneList){
            phone.setClient(this);
        }
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Client clone() {
        Client newClient = new Client(this.id, this.name);
        if(this.address != null){
            newClient.setAddress(new Address(this.address.getId(), this.address.getStreet()));
        }
        if(this.phoneList != null){
            newClient.setPhoneList(this.getPhoneList().stream()
                    .map(oldPhoneList -> new Phone(oldPhoneList.getId(), oldPhoneList.getPhoneNumber(), newClient))
                    .toList());
        }
        return newClient;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
