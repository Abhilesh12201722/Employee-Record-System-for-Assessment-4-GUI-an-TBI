import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class MainApp {

    public static void main(String[] args) {

        String[] options = {
                "Text-Based Interface",
                "Graphical User Interface"
        };

        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose your preferred interface mode:",
                "Employee Record Management System",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[1]
        );

        if (choice == 0) {
            EmployeeCLI.runCLI();
        } else if (choice == 1) {
            SwingUtilities.invokeLater(() -> new EmployeeGUI().setVisible(true));
        } else {
            System.out.println("Program closed.");
        }
    }
}