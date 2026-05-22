import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * EmployeeManager handles the main business logic of the Employee Record System.
 *
 * Responsibilities:
 * - Manage employee records using ArrayList and HashMap
 * - Add, update, delete, search, sort, and evaluate employees
 * - Synchronise employee data with text files
 * - Connect GUI/TBI operations with backend logic
 */

public class EmployeeManager {

    private ArrayList<Employee> employees;
    private HashMap<Integer, Employee> employeeMap;
    private FileHandler fileHandler;

    public EmployeeManager() {
        employees = new ArrayList<>();
        employeeMap = new HashMap<>();
        fileHandler = new FileHandler();
    }

    public void loadEmployeesFromFile(String fileName) {
        ArrayList<Employee> loadedEmployees =
                fileHandler.readEmployeesFromFile(fileName);

        employees.clear();
        employeeMap.clear();

        for (Employee emp : loadedEmployees) {
            employees.add(emp);
            employeeMap.put(emp.getEmployeeId(), emp);
        }
    }

    public boolean saveEmployeesToFile(String fileName) {
        return fileHandler.writeEmployeesToFile(fileName, employees);
    }

    public boolean addEmployee(Employee employee, String fileName) {
        if (employee == null || !validateEmployee(employee)) {
            return false;
        }

        if (employeeMap.containsKey(employee.getEmployeeId())) {
            return false;
        }

        employees.add(employee);
        employeeMap.put(employee.getEmployeeId(), employee);

        return saveEmployeesToFile(fileName);
    }

    public Employee searchEmployeeById(int employeeId) {
        if (!InputValidator.isValidEmployeeId(employeeId)) {
            return null;
        }

        return employeeMap.get(employeeId);
    }

    public List<Employee> searchEmployeeByName(String name) {
        ArrayList<Employee> results = new ArrayList<>();

        if (!InputValidator.isValidText(name)) {
            return results;
        }

        String keyword = name.trim().toLowerCase();

        for (Employee emp : employees) {
            if (emp.getName().toLowerCase().contains(keyword)) {
                results.add(emp);
            }
        }

        return results;
    }

    public boolean updateEmployee(int employeeId,
                                  String newName,
                                  String newDepartment,
                                  String newPosition,
                                  double newSalary,
                                  String newPhone,
                                  int extraValue,
                                  String newType,
                                  String fileName) {

        Employee oldEmployee = employeeMap.get(employeeId);

        if (oldEmployee == null) {
            return false;
        }

        if (!InputValidator.isValidName(newName)
                || !InputValidator.isValidText(newDepartment)
                || !InputValidator.isValidText(newPosition)
                || !InputValidator.isValidSalary(newSalary)
                || !InputValidator.isValidPhone(newPhone)
                || !InputValidator.isValidExtraValue(extraValue)) {
            return false;
        }

        Employee updatedEmployee;

        if (newType.equalsIgnoreCase("Full-Time")) {
            updatedEmployee = new FullTimeEmployee(
                    employeeId,
                    newName.trim(),
                    newDepartment.trim(),
                    newPosition.trim(),
                    newSalary,
                    newPhone.trim(),
                    extraValue
            );
        } else {
            updatedEmployee = new PartTimeEmployee(
                    employeeId,
                    newName.trim(),
                    newDepartment.trim(),
                    newPosition.trim(),
                    newSalary,
                    newPhone.trim(),
                    extraValue
            );
        }

        updatedEmployee.setPerformance(oldEmployee.getPerformance());

        int index = employees.indexOf(oldEmployee);

        if (index >= 0) {
            employees.set(index, updatedEmployee);
        }

        employeeMap.put(employeeId, updatedEmployee);

        return saveEmployeesToFile(fileName);
    }

    public boolean updateEmployee(int employeeId,
                                  String newName,
                                  String newDepartment,
                                  String newPosition,
                                  double newSalary,
                                  String newPhone,
                                  int extraValue,
                                  String fileName) {

        Employee existingEmployee = employeeMap.get(employeeId);

        if (existingEmployee == null) {
            return false;
        }

        return updateEmployee(
                employeeId,
                newName,
                newDepartment,
                newPosition,
                newSalary,
                newPhone,
                extraValue,
                existingEmployee.getEmployeeType(),
                fileName
        );
    }

