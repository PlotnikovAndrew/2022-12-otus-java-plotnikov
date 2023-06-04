package ru.otus.services;

import java.util.Objects;

public class AuthServiceImpl implements AuthService { ;
    private static final String login = "admin";
    private static final String password = "admin";

    public AuthServiceImpl() {
    }

    @Override
    public boolean authenticate(String login, String password) {
        return Objects.equals(AuthServiceImpl.login, login) && Objects.equals(AuthServiceImpl.password, password);
    }

}
