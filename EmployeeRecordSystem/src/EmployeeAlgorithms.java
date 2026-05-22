import java.util.ArrayList;
import java.util.List;

/**
 * EmployeeAlgorithms contains sorting and searching algorithms
 * used in the Employee Record Management System.
 */
public class EmployeeAlgorithms {

    /**
     * Merge Sort by Employee Name in ascending order.
     */
    public static List<Employee> mergeSortByName(List<Employee> employees) {

        if (employees.size() <= 1) {
            return employees;
        }

        int middle = employees.size() / 2;

        List<Employee> left = mergeSortByName(
                new ArrayList<>(employees.subList(0, middle)));

        List<Employee> right = mergeSortByName(
                new ArrayList<>(employees.subList(middle, employees.size())));

        return mergeByName(left, right);
    }

    /**
     * Merges two employee lists alphabetically by name.
     */
    private static List<Employee> mergeByName(
            List<Employee> left,
            List<Employee> right) {

        List<Employee> result = new ArrayList<>();

        int i = 0;
        int j = 0;

        while (i < left.size() && j < right.size()) {

            if (left.get(i).getName()
                    .compareToIgnoreCase(right.get(j).getName()) <= 0) {

                result.add(left.get(i));
                i++;

            } else {

                result.add(right.get(j));
                j++;
            }
        }

        addRemainingEmployees(result, left, i);
        addRemainingEmployees(result, right, j);

        return result;
    }

    /**
     * Merge Sort by salary in ascending order.
     * Lowest salary appears first.
     */
    public static List<Employee> mergeSortBySalaryAscending(
            List<Employee> employees) {

        if (employees.size() <= 1) {
            return employees;
        }

        int middle = employees.size() / 2;

        List<Employee> left = mergeSortBySalaryAscending(
                new ArrayList<>(employees.subList(0, middle)));

        List<Employee> right = mergeSortBySalaryAscending(
                new ArrayList<>(employees.subList(middle, employees.size())));

        return mergeBySalaryAscending(left, right);
    }

    /**
     * Merges two employee lists by salary in ascending order.
     */
    private static List<Employee> mergeBySalaryAscending(
            List<Employee> left,
            List<Employee> right) {

        List<Employee> result = new ArrayList<>();

        int i = 0;
        int j = 0;

        while (i < left.size() && j < right.size()) {

            if (left.get(i).getSalary()
                    <= right.get(j).getSalary()) {

                result.add(left.get(i));
                i++;

            } else {

                result.add(right.get(j));
                j++;
            }
        }

        addRemainingEmployees(result, left, i);
        addRemainingEmployees(result, right, j);

        return result;
    }

    /**
     * Merge Sort by salary in descending order.
     * Highest salary appears first.
     */
    public static List<Employee> mergeSortBySalaryDescending(
            List<Employee> employees) {

        if (employees.size() <= 1) {
            return employees;
        }

        int middle = employees.size() / 2;

        List<Employee> left = mergeSortBySalaryDescending(
                new ArrayList<>(employees.subList(0, middle)));

        List<Employee> right = mergeSortBySalaryDescending(
                new ArrayList<>(employees.subList(middle, employees.size())));

        return mergeBySalaryDescending(left, right);
    }

    /**
     * Merges two employee lists by salary in descending order.
     */
    private static List<Employee> mergeBySalaryDescending(
            List<Employee> left,
            List<Employee> right) {

        List<Employee> result = new ArrayList<>();

        int i = 0;
        int j = 0;

        while (i < left.size() && j < right.size()) {

            if (left.get(i).getSalary()
                    >= right.get(j).getSalary()) {

                result.add(left.get(i));
                i++;

            } else {

                result.add(right.get(j));
                j++;
            }
        }

        addRemainingEmployees(result, left, i);
        addRemainingEmployees(result, right, j);

        return result;
    }

    /**
     * Adds remaining employees after the merge comparison is completed.
     */
    private static void addRemainingEmployees(
            List<Employee> result,
            List<Employee> source,
            int startIndex) {

        for (int i = startIndex; i < source.size(); i++) {
            result.add(source.get(i));
        }
    }

    /**
     * Binary Search by Employee ID.
     * The employee list must be sorted by employee ID before calling this method.
     */
    public static Employee binarySearchById(
            List<Employee> employees,
            int targetId) {

        int left = 0;
        int right = employees.size() - 1;

        while (left <= right) {

            int middle = (left + right) / 2;
            int middleId = employees.get(middle).getEmployeeId();

            if (middleId == targetId) {
                return employees.get(middle);
            }

            if (middleId < targetId) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        return null;
    }
}