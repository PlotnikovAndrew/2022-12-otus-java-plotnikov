package ru.otus.cachehw;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    private Map<K, V> weakHashMap = new WeakHashMap<>();
    private List<HwListener<K, V>> listenerList = new ArrayList<>();
//Надо реализовать эти методы

    @Override
    public void put(K key, V value) {
        weakHashMap.put(key, value);
        notify(key, value, "PUT");
    }

    @Override
    public void remove(K key) {
        V value = this.weakHashMap.remove(key);
        if (value != null){
            notify(key, value, "REMOVE");
        }
    }

    @Override
    public V get(K key) {
        V value = this.weakHashMap.get(key);
        if (value != null){
            notify(key, value, "GET");
        }
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listenerList.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listenerList.remove(listener);
    }

    private void notify(K key, V value, String action) {
        this.listenerList.forEach(listener -> {
            if (listener != null) {
                try {
                    listener.notify(key, value, action);
                } catch (Exception e) {
                    throw new RuntimeException("");
                }
            }
        });
    }
}