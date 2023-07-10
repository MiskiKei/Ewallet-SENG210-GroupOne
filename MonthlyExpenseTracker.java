import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MonthlyExpenseTracker extends JFrame implements ActionListener {
    private JTextField expenseAmountField;
    private JTextField expenseDescriptionField;
    private JButton addExpenseButton;
    private JButton generateReportButton;
    private List<Expense> expenses;
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

        // Generate Report Button
        generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(this);

        // Layout
        setLayout(new FlowLayout());
        add(expenseAmountLabel);
        add(expenseAmountField);
        add(expenseDescriptionLabel);
        add(expenseDescriptionField);
        add(addExpenseButton);
        add(generateReportButton);

        pack();
        setSize(850, 100);
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addExpenseButton) {
            addExpense();
        } else if (e.getSource() == generateReportButton) {
            generateReport();
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

    public void generateReport() {
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

        JOptionPane.showMessageDialog(this, report.toString(), "Expense Report", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MonthlyExpenseTracker::new);
    }

    private static class Expense {
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
