import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SummaryGenerator extends JFrame {
    private MonthlyIncomeTracker incomeTracker;
    private MonthlyExpenseTracker expenseTracker;
    private JButton showReportButton;
    private JButton exportReportButton;

    public SummaryGenerator(MonthlyIncomeTracker incomeTracker, MonthlyExpenseTracker expenseTracker) {
        this.incomeTracker = incomeTracker;
        this.expenseTracker = expenseTracker;
        setTitle("Summary Generator");


        // Show Report Button
        showReportButton = new JButton("Show Report");
        showReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSummary();
            }
        });

        // Export Report Button
        exportReportButton = new JButton("Export Report");
        exportReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportSummary();
            }
        });

        // Layout
        setLayout(new FlowLayout());
        add(showReportButton);
        add(exportReportButton);

        pack();
        setVisible(true);
        setSize(850, 100);
        setLocationRelativeTo(null);
    }

    private void showSummary() {
        double totalIncome = calculateTotalIncome();
        double totalExpenses = expenseTracker.totalExpense;
        double savingsAmount = totalIncome - totalExpenses;

        StringBuilder summary = new StringBuilder();
        summary.append("Summary:\n");
        summary.append("----------------------\n");
        summary.append("Total Income: $").append(totalIncome).append("\n");
        summary.append("Total Expenses: $").append(totalExpenses).append("\n");
        summary.append("Savings Amount: $").append(savingsAmount).append("\n");
        summary.append("----------------------\n");

        JOptionPane.showMessageDialog(this, summary.toString(), "Summary", JOptionPane.PLAIN_MESSAGE);
    }

    private double calculateTotalIncome() {
        double totalIncome = 0.0;

        if (incomeTracker != null && incomeTracker.incomeEntries != null) {
            for (MonthlyIncomeTracker.IncomeEntry entry : incomeTracker.incomeEntries) {
                totalIncome += entry.getAmount();
            }
        }

        return totalIncome;
    }

    private void exportSummary() {
        JFileChooser fileChooser = new JFileChooser();
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File outputFile = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(outputFile)) {
                double totalIncome = calculateTotalIncome();
                double totalExpenses = expenseTracker.totalExpense;
                double savingsAmount = totalIncome - totalExpenses;

                StringBuilder summary = new StringBuilder();
                summary.append("Summary:\n");
                summary.append("----------------------\n");
                summary.append("Total Income: $").append(totalIncome).append("\n");
                summary.append("Total Expenses: $").append(totalExpenses).append("\n");
                summary.append("Savings Amount: $").append(savingsAmount).append("\n");
                summary.append("----------------------\n");

                writer.write(summary.toString());
                writer.flush();
                JOptionPane.showMessageDialog(this, "Report exported successfully!", "Export Report", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "An error occurred while exporting the report.", "Export Report Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MonthlyIncomeTracker incomeTracker = new MonthlyIncomeTracker();
        MonthlyExpenseTracker expenseTracker = new MonthlyExpenseTracker();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SummaryGenerator(incomeTracker, expenseTracker);
            }
        });
    }
}
