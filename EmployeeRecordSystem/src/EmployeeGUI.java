import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * EmployeeGUI provides the graphical interface for the Employee Record Management System.
 *
 * Features:
 * - Add, update, delete, search, sort, evaluate, save, and view employee records
 * - Binary Search by ID
 * - Linear Search by Name
 * - Merge Sort by Name
 * - Merge Sort by Salary Ascending and Descending
 * - JTable highlighting and clear status notifications
 * - Automatic file saving through EmployeeManager
 */
public class EmployeeGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final String FILE_NAME = "employees.txt";
    private static final String QUERY_FILE = "query_results.txt";

    private EmployeeManager manager;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;

    private JPanel formPanel;
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtDepartment;
    private JTextField txtPosition;
    private JTextField txtSalary;
    private JTextField txtPhone;
    private JTextField txtExtra;
    private JTextField txtSearch;

    private JComboBox<String> cmbType;

    // Stores highlighted table cells using row,column format.
    private Set<String> highlightedCells = new HashSet<>();

    /**
     * Constructor loads employee records and builds the GUI.
     */
    public EmployeeGUI() {
        manager = new EmployeeManager();
        manager.loadEmployeesFromFile(FILE_NAME);

        setTitle("Employee Record Management System");
        setSize(1250, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buildGUI();
        refreshTable(manager.getAllEmployees());
        hideForm();
    }

    /**
     * Builds the main GUI layout, table, form, buttons, and event listeners.
     */
    private void buildGUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Employee Record Management System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        mainPanel.add(title, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new String[] {
                        "ID", "Name", "Department", "Position", "Salary", "Phone",
                        "Type", "Extra", "Performance", "Status", "Bonus"
                },
                0
        );

        employeeTable = new JTable(tableModel);
        employeeTable.setRowHeight(26);
        employeeTable.setFont(new Font("Arial", Font.PLAIN, 13));
        employeeTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        employeeTable.setSelectionBackground(new Color(220, 235, 255));
        employeeTable.setSelectionForeground(Color.BLACK);

        // Custom renderer highlights searched, updated, added, or evaluated cells.
        employeeTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                Component cell = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                String key = row + "," + column;

                if (highlightedCells.contains(key)) {
                    cell.setBackground(new Color(255, 242, 204));
                    cell.setForeground(Color.BLACK);
                } else if (isSelected) {
                    cell.setBackground(new Color(220, 235, 255));
                    cell.setForeground(Color.BLACK);
                } else {
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                }

                return cell;
            }
        });

        mainPanel.add(new JScrollPane(employeeTable), BorderLayout.CENTER);

        formPanel = new JPanel(new GridLayout(5, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Employee Details"));

        txtId = new JTextField();
        txtName = new JTextField();
        txtDepartment = new JTextField();
        txtPosition = new JTextField();
        txtSalary = new JTextField();
        txtPhone = new JTextField();
        txtExtra = new JTextField();
        txtSearch = new JTextField();

        cmbType = new JComboBox<>(new String[] {"Full-Time", "Part-Time"});

        formPanel.add(new JLabel("Employee ID:"));
        formPanel.add(txtId);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(txtName);

        formPanel.add(new JLabel("Department:"));
        formPanel.add(txtDepartment);
        formPanel.add(new JLabel("Position:"));
        formPanel.add(txtPosition);

        formPanel.add(new JLabel("Salary:"));
        formPanel.add(txtSalary);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(txtPhone);

        formPanel.add(new JLabel("Employee Type:"));
        formPanel.add(cmbType);
        formPanel.add(new JLabel("Leave Days / Hours Worked:"));
        formPanel.add(txtExtra);

        formPanel.add(new JLabel("Search / Delete ID or Name:"));
        formPanel.add(txtSearch);

        /*
         * 4 rows x 4 columns is used because the GUI now has
         * separate salary ascending and salary descending sorting buttons.
         */
        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 10, 10));

        JButton btnAdd = new JButton("Add Employee");
        JButton btnView = new JButton("View All");
        JButton btnUpdate = new JButton("Update Employee");
        JButton btnDelete = new JButton("Delete by ID/Name");

        JButton btnSearchId = new JButton("Search by ID (Binary)");
        JButton btnSearchName = new JButton("Search by Name (Linear)");
        JButton btnSortName = new JButton("Sort by Name (Merge)");
        JButton btnSortSalaryAsc = new JButton("Sort Salary ↑");

        JButton btnSortSalaryDesc = new JButton("Sort Salary ↓");
        JButton btnEvaluatePositive = new JButton("Evaluate +");
        JButton btnEvaluateNegative = new JButton("Evaluate -");
        JButton btnSave = new JButton("Save");

        JButton btnClear = new JButton("Clear");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnView);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        buttonPanel.add(btnSearchId);
        buttonPanel.add(btnSearchName);
        buttonPanel.add(btnSortName);
        buttonPanel.add(btnSortSalaryAsc);

        buttonPanel.add(btnSortSalaryDesc);
        buttonPanel.add(btnEvaluatePositive);
        buttonPanel.add(btnEvaluateNegative);
        buttonPanel.add(btnSave);

        buttonPanel.add(btnClear);

        statusLabel = new JLabel("Ready", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 15));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(230, 230, 230));
        statusLabel.setForeground(Color.DARK_GRAY);
        statusLabel.setPreferredSize(new Dimension(100, 35));

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.add(formPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        /*
         * Event listeners connect GUI buttons with backend methods.
         * This supports event-driven programming and backend interaction.
         */
        btnAdd.addActionListener(e -> {
            clearHighlightedCells();
            showFullForm("Enter employee details, then click Add Employee again to save.");

            if (!txtId.getText().trim().isEmpty()) {
                addEmployee();
            }
        });

        btnUpdate.addActionListener(e -> {
            clearHighlightedCells();
            showFullForm("Select a row or enter employee details, then click Update Employee again.");

            if (!txtId.getText().trim().isEmpty()) {
                updateEmployee();
            }
        });

        btnDelete.addActionListener(e -> deleteEmployeeByIdOrName());

        btnSearchId.addActionListener(e -> searchById());
        btnSearchName.addActionListener(e -> searchByName());

        btnSortName.addActionListener(e -> {
            clearHighlightedCells();
            hideForm();
            refreshTable(manager.sortByName());
            showStatus("Employees sorted by Name using Merge Sort.", true);
        });

        btnSortSalaryAsc.addActionListener(e -> {
            clearHighlightedCells();
            hideForm();
            refreshTable(manager.sortBySalaryAscending());
            showStatus("Employees sorted by Salary Ascending using Merge Sort.", true);
        });

        btnSortSalaryDesc.addActionListener(e -> {
            clearHighlightedCells();
            hideForm();
            refreshTable(manager.sortBySalaryDescending());
            showStatus("Employees sorted by Salary Descending using Merge Sort.", true);
        });

        btnEvaluatePositive.addActionListener(e -> evaluateEmployee(true));
        btnEvaluateNegative.addActionListener(e -> evaluateEmployee(false));

        btnView.addActionListener(e -> {
            clearHighlightedCells();
            hideForm();
            refreshTable(manager.getAllEmployees());
            showStatus("All employee records displayed.", true);
        });

        btnSave.addActionListener(e -> saveRecords());

        btnClear.addActionListener(e -> {
            clearFields();
            hideForm();
            clearHighlightedCells();
            showStatus("Fields and highlights cleared.", true);
        });

        employeeTable.getSelectionModel().addListSelectionListener(
                e -> fillFieldsFromSelectedRow());
    }

    /**
     * Displays the employee form when add or update is required.
     */
    private void showFullForm(String message) {
        formPanel.setVisible(true);
        showStatus(message, true);
        revalidate();
        repaint();
    }

    /**
     * Hides the employee form for search, sort, and view operations.
     */
    private void hideForm() {
        formPanel.setVisible(false);
        revalidate();
        repaint();
    }

    /**
     * Adds a new employee and highlights the new row.
     */
    private void addEmployee() {
        try {
            Employee employee = createEmployeeFromFields();

            if (manager.addEmployee(employee, FILE_NAME)) {
                refreshTable(manager.getAllEmployees());
                highlightEmployeeRow(employee.getEmployeeId());
                clearInputFieldsOnly();
                showStatus("New employee added successfully. Record saved automatically.", true);
            } else {
                showStatus("Employee could not be added. Check validation rules or duplicate ID.", false);
            }

        } catch (Exception e) {
            showStatus("Invalid input. Please complete all employee details correctly.", false);
        }
    }

    /**
     * Updates employee details and highlights only the changed fields.
     */
    private void updateEmployee() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            Employee oldEmployee = manager.searchEmployeeById(id);

            if (oldEmployee == null) {
                showStatus("Employee not found for update.", false);
                return;
            }

            String oldName = oldEmployee.getName();
            String oldDepartment = oldEmployee.getDepartment();
            String oldPosition = oldEmployee.getPosition();
            double oldSalary = oldEmployee.getSalary();
            String oldPhone = oldEmployee.getPhone();
            String oldType = oldEmployee.getEmployeeType();
            int oldExtra = oldEmployee.getExtraValue();

            String newName = txtName.getText().trim();
            String newDepartment = txtDepartment.getText().trim();
            String newPosition = txtPosition.getText().trim();
            double newSalary = Double.parseDouble(txtSalary.getText().trim());
            String newPhone = txtPhone.getText().trim();
            String newType = cmbType.getSelectedItem().toString();
            int newExtra = Integer.parseInt(txtExtra.getText().trim());

            List<Integer> changedColumns = new ArrayList<>();
            StringBuilder changes = new StringBuilder();

            if (!oldName.equals(newName)) {
                changedColumns.add(1);
                changes.append("Name updated. ");
            }

            if (!oldDepartment.equals(newDepartment)) {
                changedColumns.add(2);
                changes.append("Department updated. ");
            }

            if (!oldPosition.equals(newPosition)) {
                changedColumns.add(3);
                changes.append("Position updated. ");
            }

            if (oldSalary != newSalary) {
                changedColumns.add(4);
                changes.append("Salary updated. ");
            }

            if (!oldPhone.equals(newPhone)) {
                changedColumns.add(5);
                changes.append("Phone updated. ");
            }

            if (!oldType.equals(newType)) {
                changedColumns.add(6);
                changes.append("Employee type updated. ");
            }

            if (oldExtra != newExtra) {
                changedColumns.add(7);
                changes.append("Extra value updated. ");
            }

            boolean updated = manager.updateEmployee(
                    id,
                    newName,
                    newDepartment,
                    newPosition,
                    newSalary,
                    newPhone,
                    newExtra,
                    newType,
                    FILE_NAME
            );

            if (updated) {
                refreshTable(manager.getAllEmployees());

                if (!changedColumns.isEmpty()) {
                    highlightUpdatedFields(id, changedColumns);
                    showStatus("Employee ID " + id + " updated successfully. " + changes, true);
                } else {
                    highlightEmployeeRow(id);
                    showStatus("Employee ID " + id + " checked. No field value changed.", true);
                }

                clearInputFieldsOnly();

            } else {
                showStatus("Employee update failed. Please check employee details.", false);
            }

        } catch (Exception e) {
            showStatus("Invalid input. Please enter valid employee details.", false);
        }
    }

    /**
     * Deletes an employee by either ID or name after confirmation.
     */
    private void deleteEmployeeByIdOrName() {
        clearHighlightedCells();

        String input = JOptionPane.showInputDialog(
                this,
                "Enter Employee ID or Name to delete:"
        );

        if (input == null || input.trim().isEmpty()) {
            showStatus("Delete cancelled. No employee ID or name entered.", false);
            return;
        }

        Employee employee = findEmployeeByIdOrName(input.trim());

        if (employee == null) {
            showStatus("No matching employee found.", false);
            return;
        }

        int id = employee.getEmployeeId();
        String name = employee.getName();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete Employee: " + name + " (ID: " + id + ")?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            showStatus("Delete operation cancelled.", false);
            return;
        }

        if (manager.deleteEmployee(id, FILE_NAME)) {
            refreshTable(manager.getAllEmployees());
            clearInputFieldsOnly();
            showStatus("Employee deleted successfully: "
                    + name + " (ID: " + id + "). Record saved automatically.", true);
        } else {
            showStatus("Delete operation failed.", false);
        }
    }

    /**
     * Searches employee by ID using Binary Search.
     */
    private void searchById() {
        clearHighlightedCells();

        String input = JOptionPane.showInputDialog(
                this,
                "Enter Employee ID for Binary Search:"
        );

        if (input == null || input.trim().isEmpty()) {
            showStatus("Search cancelled. No employee ID entered.", false);
            return;
        }

        try {
            int id = Integer.parseInt(input.trim());
            Employee employee = manager.binarySearchById(id);

            if (employee != null) {
                refreshTable(manager.getAllEmployees());
                highlightEmployeeRow(employee.getEmployeeId());
                showStatus("Employee highlighted using Binary Search by ID.", true);
            } else {
                showStatus("Employee not found using Binary Search.", false);
            }

        } catch (Exception e) {
            showStatus("Invalid employee ID for Binary Search.", false);
        }
    }

    /**
     * Searches employee by name using Linear Search.
     */
    private void searchByName() {
        clearHighlightedCells();

        String keyword = JOptionPane.showInputDialog(
                this,
                "Enter Employee Name for Linear Search:"
        );

        if (keyword == null || keyword.trim().isEmpty()) {
            showStatus("Search cancelled. No name entered.", false);
            return;
        }

        List<Employee> results = manager.searchEmployeeByName(keyword.trim());

        if (results.isEmpty()) {
            showStatus("No matching employee found using Linear Search.", false);
        } else {
            refreshTable(manager.getAllEmployees());
            highlightEmployeeRow(results.get(0).getEmployeeId());
            manager.saveSearchResults(QUERY_FILE, results);
            showStatus("Employee highlighted using Linear Search. Results saved automatically.", true);
        }
    }

    /**
     * Updates employee performance using positive or negative feedback.
     */
    private void evaluateEmployee(boolean positiveFeedback) {
        clearHighlightedCells();

        String input = JOptionPane.showInputDialog(
                this,
                "Enter Employee ID to evaluate:"
        );

        if (input == null || input.trim().isEmpty()) {
            showStatus("Evaluation cancelled. No employee ID entered.", false);
            return;
        }

        try {
            int id = Integer.parseInt(input.trim());

            if (manager.evaluateEmployee(id, positiveFeedback, FILE_NAME)) {
                refreshTable(manager.getAllEmployees());
                highlightEmployeeRow(id);
                showStatus("Employee evaluation updated successfully. Record saved automatically.", true);
            } else {
                showStatus("Employee not found.", false);
            }

        } catch (Exception e) {
            showStatus("Invalid employee ID for evaluation.", false);
        }
    }

    /**
     * Saves all employee records manually.
     */
    private void saveRecords() {
        if (manager.saveEmployeesToFile(FILE_NAME)) {
            showStatus("All employee records saved successfully.", true);
        } else {
            showStatus("Error saving employee records.", false);
        }
    }

    /**
     * Finds an employee by either ID or name.
     */
    private Employee findEmployeeByIdOrName(String input) {
        try {
            return manager.searchEmployeeById(Integer.parseInt(input));
        } catch (NumberFormatException e) {
            List<Employee> results = manager.searchEmployeeByName(input);

            if (!results.isEmpty()) {
                return results.get(0);
            }
        }

        return null;
    }

    /**
     * Creates a FullTimeEmployee or PartTimeEmployee object from input fields.
     */
    private Employee createEmployeeFromFields() {
        int id = Integer.parseInt(txtId.getText().trim());
        String name = txtName.getText().trim();
        String department = txtDepartment.getText().trim();
        String position = txtPosition.getText().trim();
        double salary = Double.parseDouble(txtSalary.getText().trim());
        String phone = txtPhone.getText().trim();
        int extra = Integer.parseInt(txtExtra.getText().trim());

        if (cmbType.getSelectedItem().toString().equals("Full-Time")) {
            return new FullTimeEmployee(
                    id, name, department, position, salary, phone, extra);
        }

        return new PartTimeEmployee(
                id, name, department, position, salary, phone, extra);
    }

    /**
     * Refreshes JTable with the supplied employee list.
     */
    private void refreshTable(List<Employee> employees) {
        tableModel.setRowCount(0);

        for (Employee emp : employees) {
            tableModel.addRow(new Object[] {
                    emp.getEmployeeId(),
                    emp.getName(),
                    emp.getDepartment(),
                    emp.getPosition(),
                    String.format("%.2f", emp.getSalary()),
                    emp.getPhone(),
                    emp.getEmployeeType(),
                    emp.getExtraValue(),
                    String.format("%.2f", emp.getPerformance()),
                    emp.employeeStatus(),
                    String.format("%.2f", emp.calculateBonus())
            });
        }
    }

    /**
     * Loads selected JTable row data into the form fields.
     */
    private void fillFieldsFromSelectedRow() {
        int row = employeeTable.getSelectedRow();

        if (row >= 0) {
            formPanel.setVisible(true);

            txtId.setText(tableModel.getValueAt(row, 0).toString());
            txtName.setText(tableModel.getValueAt(row, 1).toString());
            txtDepartment.setText(tableModel.getValueAt(row, 2).toString());
            txtPosition.setText(tableModel.getValueAt(row, 3).toString());
            txtSalary.setText(tableModel.getValueAt(row, 4).toString());
            txtPhone.setText(tableModel.getValueAt(row, 5).toString());
            cmbType.setSelectedItem(tableModel.getValueAt(row, 6).toString());
            txtExtra.setText(tableModel.getValueAt(row, 7).toString());

            revalidate();
            repaint();
        }
    }

    /**
     * Clears all fields, selections, and highlights.
     */
    private void clearFields() {
        clearInputFieldsOnly();
        employeeTable.clearSelection();
        clearHighlightedCells();
    }

    /**
     * Clears input fields only.
     */
    private void clearInputFieldsOnly() {
        txtId.setText("");
        txtName.setText("");
        txtDepartment.setText("");
        txtPosition.setText("");
        txtSalary.setText("");
        txtPhone.setText("");
        txtExtra.setText("");
        txtSearch.setText("");
        cmbType.setSelectedIndex(0);
    }

    /**
     * Displays success or error messages in the status label.
     */
    private void showStatus(String message, boolean success) {
        statusLabel.setText(message);

        if (success) {
            statusLabel.setBackground(new Color(198, 239, 206));
            statusLabel.setForeground(new Color(0, 97, 0));
        } else {
            statusLabel.setBackground(new Color(255, 199, 206));
            statusLabel.setForeground(new Color(156, 0, 6));
        }
    }

    /**
     * Highlights an entire employee row.
     */
    private void highlightEmployeeRow(int id) {
        highlightedCells.clear();

        for (int row = 0; row < tableModel.getRowCount(); row++) {
            int tableId =
                    Integer.parseInt(tableModel.getValueAt(row, 0).toString());

            if (tableId == id) {
                for (int col = 0; col < tableModel.getColumnCount(); col++) {
                    highlightedCells.add(row + "," + col);
                }

                employeeTable.setRowSelectionInterval(row, row);
                employeeTable.scrollRectToVisible(
                        employeeTable.getCellRect(row, 0, true));
                employeeTable.repaint();
                break;
            }
        }
    }

    /**
     * Highlights only the specific updated fields.
     */
    private void highlightUpdatedFields(
            int employeeId,
            List<Integer> changedColumns) {

        highlightedCells.clear();

        int targetRow = -1;

        for (int row = 0; row < tableModel.getRowCount(); row++) {
            int tableId =
                    Integer.parseInt(tableModel.getValueAt(row, 0).toString());

            if (tableId == employeeId) {
                targetRow = row;
                break;
            }
        }

        if (targetRow == -1) {
            return;
        }

        for (int column : changedColumns) {
            highlightedCells.add(targetRow + "," + column);
        }

        employeeTable.setRowSelectionInterval(targetRow, targetRow);
        employeeTable.scrollRectToVisible(
                employeeTable.getCellRect(targetRow, 0, true));
        employeeTable.repaint();
    }

    /**
     * Clears all highlighted cells and table selection.
     */
    private void clearHighlightedCells() {
        highlightedCells.clear();
        employeeTable.clearSelection();
        employeeTable.repaint();
    }
}