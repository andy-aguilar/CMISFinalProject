import java.util.Scanner;

/**
 * Author: Andres Aguilar
 * Date: 4/25/23
 * This is a combination of three programs:
 * 1. converts cubic feet to bushels,
 * 2. converts miles to kilometers,
 * 3. determines if a student graduated with honors
 * The program takes in user input to determine which program to run
 */

public class App {
    private static Scanner in = new Scanner(System.in);
    private static String MENU_TEXT = "MENU\n1: Add multiple new customers\n2: Add single new customer\n3: Display all customers\n4: Retrieve specific customer's data\n5: Retrieve customers with orders based on range\n9: Exit program\n\nEnter your selection : ";
    private static String NUMBER_OF_CUSTOMERS_TO_ADD_PROMPT = "How many customers would you like to add: ";
    private static String CUSTOMER_NAME_PROMPT = "Please enter the customer's name: ";
    private static String TOTAL_SALES_PROMPT = "Please the total sales for this customer: ";
    private static String CUSTOMER_ID_PROMPT = "Please this customer's customerId: ";
    private static CustomerDatabase CUSTOMER_DB = new CustomerDatabase();

    public static void main(String[] args) throws Exception {
        clearScreen();

        int userInput = 0;

        while (userInput != 9) {
            // getMenuSelection gives the user a menu and validates input so it will always
            // be 1-5 or 9
            userInput = getMenuSelection();
            spacing();

            // Appropriate user input will trigger corresponding methods or exit the program
            switch (userInput) {
                case 1:
                    addMultipleCustomers();
                    break;
                case 2:
                    addSingleCustomer();
                    break;
                case 3:
                    displayAllCustomers();
                    break;
                case 4:
                    retrieveSpecificCustomer();
                    break;
                case 5:
                    retrieveCustomersFromRange();
                    break;
                case 9:
                    System.out.println("Goodbye!");
                    break;
            }
        }

    }

    private static void addMultipleCustomers() {
        int numberOfCustomersToAdd = getInt(NUMBER_OF_CUSTOMERS_TO_ADD_PROMPT);

        Customer[] customers = new Customer[numberOfCustomersToAdd];

        for (int i = 0; i < customers.length; i++) {
            // Print out the customer that is being added
            System.out.println("Customer # " + (i + 1));
            // Create single customer
            Customer customer = getCustomer();
            // Add customer at the current index;
            customers[i] = customer;
            spacing();
        }

        CUSTOMER_DB.addCustomers(customers);
        spacing();
    }

    private static void addSingleCustomer() {
        Customer customer = getCustomer();
        CUSTOMER_DB.addCustomer(customer);
        spacing();
    }

    private static void displayAllCustomers() {
        CUSTOMER_DB.printCustomers();
    }

    private static void retrieveSpecificCustomer() {
        String inputName = getString("What is the name of the customer you would like to retrieve: ");
        System.out.println();
        try {
            Customer foundCustomer = CUSTOMER_DB.getCustomerByName(inputName);
            foundCustomer.printCustomer();
        } catch (Exception e) {
            System.out.println("Sorry. We couldn't find a customer with that name.\n");
        }
    }

    private static void retrieveCustomersFromRange() {
        int min = getInt("Enter the minimum value in the range: ");
        int max = getInt("Enter the maximum value in the range: ");
        System.out.println("");

        CUSTOMER_DB.printCustomersInRange(min, max);
    }

    // USER INPUT METHODS

    private static int getInt(String prompt) {
        System.out.print(prompt);
        while (!in.hasNextInt()) {
            in.nextLine();
            System.out.print("Please enter a valid number: ");
        }
        int input = in.nextInt();
        // nextLIne is her to consume the line entered. This should allow us to avoid
        // this bug elsewhere
        in.nextLine();
        return input;
    }

    private static String getString(String prompt) {
        System.out.print(prompt);
        return in.nextLine();
    }

    private static Customer getCustomer() {
        String customerName = getString(CUSTOMER_NAME_PROMPT);
        int customerId = getValidId();
        int totalSales = getInt(TOTAL_SALES_PROMPT);
        return new Customer(customerName, customerId, totalSales);
    }

    private static int getValidId() {
        System.out.print(CUSTOMER_ID_PROMPT);
        int userInput = 0;

        while (!isValidId(userInput)) {
            // Validate that user inputs a number, then validate that the number is properly
            // 5 digits
            if (!in.hasNextInt()) {
                String input = in.next();

                System.out.printf("\"%s\" is not a valid user id. User ids must be 5 digit numbers: ", input);
            } else {
                userInput = in.nextInt();
                in.nextLine();
                if (!isValidId(userInput)) {
                    System.out.printf(
                            "%d is not a valid user id. User ids must be 5 digit numbers, and must be unique from other customers entered: ",
                            userInput);

                }
            }
        }
        return userInput;
    }

    private static boolean isValidId(int userInput) {
        // validate that userInput is a 5 digit integer and not the same as customers in
        // the database
        // TODO when adding multiple customers this does not validate the IDs against
        // each other.
        return userInput <= 99999 && userInput >= 10000 && CUSTOMER_DB.isIdValid(userInput);
    }

    private static int getMenuSelection() {
        int userInput = 0;
        System.out.print(MENU_TEXT);

        // loop until user has entered a valid input (an int that is either 1-3 or 9)
        while (!isValidMenuSelection(userInput)) {
            if (!in.hasNextInt()) {
                String input = in.next();

                System.out.printf("\"%s\" is not a valid input. Please select a number from the menu: ", input);
            } else {
                userInput = in.nextInt();
                in.nextLine();
                if (!isValidMenuSelection(userInput)) {
                    System.out.printf("%s is not a menu option. Please select a number from the menu: ", userInput);
                }
            }
        }

        return userInput;
    }

    private static boolean isValidMenuSelection(int userInput) {
        // Validation logic
        return userInput == 9 || (userInput >= 1 && userInput <= 5);
    }

    private static void spacing() {
        System.out.println();
        System.out.println();
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
