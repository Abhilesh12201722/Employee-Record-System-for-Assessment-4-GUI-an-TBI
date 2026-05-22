/**
 * Represents a part-time employee.
 * Demonstrates inheritance and polymorphism.
 */
public class PartTimeEmployee extends Employee {

    private int hoursWorked;

    /**
     * Constructor for PartTimeEmployee.
     */
    public PartTimeEmployee(int employeeId, String name, String department,
                            String position, double salary,
                            String phone, int hoursWorked) {

        super(employeeId, name, department, position, salary, phone);

        if (hoursWorked < 0) {
            this.hoursWorked = 0;
        } else {
            this.hoursWorked = hoursWorked;
        }
    }

    /**
     * Returns hours worked.
     */
    public int getHoursWorked() {
        return hoursWorked;
    }

    /**
     * Updates hours worked.
     */
    public void setHoursWorked(int hoursWorked) {
        if (hoursWorked >= 0) {
            this.hoursWorked = hoursWorked;
        }
    }

    /**
     * Applies appraisal logic for part-time employees.
     */
    @Override
    public void appraisal(boolean positiveFeedback) {
        if (positiveFeedback) {
            performance += 2.5;
        } else {
            performance -= 2.0;
        }

        // Keep performance between 0 and 100
        performance = Math.max(0, Math.min(100, performance));
    }

    /**
     * Returns employee type.
     */
    @Override
    public String getEmployeeType() {
        return "Part-Time";
    }

    /**
     * Returns the label for extra information.
     */
    @Override
    public String getExtraLabel() {
        return "Hours Worked";
    }

    /**
     * Returns hours worked.
     */
    @Override
    public int getExtraValue() {
        return hoursWorked;
    }

    /**
     * Updates hours worked through polymorphic access.
     */
    @Override
    public void setExtraValue(int value) {
        if (value >= 0) {
            this.hoursWorked = value;
        }
    }
}