package ru.otus.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "phones")
public class Phone {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "phone_number")
    private String phoneNumber;
    @ManyToOne(targetEntity = Client.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;

    public Phone(Long id, String phoneNumber) {
        this.id = id;
        this.phoneNumber = phoneNumber;
    }

    public Phone(String phoneNumber) {
        this.id = null;
        this.phoneNumber = phoneNumber;
    }

}
