import java.util.ArrayList;

public class CustomerDatabase {
    private ArrayList<Customer> customers;

    public CustomerDatabase() {
        this.customers = new ArrayList<Customer>();
    }

    // adds an individual customer to the customer arraylist
    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    // takes an array of customers and adds all of them
    public void addCustomers(Customer[] customers) {
        for (int i = 0; i < customers.length; i++) {
            addCustomer(customers[i]);
        }

        System.out.printf("Successfully added %d customers", customers.length);
        
    }

    public void printCustomers() {
        for (int i = 0; i < this.customers.size(); i++) {
            this.customers.get(i).printCustomer();
        }
    }

    public Customer getCustomerByName(String customerName) throws Exception {
        // Null customer has id of 1 which is impossible based on validation in App.java
        Customer nullCustomer = new Customer("null", 1, 1);
        Customer foundCustomer = nullCustomer;
        // iterate over customers to find one that matches the input name
        for (int i = 0; i < this.customers.size(); i++) {
            Customer currentCustomer = this.customers.get(i);
            if (currentCustomer.getCustomerName().equalsIgnoreCase(customerName)) {
                foundCustomer = currentCustomer;
            }
        }

        if (foundCustomer != nullCustomer) {
            return foundCustomer;
        } else {
            throw new Exception("Customer not found");
        }

    }

    public void printCustomersInRange(int min, int max) {
        ArrayList<Customer> customersInRange = getCustomersInRange(min, max);
        if (customersInRange.size() == 0) {
            System.out.println("We couldn't find any customers with total sales in that range.\n");
        } else {

            for (int i = 0; i < customersInRange.size(); i++) {
                Customer currentCustomer = customersInRange.get(i);
                currentCustomer.printCustomer();
            }

        }

    }

    public ArrayList<Customer> getCustomersInRange(int min, int max) {
        ArrayList<Customer> customersInRange = new ArrayList<Customer>();
        for (int i = 0; i < this.customers.size(); i++) {
            Customer currentCustomer = this.customers.get(i);
            int customerSales = currentCustomer.getTotalSales();

            if (customerSales >= min && customerSales <= max) {
                customersInRange.add(currentCustomer);
            }
        }
        return customersInRange;
    }

    public boolean isIdValid(int id) {
        // Validates uniqueness of the id against other customers
        boolean isValid = true;

        for (int i = 0; i < this.customers.size(); i++) {
            // iterate over customers and see if any match the id
            Customer currentCustomer = this.customers.get(i);
            if (currentCustomer.getCustomerId() == id) {
                isValid = false;
            }
        }

        return isValid;
    }
}
