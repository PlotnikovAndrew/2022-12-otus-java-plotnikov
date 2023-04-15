package ru.otus.dataprocessor;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import ru.otus.model.Measurement;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() throws IOException {
        List<Measurement> measurementList;

        try (FileReader reader = new FileReader(Objects.requireNonNull(ResourcesFileLoader.class.getClassLoader().getResource(this.fileName)).getPath())) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Measurement>>() {
            }.getType();
            measurementList = gson.fromJson(reader, type);
            System.out.println(measurementList);
        }
        return measurementList;
    }
}
