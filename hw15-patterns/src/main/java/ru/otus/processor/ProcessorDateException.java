package ru.otus.processor;

import ru.otus.model.Message;


public class ProcessorDateException implements Processor {

    private final DataTimeProvider dataTimeProvider;

    public ProcessorDateException(DataTimeProvider dataTimeProvider) {
        this.dataTimeProvider = dataTimeProvider;
    }

    @Override
    public Message process(Message message) {
        System.out.println(dataTimeProvider.getData());
        if (dataTimeProvider.getData().getSecond() % 2 == 0) {
            throw new Error("Even second");
        }
        return message;
    }
}
