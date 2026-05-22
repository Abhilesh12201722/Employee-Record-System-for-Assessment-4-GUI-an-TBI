/**
 * Abstract base class for all employees.
 * Demonstrates abstraction and supports inheritance and polymorphism.
 */
public abstract class Employee {

    private int employeeId;
    private String name;
    private String department;
    private String position;
    private String phone;
    private double salary;
    protected double performance;

    /**
     * Constructor for Employee.
     * Performance starts at 50 as a neutral default score.
     */
    public Employee(int employeeId, String name, String department, String position,
                    double salary, String phone) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.phone = phone;
        this.performance = 50.0;
    }

    // Getters
    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }

    public String getPhone() {
        return phone;
    }

    public double getSalary() {
        return salary;
    }

    public double getPerformance() {
        return performance;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Sets performance while keeping it between 0 and 100.
     * This is needed when loading saved performance from file.
     */
    public void setPerformance(double performance) {
        this.performance = Math.max(0, Math.min(100, performance));
    }

    /**
     * Applies positive or negative appraisal logic.
     * Implemented differently by each employee type.
     */
    public abstract void appraisal(boolean positiveFeedback);

    /**
     * Returns the employee type, such as Full-Time or Part-Time.
     */
    public abstract String getEmployeeType();

    /**
     * Returns the label for the employee-specific value.
     */
    public abstract String getExtraLabel();

    /**
     * Returns the employee-specific value.
     */
    public abstract int getExtraValue();

    /**
     * Updates the employee-specific value.
     */
    public abstract void setExtraValue(int value);

    /**
     * Returns employee status based on performance score.
     */
    public String employeeStatus() {
        if (performance >= 80) {
            return "Kudos! Great Job";
        }
        if (performance >= 50) {
            return "Acceptable";
        }
        if (performance >= 30) {
            return "Requires Improvement";
        }
        return "Unacceptable - Under Review";
    }

    /**
     * Calculates bonus based on performance.
     * Maximum bonus = 15% of salary.
     */
    public double calculateBonus() {
        double factor = performance / 100.0;
        return (salary * 0.15) * factor;
    }

    /**
     * Returns formatted employee details for TBI display.
     */
    @Override
    public String toString() {
        return String.format(
                "%-6d %-18s %-14s %-14s %-10.2f %-12s %-10s %-8.2f %-22s $%-10.2f",
                employeeId,
                name,
                department,
                position,
                salary,
                phone,
                getEmployeeType(),
                performance,
                employeeStatus(),
                calculateBonus()
        );
    }
}