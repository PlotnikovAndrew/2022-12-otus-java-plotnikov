package ru.otus.crm.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Getter
@Setter
@ToString
@Table(name = "phones")
public class Phone {

    @Id
    private Long id;

    private String phoneNumber;

    private Long clientId;

    public Phone(Long id, String phoneNumber, Long clientId) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.clientId = clientId;
    }

    public Phone(String phoneNumber) {
        this.id = null;
        this.phoneNumber = phoneNumber;
    }

}
