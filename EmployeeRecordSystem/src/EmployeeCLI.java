import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * EmployeeCLI provides the Text-Based Interface for the Employee Record System.
 *
 * Features:
 * - Add, view, update, delete, search, sort, evaluate, and save employee records
 * - Linear Search by Name
 * - HashMap Search by ID
 * - Binary Search by ID
 * - Merge Sort by Name
 * - Merge Sort by Salary Ascending and Descending
 * - Exception handling for invalid numeric input
 */
public class EmployeeCLI {

    private static final String FILE_NAME = "employees.txt";
    private static final String QUERY_FILE = "query_results.txt";

    public static void main(String[] args) {
        runCLI();
    }

    /**
     * Runs the main Text-Based Interface menu.
     */
    public static void runCLI() {
        Scanner scanner = new Scanner(System.in);
        EmployeeManager manager = new EmployeeManager();

        manager.loadEmployeesFromFile(FILE_NAME);

        int choice = 0;

        do {
            showMenu(manager);

            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addEmployee(scanner, manager);
                        break;
                    case 2:
                        viewEmployees(manager);
                        break;
                    case 3:
                        searchEmployeeByName(scanner, manager);
                        break;
                    case 4:
                        searchEmployeeById(scanner, manager);
                        break;
                    case 5:
                        binarySearchEmployeeById(scanner, manager);
                        break;
                    case 6:
                        sortByName(manager);
                        break;
                    case 7:
                        sortBySalaryAscending(manager);
                        break;
                    case 8:
                        sortBySalaryDescending(manager);
                        break;
                    case 9:
                        updateEmployee(scanner, manager);
                        break;
                    case 10:
                        deleteEmployee(scanner, manager);
                        break;
                    case 11:
                        evaluateEmployee(scanner, manager);
                        break;
                    case 12:
                        saveEmployees(manager);
                        break;
                    case 13:
                        System.out.println("Exiting Text-Based Interface. Goodbye.");
                        break;
                    default:
                        System.out.println("Error: Invalid choice. Please select between 1 and 13.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid numeric menu choice.");
            }

            System.out.println();

        } while (choice != 13);

