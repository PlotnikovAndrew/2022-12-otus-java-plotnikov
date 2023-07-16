package ru.otus.crm.controllers;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.crm.services.DBServiceClient;

import java.util.*;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final DBServiceClient dbServiceClient;
    private final Gson gson;

    public ClientController(DBServiceClient dbServiceClient, Gson gson){
        this.dbServiceClient = dbServiceClient;
        this.gson = gson;
    }

    @GetMapping("")
    public String getClients(Model model) {
        model.addAttribute("clients", dbServiceClient.getAll());
        return "client";
    }
    @PostMapping("/add")
    public String postClient(@RequestBody String json){

        Map<String, String> parsedJson;
        parsedJson = gson.fromJson(json, Map.class);

        if (!parsedJson.containsKey("name") || !parsedJson.containsKey("address") || !parsedJson.containsKey("phoneNumber")){
            throw new RuntimeException("Bad request!");
        }

        Set<String> phoneSet = new HashSet<>();
        phoneSet.add(parsedJson.get("phoneNumber"));
        if(parsedJson.get("phoneNumberExtraFirst") != ""){
            phoneSet.add(parsedJson.get("phoneNumberExtraFirst"));
        }
        if(parsedJson.get("phoneNumberExtraFirst") != null){
            phoneSet.add(parsedJson.get("phoneNumberExtraSecond"));
        }

        dbServiceClient.saveClient(parsedJson.get("name"), parsedJson.get("address"), phoneSet);

        return "client";
    }
}
