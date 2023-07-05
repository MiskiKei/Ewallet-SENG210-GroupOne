import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class ReportExporter extends JFrame implements ActionListener {
    private JComboBox<String> reportComboBox;
    private JButton exportButton;
    private String monthlyIncomeReport;
    private String monthlyExpenseReport;

    public ReportExporter() {
        setTitle("Report Exporter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Report Label and Combo Box
        JLabel reportLabel = new JLabel("Select Report:");
        String[] reports = {"Monthly Income Report", "Monthly Expense Report"};
        reportComboBox = new JComboBox<>(reports);

        // Export Button
        exportButton = new JButton("Export Report");
        exportButton.addActionListener(this);

        // Layout
        setLayout(new FlowLayout());
        add(reportLabel);
        add(reportComboBox);
        add(exportButton);

        pack();
        setVisible(true);
        setSize(400, 150);
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exportButton) {
            String selectedReport = (String) reportComboBox.getSelectedItem();

            switch (selectedReport) {
                case "Monthly Income Report":
                    if (monthlyIncomeReport == null) {
                        monthlyIncomeReport = generateMonthlyIncomeReport();
                    }
                    exportToFile("MonthlyIncomeReport.txt", monthlyIncomeReport);
                    break;
                case "Monthly Expense Report":
                    if (monthlyExpenseReport == null) {
                        monthlyExpenseReport = generateMonthlyExpenseReport();
                    }
                    exportToFile("MonthlyExpenseReport.txt", monthlyExpenseReport);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Invalid report selected.");
            }
        }
    }

    private String generateMonthlyIncomeReport() {
        StringBuilder report = new StringBuilder();
        // Logic to generate the monthly income report without modifying the MonthlyIncomeTracker class

        report.append("Monthly Income Report:\n");
        report.append("----------------------\n");
        report.append("Month: January\n");
        report.append("Salary: $3000\n");
        report.append("Bonus: $500\n");
        report.append("----------------------\n");
        report.append("Overall Total Income: $3500\n");
        report.append("----------------------\n");

        return report.toString();
    }

    private String generateMonthlyExpenseReport() {
        StringBuilder report = new StringBuilder();
        // Logic to generate the monthly expense report without modifying the MonthlyExpenseTracker class

        // Example:
        report.append("Monthly Expense Report:\n");
        report.append("----------------------\n");
        report.append("Month: January\n");
        report.append("Expense 1: $100\n");
        report.append("Expense 2: $200\n");
        report.append("----------------------\n");
        report.append("Total Monthly Expense: $300\n");
        report.append("----------------------\n");

        return report.toString();
    }

    private void exportToFile(String fileName, String content) {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(content);
            writer.close();
            JOptionPane.showMessageDialog(this, "Report exported successfully to " + fileName,
                    "", JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error exporting report: " + e.getMessage(),
                    "", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReportExporter::new);
    }
}
