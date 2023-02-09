package homework;


import java.util.*;

import static java.util.Objects.isNull;

public class CustomerService {

    private final NavigableMap<Customer, String> TREE_MAP = new TreeMap<>(new Comparator<Customer>() {
        @Override
        public int compare(Customer o1, Customer o2) {
            Long scores1 = o1.getScores();
            Long scores2 = o2.getScores();
            return scores1.compareTo(scores2);
        }
    });

    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        Customer customerNew = TREE_MAP.firstKey();
        String stringValue = TREE_MAP.get(customerNew);
        Customer customerClone;

        try {
            customerClone = (Customer) customerNew.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        return new AbstractMap.SimpleEntry<>(customerClone, stringValue);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Customer customerNew = TREE_MAP.higherKey(customer);
        if (isNull(customerNew)) {
            return null;
        }
        String stringValue = TREE_MAP.get(customerNew);
        Customer customerClone;

        try {
            customerClone = (Customer) customerNew.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        return Map.entry(customerClone, stringValue);
    }

    public void add(Customer customer, String data) {
        TREE_MAP.put(customer, data);
    }
}
