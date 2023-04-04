package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessorAggregator implements Processor {


    @Override
    public Map<String, Double> process(List<Measurement> data) {
        Map<String, Double> measurementMap = new HashMap<>();

        for (Measurement measurement : data) {
            if (!measurementMap.containsKey(measurement.getName())) {
                measurementMap.put(measurement.getName(), measurement.getValue());
            } else {
                Double oldValue = measurementMap.get(measurement.getName());
                Double newValue = oldValue + measurement.getValue();
                measurementMap.put(measurement.getName(), newValue);
            }
        }
        System.out.println(measurementMap);
        return measurementMap;
    }
}