    public boolean deleteEmployee(int employeeId, String fileName) {
        Employee employee = employeeMap.get(employeeId);

        if (employee == null) {
            return false;
        }

        employees.remove(employee);
        employeeMap.remove(employeeId);

        return saveEmployeesToFile(fileName);
    }

    public boolean evaluateEmployee(int employeeId,
                                    boolean positiveFeedback,
                                    String fileName) {

        Employee employee = employeeMap.get(employeeId);

        if (employee == null) {
            return false;
        }

        employee.appraisal(positiveFeedback);

        return saveEmployeesToFile(fileName);
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    public List<Employee> sortByName() {
        return EmployeeAlgorithms.mergeSortByName(
                new ArrayList<>(employees));
    }

    /**
     * Sorts employees by salary in ascending order using Merge Sort.
     */
    public List<Employee> sortBySalaryAscending() {

        return EmployeeAlgorithms.mergeSortBySalaryAscending(
                new ArrayList<>(employees));
    }

    /**
     * Sorts employees by salary in descending order using Merge Sort.
     */
    public List<Employee> sortBySalaryDescending() {

        return EmployeeAlgorithms.mergeSortBySalaryDescending(
                new ArrayList<>(employees));
    }

    public Employee binarySearchById(int employeeId) {
        ArrayList<Employee> sortedEmployees =
                new ArrayList<>(employees);

        sortedEmployees.sort((e1, e2) ->
                Integer.compare(e1.getEmployeeId(), e2.getEmployeeId()));

        return EmployeeAlgorithms.binarySearchById(
                sortedEmployees,
                employeeId
        );
    }

    public boolean saveSearchResults(String fileName,
                                     List<Employee> results) {
        return fileHandler.writeQueryResultsToFile(fileName, results);
    }

    public boolean saveSearchResultsToFile(String fileName,
                                           List<Employee> results) {
        return saveSearchResults(fileName, results);
    }

    private boolean validateEmployee(Employee employee) {
        return InputValidator.isValidEmployeeId(employee.getEmployeeId())
                && InputValidator.isValidName(employee.getName())
                && InputValidator.isValidText(employee.getDepartment())
                && InputValidator.isValidText(employee.getPosition())
                && InputValidator.isValidSalary(employee.getSalary())
                && InputValidator.isValidPhone(employee.getPhone())
                && InputValidator.isValidExtraValue(employee.getExtraValue());
    }

    public void printEmployees(List<Employee> employeeList) {
        if (employeeList == null || employeeList.isEmpty()) {
            System.out.println("No employee records found.");
            return;
        }

        System.out.println("\n====================================================================================================================");
        System.out.printf(
                "%-6s %-18s %-14s %-14s %-10s %-12s %-12s %-12s %-20s %-10s%n",
                "ID", "Name", "Department", "Position", "Salary",
                "Phone", "Type", "Performance", "Status", "Bonus"
        );
        System.out.println("====================================================================================================================");

        for (Employee emp : employeeList) {
            System.out.printf(
                    "%-6d %-18s %-14s %-14s %-10.2f %-12s %-12s %-12.2f %-20s %-10.2f%n",
                    emp.getEmployeeId(),
                    emp.getName(),
                    emp.getDepartment(),
                    emp.getPosition(),
                    emp.getSalary(),
                    emp.getPhone(),
                    emp.getEmployeeType(),
                    emp.getPerformance(),
                    emp.employeeStatus(),
                    emp.calculateBonus()
            );
        }

        System.out.println("====================================================================================================================");
    }

    public void displayEmployeesTable() {
        printEmployees(employees);
    }

    public void displaySingleEmployee(Employee emp) {
        if (emp == null) {
            System.out.println("Employee not found.");
            return;
        }

        ArrayList<Employee> singleEmployee = new ArrayList<>();
        singleEmployee.add(emp);

        printEmployees(singleEmployee);
    }
}