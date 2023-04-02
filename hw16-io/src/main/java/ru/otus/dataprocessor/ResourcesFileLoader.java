package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.io.*;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() throws IOException {
        try (var bufferedReader = new BufferedReader(new FileReader(this.fileName))){
            System.out.println("text from the file:");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        }
             //читает файл, парсит и возвращает результат
        return null;
    }

    public static void main(String[] args){
        var inputDataFileName = "inputData.json";

        ResourcesFileLoader resourcesFileLoader = new ResourcesFileLoader(inputDataFileName);
        try {
            resourcesFileLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
