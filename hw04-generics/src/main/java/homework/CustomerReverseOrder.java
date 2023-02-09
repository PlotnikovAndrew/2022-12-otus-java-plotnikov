package homework;

import java.util.LinkedList;

public class CustomerReverseOrder {

    private LinkedList<Customer> linkedListCustomer = new LinkedList<>();

    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    public void add(Customer customer) {
        linkedListCustomer.add(customer);
    }

    public Customer take() {
        return linkedListCustomer.pollLast();
    }
}
