package ru.otus.crm.services;

import org.springframework.stereotype.Service;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.repository.AddressRepository;
import ru.otus.crm.repository.ClientRepository;
import ru.otus.crm.repository.PhoneRepository;
import ru.otus.sessionmanager.TransactionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DBServiceClientImpl implements DBServiceClient{

    private final ClientRepository clientRepository;
    private final  AddressRepository addressRepository;
    private final  PhoneRepository phoneRepository;
    private final TransactionManager transactionManager;


    public DBServiceClientImpl(ClientRepository clientRepository,
                               AddressRepository addressRepository,
                               PhoneRepository phoneRepository,
                               TransactionManager transactionManager){
        this.clientRepository = clientRepository;
        this.addressRepository = addressRepository;
        this.phoneRepository = phoneRepository;
        this.transactionManager = transactionManager;
    }


        @Override
    public void saveClient(Client client) {
        transactionManager.doInTransaction(() ->{
            return this.clientRepository.save(client);
        });
    }

    @Override
    public List<Client> getAll() {
        List<Client> list = new ArrayList<>();
        clientRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public void saveClient(String name, String address, Set<String> phones) {
        transactionManager.doInTransaction(() -> {
            Client client = this.clientRepository.save(new Client(name));
            this.addressRepository.save(new Address(address, client.getId()));
            for (String phone: phones){
                this.phoneRepository.save(new Phone(phone, client.getId()));
            }
            return client;
        });
    }
    }
