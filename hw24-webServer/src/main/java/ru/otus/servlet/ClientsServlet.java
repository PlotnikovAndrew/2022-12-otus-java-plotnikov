package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.model.Client;
import ru.otus.services.DBServiceClient;
import ru.otus.services.TemplateProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;


public class ClientsServlet extends HttpServlet {

    private static final String CLIENTS_PAGE_TEMPLATE = "client.html";

    private final DBServiceClient dbServiceClient;
    private final TemplateProcessor templateProcessor;
    private final Gson gson;

    public ClientsServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient, Gson gson) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();

        List<Client> clients = dbServiceClient.findAll();
        paramsMap.put("clients", clients);

        resp.setContentType("text/html");
        resp.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuilder sb = new StringBuilder();
        try(BufferedReader reader = req.getReader()){
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String requestBody = sb.toString();

        Client newClient = this.gson.fromJson(requestBody, Client.class);

        dbServiceClient.saveClient(newClient);

        resp.setStatus(HttpServletResponse.SC_CREATED);
    }
}
