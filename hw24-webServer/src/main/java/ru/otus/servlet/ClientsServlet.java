package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;
import ru.otus.services.DBServiceClient;
import ru.otus.services.TemplateProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;


public class ClientsServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "client.html";

    private final DBServiceClient dbServiceClient;
    private final TemplateProcessor templateProcessor;

    public ClientsServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        List<Client> clients = dbServiceClient.findAll();

        resp.setContentType("text/html");
        resp.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String requestBody = sb.toString();
        Gson gson = new Gson();
        Client newClient = gson.fromJson(requestBody, Client.class);

        dbServiceClient.saveClient(newClient);


//        System.out.println("===================================================================================================");
//        System.out.println(req);
//        System.out.println(newClient);
//        System.out.println(newClient.getAddress());
//        System.out.println(newClient.getPhoneList());
//        System.out.println("===================================================================================================");
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.sendRedirect("/clients");
    }
}
