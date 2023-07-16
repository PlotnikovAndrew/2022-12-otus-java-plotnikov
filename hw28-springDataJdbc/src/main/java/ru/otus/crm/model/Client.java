package ru.otus.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Arrays;
import java.util.Set;

@Table("client")
public class Client {

    @Id
    private Long id;
    private String name;
    @MappedCollection(idColumn = "id", keyColumn = "id")
    private Set<Phone> numbers;
    @MappedCollection(idColumn = "id", keyColumn = "id")
    private Address address;

    @PersistenceCreator
    public Client(Long id, String name, Set<Phone> numbers, Address address){
        this.id = id;
        this.name = name;
        this.numbers = numbers;
        this.address = address;
    }

    public Client(String name, Set<Phone> numbers, Address address){
        this.id = null;
        this.name = name;
        this.numbers = numbers;
        this.address = address;
    }

    public Client(String name){
        this.id = null;
        this.name = name;
        this.numbers = null;
        this.address = null;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Phone> getNumbers() {
        return numbers;
    }
    public String getNumbersString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Phone phone : numbers){
            stringBuilder.append(phone.getNumber());
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    public Address getAddress() {
        return address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumbers(Set<Phone> numbers) {
        this.numbers = numbers;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
