import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployeeManagerJUnitTest {

    private EmployeeManager manager;
    private static final String TEST_FILE = "test.txt";

    @BeforeEach
    void setup() throws Exception {

        // Clear test file before each test so tests are repeatable.
        PrintWriter writer = new PrintWriter(TEST_FILE);
        writer.close();

        manager = new EmployeeManager();

        manager.addEmployee(new FullTimeEmployee(
                101, "Abhilesh Mandal", "IT", "Developer",
                80000, "0490804933", 20), TEST_FILE);

        manager.addEmployee(new PartTimeEmployee(
                102, "Khushi Sharma", "HR", "Assistant",
                40000, "0411223344", 25), TEST_FILE);

        manager.addEmployee(new FullTimeEmployee(
                103, "Numan Javed", "Finance", "Manager",
                95000, "0422334455", 18), TEST_FILE);
    }

    @Test
    void testSearchEmployeeById() {
        Employee employee = manager.searchEmployeeById(101);

        assertNotNull(employee);
        assertEquals("Abhilesh Mandal", employee.getName());
    }

    @Test
    void testSearchEmployeeByName() {
        List<Employee> results = manager.searchEmployeeByName("Khushi");

        assertEquals(1, results.size());
        assertEquals("Khushi Sharma", results.get(0).getName());
    }

    @Test
    void testBinarySearchById() {
        Employee employee = manager.binarySearchById(103);

        assertNotNull(employee);
        assertEquals("Numan Javed", employee.getName());
    }

    @Test
    void testSearchEmployeeByInvalidIdReturnsNull() {
        Employee employee = manager.searchEmployeeById(-1);

        assertNull(employee);
    }

    @Test
    void testSearchEmployeeByNameNotFound() {
        List<Employee> results = manager.searchEmployeeByName("Unknown");

        assertTrue(results.isEmpty());
    }

    @Test
    void testSortByName() {
        List<Employee> sorted = manager.sortByName();

        assertEquals("Abhilesh Mandal", sorted.get(0).getName());
        assertEquals("Khushi Sharma", sorted.get(1).getName());
        assertEquals("Numan Javed", sorted.get(2).getName());
    }

    @Test
    void testSortBySalaryAscending() {
        List<Employee> sorted = manager.sortBySalaryAscending();

        assertEquals(40000.0, sorted.get(0).getSalary(), 0.01);
        assertEquals(80000.0, sorted.get(1).getSalary(), 0.01);
        assertEquals(95000.0, sorted.get(2).getSalary(), 0.01);
    }

    @Test
    void testSortBySalaryDescending() {
        List<Employee> sorted = manager.sortBySalaryDescending();

        assertEquals(95000.0, sorted.get(0).getSalary(), 0.01);
        assertEquals(80000.0, sorted.get(1).getSalary(), 0.01);
        assertEquals(40000.0, sorted.get(2).getSalary(), 0.01);
    }

    @Test
    void testAddDuplicateEmployeeId() {
        boolean added = manager.addEmployee(new FullTimeEmployee(
                101, "Duplicate User", "IT", "Tester",
                50000, "0400000000", 10), TEST_FILE);

        assertFalse(added);
    }

    @Test
    void testAddInvalidEmployeeSalary() {
        boolean added = manager.addEmployee(new FullTimeEmployee(
                104, "Invalid Salary", "IT", "Tester",
                -5000, "0400000000", 10), TEST_FILE);

        assertFalse(added);
    }

    @Test
    void testUpdateEmployeeDetails() {
        boolean updated = manager.updateEmployee(
                101,
                "Abhilesh Mandal",
                "Cybersecurity",
                "Security Analyst",
                90000,
                "0490804933",
                22,
                "Full-Time",
                TEST_FILE
        );

        Employee employee = manager.searchEmployeeById(101);

        assertTrue(updated);
        assertEquals("Cybersecurity", employee.getDepartment());
        assertEquals("Security Analyst", employee.getPosition());
        assertEquals(90000.0, employee.getSalary(), 0.01);
        assertEquals(22, employee.getExtraValue());
    }

    @Test
    void testUpdateEmployeeTypeConversion() {
        boolean updated = manager.updateEmployee(
                101,
                "Abhilesh Mandal",
                "IT",
                "Developer",
                80000,
                "0490804933",
                35,
                "Part-Time",
                TEST_FILE
        );

        Employee employee = manager.searchEmployeeById(101);

        assertTrue(updated);
        assertEquals("Part-Time", employee.getEmployeeType());
        assertEquals(35, employee.getExtraValue());
    }

    @Test
    void testDeleteEmployee() {
        boolean deleted = manager.deleteEmployee(102, TEST_FILE);

        Employee employee = manager.searchEmployeeById(102);

        assertTrue(deleted);
        assertNull(employee);
    }

    @Test
    void testDeleteNonExistingEmployee() {
        boolean deleted = manager.deleteEmployee(999, TEST_FILE);

        assertFalse(deleted);
    }

    @Test
    void testEvaluateEmployeePositiveFeedback() {
        Employee before = manager.searchEmployeeById(101);
        double oldPerformance = before.getPerformance();

        boolean evaluated = manager.evaluateEmployee(101, true, TEST_FILE);

        Employee after = manager.searchEmployeeById(101);

        assertTrue(evaluated);
        assertTrue(after.getPerformance() > oldPerformance);
    }

    @Test
    void testEvaluateNonExistingEmployee() {
        boolean evaluated = manager.evaluateEmployee(999, true, TEST_FILE);

        assertFalse(evaluated);
    }
}