        scanner.close();
    }

    /**
     * Displays the TBI menu.
     */
    public static void showMenu(EmployeeManager manager) {
        System.out.println("\n==============================================================");
        System.out.println("           EMPLOYEE RECORD MANAGEMENT SYSTEM - TBI");
        System.out.println("==============================================================");
        System.out.println(" Total Employees : " + manager.getAllEmployees().size());
        System.out.println(" Data Structures : ArrayList + HashMap");
        System.out.println(" Algorithms      : Linear Search, Binary Search, Merge Sort");
        System.out.println("==============================================================");
        System.out.println(" 1.  Add Employee");
        System.out.println(" 2.  View Employees");
        System.out.println("--------------------------------------------------------------");
        System.out.println(" SEARCHING METHODS");
        System.out.println(" 3.  Search Employee by Name (Linear Search)");
        System.out.println(" 4.  Search Employee by ID (HashMap Search)");
        System.out.println(" 5.  Search Employee by ID (Binary Search)");
        System.out.println("--------------------------------------------------------------");
        System.out.println(" SORTING METHODS");
        System.out.println(" 6.  Sort Employees by Name (Merge Sort)");
        System.out.println(" 7.  Sort Employees by Salary Ascending (Merge Sort)");
        System.out.println(" 8.  Sort Employees by Salary Descending (Merge Sort)");
        System.out.println("--------------------------------------------------------------");
        System.out.println(" EMPLOYEE MANAGEMENT");
        System.out.println(" 9.  Update Employee");
        System.out.println("10.  Delete Employee");
        System.out.println("11.  Evaluate Employee");
        System.out.println("12.  Save Employee Records");
        System.out.println("13.  Exit System");
        System.out.println("==============================================================");
        System.out.print("Enter your choice: ");
    }

    /**
     * Adds a new employee record.
     */
    public static void addEmployee(Scanner scanner, EmployeeManager manager) {
        try {
            System.out.print("Enter Employee ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Department: ");
            String department = scanner.nextLine();

            System.out.print("Enter Position: ");
            String position = scanner.nextLine();

            System.out.print("Enter Salary: ");
            double salary = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter Phone: ");
            String phone = scanner.nextLine();

            System.out.print("Enter Employee Type (1 = Full-Time, 2 = Part-Time): ");
            int typeChoice = Integer.parseInt(scanner.nextLine());

            Employee employee;

            if (typeChoice == 1) {
                System.out.print("Enter Annual Leave Days: ");
                int annualLeaveDays = Integer.parseInt(scanner.nextLine());

                employee = new FullTimeEmployee(
                        id, name, department, position, salary, phone, annualLeaveDays);

            } else if (typeChoice == 2) {
                System.out.print("Enter Hours Worked: ");
                int hoursWorked = Integer.parseInt(scanner.nextLine());

                employee = new PartTimeEmployee(
                        id, name, department, position, salary, phone, hoursWorked);

            } else {
                System.out.println("Error: Invalid employee type selected.");
                return;
            }

            if (manager.addEmployee(employee, FILE_NAME)) {
                System.out.println("Employee added successfully and saved automatically.");
            } else {
                System.out.println("Error: Employee could not be added. Check validation or duplicate ID.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid numeric input. Please try again.");
        }
    }

    /**
     * Displays all employee records.
     */
    public static void viewEmployees(EmployeeManager manager) {
        manager.printEmployees(manager.getAllEmployees());
    }

    /**
     * Searches employees by name using Linear Search.
     */
    public static void searchEmployeeByName(Scanner scanner, EmployeeManager manager) {
        System.out.print("Enter employee name keyword: ");
        String name = scanner.nextLine();

        List<Employee> results = manager.searchEmployeeByName(name);

        if (results.isEmpty()) {
            System.out.println("No matching employee records found.");
        } else {
            manager.printEmployees(results);
            manager.saveSearchResults(QUERY_FILE, results);
            System.out.println("Search results saved to " + QUERY_FILE);
        }
    }

    /**
     * Searches employee by ID using HashMap search.
     */
    public static void searchEmployeeById(Scanner scanner, EmployeeManager manager) {
        try {
            System.out.print("Enter Employee ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            Employee employee = manager.searchEmployeeById(id);

            if (employee != null) {
                manager.printEmployees(singleEmployeeList(employee));
            } else {
                System.out.println("Employee not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid employee ID.");
        }
    }

    /**
     * Searches employee by ID using Binary Search.
     */
    public static void binarySearchEmployeeById(Scanner scanner, EmployeeManager manager) {
        try {
            System.out.print("Enter Employee ID for Binary Search: ");
            int id = Integer.parseInt(scanner.nextLine());

            Employee employee = manager.binarySearchById(id);

            if (employee != null) {
                manager.printEmployees(singleEmployeeList(employee));
            } else {
                System.out.println("Employee not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid employee ID.");
        }
    }

    /**
     * Updates employee details and allows employee type conversion.
     */
    public static void updateEmployee(Scanner scanner, EmployeeManager manager) {
        try {
            System.out.print("Enter Employee ID to update: ");
            int id = Integer.parseInt(scanner.nextLine());

            Employee existingEmployee = manager.searchEmployeeById(id);

            if (existingEmployee == null) {
                System.out.println("Employee not found.");
                return;
            }

            System.out.println("Current Employee Type: " + existingEmployee.getEmployeeType());
            System.out.print("Enter New Employee Type (1 = Full-Time, 2 = Part-Time): ");
            int typeChoice = Integer.parseInt(scanner.nextLine());

            String newType;

            if (typeChoice == 1) {
                newType = "Full-Time";
            } else if (typeChoice == 2) {
                newType = "Part-Time";
            } else {
                System.out.println("Error: Invalid employee type selected.");
                return;
            }

            System.out.print("Enter New Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter New Department: ");
            String department = scanner.nextLine();

            System.out.print("Enter New Position: ");
            String position = scanner.nextLine();

            System.out.print("Enter New Salary: ");
            double salary = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter New Phone: ");
            String phone = scanner.nextLine();

            if (newType.equals("Full-Time")) {
                System.out.print("Enter New Annual Leave Days: ");
            } else {
                System.out.print("Enter New Hours Worked: ");
            }

            int extraValue = Integer.parseInt(scanner.nextLine());

            boolean updated = manager.updateEmployee(
                    id,
                    name,
                    department,
                    position,
                    salary,
                    phone,
                    extraValue,
                    newType,
                    FILE_NAME
            );

            if (updated) {
                System.out.println("Employee record updated successfully and saved automatically.");
            } else {
                System.out.println("Error: Employee record could not be updated.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid numeric input. Please try again.");
        }
    }

    /**
     * Deletes an employee record.
     */
    public static void deleteEmployee(Scanner scanner, EmployeeManager manager) {
        try {
            System.out.print("Enter Employee ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (manager.deleteEmployee(id, FILE_NAME)) {
                System.out.println("Employee record deleted successfully and saved automatically.");
            } else {
                System.out.println("Employee not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid employee ID.");
        }
    }

    /**
     * Updates employee performance through positive or negative feedback.
     */
    public static void evaluateEmployee(Scanner scanner, EmployeeManager manager) {
        try {
            System.out.print("Enter Employee ID to evaluate: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Was the feedback positive? (yes/no): ");
            String feedback = scanner.nextLine();

            boolean positiveFeedback = feedback.equalsIgnoreCase("yes");

            if (manager.evaluateEmployee(id, positiveFeedback, FILE_NAME)) {
                System.out.println("Evaluation saved successfully.");
            } else {
                System.out.println("Employee not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid employee ID.");
        }
    }

    /**
     * Sorts employees by name using Merge Sort.
     */
    public static void sortByName(EmployeeManager manager) {
        List<Employee> sorted = manager.sortByName();

        System.out.println("Employees sorted by name using Merge Sort:");
        manager.printEmployees(sorted);
    }

    /**
     * Sorts employees by salary in ascending order using Merge Sort.
     */
    public static void sortBySalaryAscending(EmployeeManager manager) {
        List<Employee> sorted = manager.sortBySalaryAscending();

        System.out.println("Employees sorted by salary ascending using Merge Sort:");
        manager.printEmployees(sorted);
    }

    /**
     * Sorts employees by salary in descending order using Merge Sort.
     */
    public static void sortBySalaryDescending(EmployeeManager manager) {
        List<Employee> sorted = manager.sortBySalaryDescending();

        System.out.println("Employees sorted by salary descending using Merge Sort:");
        manager.printEmployees(sorted);
    }

    /**
     * Saves employee records manually.
     */
    public static void saveEmployees(EmployeeManager manager) {
        if (manager.saveEmployeesToFile(FILE_NAME)) {
            System.out.println("All employee records saved successfully.");
        } else {
            System.out.println("Error: Employee records could not be saved.");
        }
    }

    /**
     * Converts a single employee into a list for table printing.
     */
    private static List<Employee> singleEmployeeList(Employee employee) {
        ArrayList<Employee> list = new ArrayList<>();
        list.add(employee);
        return list;
    }
}