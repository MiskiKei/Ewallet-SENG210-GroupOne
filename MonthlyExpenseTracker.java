import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MonthlyExpenseTracker extends JFrame implements ActionListener {
    private JTextField expenseAmountField;
    private JTextField expenseDescriptionField;
    private JButton addExpenseButton;
    private JButton generateReportButton;
    private JButton readFileButton;
    private JButton exportReportButton;
    public List<Expense> expenses;
    public double totalExpense;

    public MonthlyExpenseTracker() {
        setTitle("Monthly Expense Tracker");
        expenses = new ArrayList<>();

        // Expense Amount Label and Text Field
        JLabel expenseAmountLabel = new JLabel("Expense Amount:");
        expenseAmountField = new JTextField(10);

        // Expense Description Label and Text Field
        JLabel expenseDescriptionLabel = new JLabel("Expense Description:");
        expenseDescriptionField = new JTextField(20);

        // Add Expense Button
        addExpenseButton = new JButton("Add Expense");
        addExpenseButton.addActionListener(this);

        // Read File Button
        readFileButton = new JButton("Read File");
        readFileButton.addActionListener(this);

        // Generate Report Button
        generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(this);

        // Export Report Button
        exportReportButton = new JButton("Export Report");
        exportReportButton.addActionListener(this);

        // Layout
        setLayout(new FlowLayout());
        add(expenseAmountLabel);
        add(expenseAmountField);
        add(expenseDescriptionLabel);
        add(expenseDescriptionField);
        add(addExpenseButton);
        add(readFileButton);
        add(generateReportButton);
        add(exportReportButton);

        pack();
        setSize(850, 100);
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addExpenseButton) {
            addExpense();
        } else if (e.getSource() == generateReportButton) {
            generateReport();
        } else if (e.getSource() == readFileButton) {
            readExpenseFile();
        } else if (e.getSource() == exportReportButton) {
            exportReport();
        }
    }

    public void addExpense() {
        String amountStr = expenseAmountField.getText();
        String description = expenseDescriptionField.getText();

        if (amountStr.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both the amount and description of the expense.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid expense amount. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Expense expense = new Expense(amount, description);
        expenses.add(expense);
        totalExpense += amount;

        expenseAmountField.setText("");
        expenseDescriptionField.setText("");
    }

    public void readExpenseFile() {
        try {
            File file = new File("ExpenseFile");
            Scanner scnr = new Scanner(file);
            scnr.useDelimiter(",");

            while (scnr.hasNextLine()) {
                String line = scnr.nextLine();
                String[] parts = line.split(",");

                if (parts.length == 2) {
                    double amount = Double.parseDouble(parts[0].trim());
                    String description = parts[1].trim();

                    Expense expense = new Expense(amount, description);
                    expenses.add(expense);
                    totalExpense += amount;
                } else {
                    System.out.println("Invalid line: " + line);
                }
            }

            System.out.println("Success!");
            generateReport();
            scnr.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("Monthly Expense Report:\n");
        report.append("----------------------\n");

        for (int i = 0; i < expenses.size(); i++) {
            Expense expense = expenses.get(i);
            report.append("Expense Description: " + expense.getDescription() + " ($" + expense.getAmount() + ")\n");
        }

        // Total Expense
        report.append("----------------------\n");
        report.append("Total Monthly Expense: $" + totalExpense + "\n");
        report.append("----------------------\n");

        JOptionPane.showMessageDialog(this, report.toString(), "", JOptionPane.PLAIN_MESSAGE);

        return report.toString();
    }

    public void exportReport() {
        // Create a file chooser dialog
        JFileChooser fileChooser = new JFileChooser();

        // Set the default file name and extension
        String defaultFileName = "MonthlyExpenseReport.txt";
        fileChooser.setSelectedFile(new File(defaultFileName));

        // Show the save dialog
        int userSelection = fileChooser.showSaveDialog(this);

        // Check if the user clicked the "Save" button
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File outputFile = fileChooser.getSelectedFile();

            try {
                // Write the report content to the selected file
                Files.write(outputFile.toPath(), generateReport().getBytes());
                JOptionPane.showMessageDialog(this, "Report exported successfully!", "Export Report", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "An error occurred while exporting the report.", "Export Report Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MonthlyExpenseTracker::new);
    }

    public static class Expense {
        private double amount;
        private String description;

        public Expense(double amount, String description) {
            this.amount = amount;
            this.description = description;
        }

        public double getAmount() {
            return amount;
        }

        public String getDescription() {
            return description;
        }
    }
}
