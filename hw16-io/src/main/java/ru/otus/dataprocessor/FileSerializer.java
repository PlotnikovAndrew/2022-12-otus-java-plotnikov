package ru.otus.dataprocessor;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class FileSerializer implements Serializer {

    public FileSerializer(String fileName) {
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        var gson = new Gson();

        String json = gson.toJson(data);
        System.out.println(json);
        System.out.println(json);
    }

    public static void main(String[] args){
//        var inputDataFileName = "inputData.json";
        var inputDataFileName = "inputData.json";
        Map<String, Double> inputDataFile = new HashMap<>();
        inputDataFile.put("val1", 0.0);
        inputDataFile.put("val2", 1.0);
        inputDataFile.put("val3", 2.0);
        FileSerializer fileSerializer = new FileSerializer(inputDataFileName);
        fileSerializer.serialize(inputDataFile);

    }
}
