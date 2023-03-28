package ru.otus.processor;

import org.junit.jupiter.api.*;
import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.ListenerPrinterConsole;
import ru.otus.model.Message;

import java.time.LocalDateTime;
import java.util.List;

public class DateExceptionTest {

    @Test
    @DisplayName("Проверка прохождения процессора ProcessorDateException в нечетную секунду")
    void timeTestOddSecond() {
        List<Processor> processors = List.of(new ProcessorDateException(() -> LocalDateTime.now().withSecond(1)),
                new ProcessorDateException(() -> LocalDateTime.now().withSecond(7)),
                new ProcessorDateException(() -> LocalDateTime.now().withSecond(15)));
        var complexProcessor = new ComplexProcessor(processors, ex -> {
        });
        var listenerPrinter = new ListenerPrinterConsole();
        complexProcessor.addListener(listenerPrinter);

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("11")
                .field12("12")
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);
        complexProcessor.removeListener(listenerPrinter);
    }

    @Test
    @DisplayName("Проверка выбрасывания ошибки процессора ProcessorDateException в четную секунду")
    void timeTestEvenSecond() {
        List<Processor> processors = List.of(new ProcessorDateException(() -> LocalDateTime.now().withSecond(2)));
        var complexProcessor = new ComplexProcessor(processors, ex -> {
        });
        var listenerPrinter = new ListenerPrinterConsole();
        complexProcessor.addListener(listenerPrinter);

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("11")
                .field12("12")
                .build();

        boolean isTrue = false;

        try {
            complexProcessor.handle(message);
        } catch (Error e) {
            isTrue = true;
        }

        if (isTrue) {
            System.out.println("Test passed");
        } else {
            throw new Error("Test failed");
        }

        complexProcessor.removeListener(listenerPrinter);
    }
}
