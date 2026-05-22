/**
 * InputValidator provides reusable validation methods
 * for employee data to ensure data integrity and system reliability.
 */
public class InputValidator {

    /**
     * Validates employee ID.
     * Employee ID must be greater than 0.
     */
    public static boolean isValidEmployeeId(int employeeId) {
        return employeeId > 0;
    }

    /**
     * Validates employee name.
     * Allows alphabetic characters and spaces only.
     * Name length must be between 2 and 50 characters.
     */
    public static boolean isValidName(String name) {

        if (name == null) {
            return false;
        }

        String trimmedName = name.trim();

        return trimmedName.matches("[A-Za-z ]{2,50}");
    }

    /**
     * Validates general text fields such as department and position.
     * Text must not be null, empty, or whitespace only.
     */
    public static boolean isValidText(String text) {

        if (text == null) {
            return false;
        }

        return !text.trim().isEmpty();
    }

    /**
     * Validates salary.
     * Salary must be greater than 0.
     */
    public static boolean isValidSalary(double salary) {
        return salary > 0;
    }

    /**
     * Validates phone numbers.
     * Accepts 10 to 15 digits only.
     */
    public static boolean isValidPhone(String phone) {

        if (phone == null) {
            return false;
        }

        String trimmedPhone = phone.trim();

        return trimmedPhone.matches("\\d{10,15}");
    }

    /**
     * Validates extra values such as:
     * - annual leave days
     * - hours worked
     *
     * Value cannot be negative.
     */
    public static boolean isValidExtraValue(int value) {
        return value >= 0;
    }

    /**
     * Validates performance score.
     * Performance must remain between 0 and 100.
     */
    public static boolean isValidPerformance(double performance) {
        return performance >= 0 && performance <= 100;
    }
}