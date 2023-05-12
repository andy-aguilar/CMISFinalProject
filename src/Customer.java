public class Customer {
    private String customerName;
    private int customerId;
    private int totalSales;

    public Customer(String customerName, int customerId, int totalSales) {
        this.customerName = customerName;
        this.customerId = customerId;
        this.totalSales = totalSales;
    }


    public String getCustomerName() {
        return this.customerName;
    }
    public int getCustomerId() {
        return this.customerId;
    }
    public int getTotalSales() {
        return this.totalSales;
    }

    public void printCustomer() {
        System.out.printf("CUSTOMER INFO\nName: %s\nId: %s\nTotal Sales: %s\n\n", this.customerName, this.customerId, this.totalSales);
    }
}
