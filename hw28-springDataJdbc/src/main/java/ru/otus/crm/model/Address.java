package ru.otus.crm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "address")
public class Address {
    @Id
    private Long id;

    private String street;


    private Long clientId;

    public Address(Long id, String street, Long clientId) {
        this.id = id;
        this.street = street;
        this.clientId = clientId;
    }
}
