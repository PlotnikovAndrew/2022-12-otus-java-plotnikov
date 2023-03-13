package ru.otus;

public enum Rubles {

    RUB_50(50),
    RUB_100(100),
    RUB_200(200),
    RUB_500(500),
    RUB_1000(1000),
    RUB_2000(2000),
    RUB_5000(5000);

    private final int value;

    Rubles(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
