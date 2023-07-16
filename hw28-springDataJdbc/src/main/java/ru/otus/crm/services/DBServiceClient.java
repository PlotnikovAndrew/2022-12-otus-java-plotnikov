package ru.otus.crm.services;

import ru.otus.crm.model.Client;

import java.util.List;
import java.util.Set;

public interface DBServiceClient {
//    void saveClient(String name, String address, String phoneNumber);
    void saveClient(Client client);
    void saveClient(String name, String address, Set<String> phones);
    List<Client> getAll();
}
