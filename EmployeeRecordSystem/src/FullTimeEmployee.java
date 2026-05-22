/**
 * Represents a full-time employee.
 * Demonstrates inheritance and polymorphism.
 */
public class FullTimeEmployee extends Employee {

    private int annualLeaveDays;

    /**
     * Constructor for FullTimeEmployee.
     */
    public FullTimeEmployee(int employeeId, String name, String department,
                            String position, double salary,
                            String phone, int annualLeaveDays) {

        super(employeeId, name, department, position, salary, phone);

        if (annualLeaveDays < 0) {
            this.annualLeaveDays = 0;
        } else {
            this.annualLeaveDays = annualLeaveDays;
        }
    }

    /**
     * Returns annual leave days.
     */
    public int getAnnualLeaveDays() {
        return annualLeaveDays;
    }

    /**
     * Updates annual leave days.
     */
    public void setAnnualLeaveDays(int annualLeaveDays) {
        if (annualLeaveDays >= 0) {
            this.annualLeaveDays = annualLeaveDays;
        }
    }

    /**
     * Applies employee appraisal logic.
     * Full-time employees receive stronger performance adjustments.
     */
    @Override
    public void appraisal(boolean positiveFeedback) {

        if (positiveFeedback) {
            performance += 5.5;
        } else {
            performance -= 5.5;
        }

        // Keep performance within valid range
        performance = Math.max(0, Math.min(100, performance));
    }

    /**
     * Returns employee type.
     */
    @Override
    public String getEmployeeType() {
        return "Full-Time";
    }

    /**
     * Returns the label used for extra employee information.
     */
    @Override
    public String getExtraLabel() {
        return "Annual Leave Days";
    }

    /**
     * Returns annual leave days.
     */
    @Override
    public int getExtraValue() {
        return annualLeaveDays;
    }

    /**
     * Updates annual leave days through polymorphic access.
     */
    @Override
    public void setExtraValue(int value) {

        if (value >= 0) {
            this.annualLeaveDays = value;
        }
    }
}