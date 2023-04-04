package ru.otus.dataprocessor;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class FileSerializer implements Serializer {
    private String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        TreeMap<String,Double> treeMapData = new TreeMap<>(data);
        try (FileWriter fileWriter = new FileWriter(this.fileName)) {
            new Gson().toJson(treeMapData, fileWriter);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
