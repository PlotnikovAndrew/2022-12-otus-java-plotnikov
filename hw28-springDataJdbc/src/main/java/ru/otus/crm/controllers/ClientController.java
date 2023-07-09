package ru.otus.crm.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.services.DBServiceClient;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;;

import java.util.HashSet;
import java.util.Set;

@Controller
public class ClientController {

    private final DBServiceClient dbServiceClient;

    public ClientController(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @GetMapping("/client")
    public String getClientsList(Model model) {
        model.addAttribute("clients", dbServiceClient.getAll());
        return "client";
    }

    @PostMapping("/clients/add")
    public String postClient(@RequestParam String name,
                             @RequestParam String address,
                             @RequestParam String phone1,
                             @RequestParam String phone2,
                             @RequestParam String phone3) {

        Set<String> phonesSetString = new HashSet<>();
        phonesSetString.add(phone1);
        if (!phone2.isEmpty()) {
            phonesSetString.add(phone2);
        }
        if (!phone3.isEmpty()) {
            phonesSetString.add(phone3);
        }

        Client newClient = createClient(name, address, phonesSetString);

        dbServiceClient.saveClient(newClient);
        return "client";
    }

    private Client createClient(String name, String address, Set<String> phonesSetString){
        Client client = new Client(name);
        Client createdClientWithoutAddressAndPhones = dbServiceClient.saveClient(client);

        Address addressClient = new Address(null ,address, createdClientWithoutAddressAndPhones.getId());
        createdClientWithoutAddressAndPhones.setAddress(addressClient);

        Set<Phone> phonesSet = new HashSet<>();
        for(String phoneNumber : phonesSetString){
            phonesSet.add(new Phone(null, phoneNumber, createdClientWithoutAddressAndPhones.getId()));
        }
        createdClientWithoutAddressAndPhones.setPhoneSet(phonesSet);

        Client creatdClient = createdClientWithoutAddressAndPhones;

        return creatdClient;
    }
}
