import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public ArrayList<Employee> readEmployeesFromFile(String fileName) {
        ArrayList<Employee> employees = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("Warning: File not found. Starting with empty records.");
            return employees;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty()
                        || line.startsWith("=")
                        || line.startsWith("-")
                        || line.startsWith("Type,")
                        || line.startsWith("| Type")) {
                    continue;
                }

                try {
                    Employee employee = null;

                    if (line.contains("|")) {
                        String[] parts = line.split("\\|");
                        ArrayList<String> values = new ArrayList<>();

                        for (String part : parts) {
                            if (!part.trim().isEmpty()) {
                                values.add(part.trim());
                            }
                        }

                        if (values.size() < 9) {
                            System.out.println("Warning: Invalid table record at line " + lineNumber);
                            continue;
                        }

                        String type = values.get(0);
                        int id = Integer.parseInt(values.get(1));
                        String name = values.get(2);
                        String department = values.get(3);
                        String position = values.get(4);
                        double salary = Double.parseDouble(values.get(5));
                        String phone = values.get(6);
                        int extra = Integer.parseInt(values.get(7));
                        double performance = Double.parseDouble(values.get(8));

                        if (type.equalsIgnoreCase("Full-Time")) {
                            employee = new FullTimeEmployee(id, name, department, position, salary, phone, extra);
                        } else if (type.equalsIgnoreCase("Part-Time")) {
                            employee = new PartTimeEmployee(id, name, department, position, salary, phone, extra);
                        }

                        if (employee != null) {
                            employee.setPerformance(performance);
                        }

                    } else if (line.contains(",")) {
                        String[] parts = line.split(",");

                        if (parts.length < 8) {
                            System.out.println("Warning: Invalid CSV record at line " + lineNumber);
                            continue;
                        }

                        String type = parts[0].trim();
                        int id = Integer.parseInt(parts[1].trim());
                        String name = parts[2].trim();
                        String department = parts[3].trim();
                        String position = parts[4].trim();
                        double salary = Double.parseDouble(parts[5].trim());
                        String phone = parts[6].trim();
                        int extra = Integer.parseInt(parts[7].trim());

                        if (type.equalsIgnoreCase("Full-Time")) {
                            employee = new FullTimeEmployee(id, name, department, position, salary, phone, extra);
                        } else if (type.equalsIgnoreCase("Part-Time")) {
                            employee = new PartTimeEmployee(id, name, department, position, salary, phone, extra);
                        }

                        if (employee != null && parts.length >= 9) {
                            employee.setPerformance(Double.parseDouble(parts[8].trim()));
                        }
                    }

                    if (employee != null) {
                        employees.add(employee);
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Warning: Invalid numeric value at line " + lineNumber);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return employees;
    }

    public boolean writeEmployeesToFile(String fileName, List<Employee> employees) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {

            writer.println("==============================================================================================================================");
            writer.printf("| %-10s | %-6s | %-20s | %-15s | %-15s | %-10s | %-12s | %-10s | %-11s | %-10s |%n",
                    "Type", "ID", "Name", "Department", "Position", "Salary", "Phone", "Extra", "Performance", "Bonus");
            writer.println("==============================================================================================================================");

            if (employees == null || employees.isEmpty()) {
                writer.println("| No employee records found.                                                                                                 |");
            } else {
                for (Employee emp : employees) {
                    writer.printf("| %-10s | %-6d | %-20s | %-15s | %-15s | %-10.2f | %-12s | %-10d | %-11.2f | %-10.2f |%n",
                            emp.getEmployeeType(),
                            emp.getEmployeeId(),
                            emp.getName(),
                            emp.getDepartment(),
                            emp.getPosition(),
                            emp.getSalary(),
                            emp.getPhone(),
                            emp.getExtraValue(),
                            emp.getPerformance(),
                            emp.calculateBonus());
                }
            }

            writer.println("==============================================================================================================================");
            return true;

        } catch (IOException e) {
            System.out.println("Error writing employee file: " + e.getMessage());
            return false;
        }
    }

    public boolean writeQueryResultsToFile(String fileName, List<Employee> results) {
        return writeEmployeesToFile(fileName, results);
    }
}