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
    private JButton generateReportTypeButton;
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
        JLabel expenseDescriptionLabel = new JLabel("Expense Type:");
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
        
     // Generate Report By type Button
        generateReportTypeButton = new JButton("By Type");
        generateReportTypeButton.addActionListener(this);


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
        add(generateReportTypeButton);
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
        } else if (e.getSource() == generateReportTypeButton) {
            generateReportByType();
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
        
        if (!description.equalsIgnoreCase("Car Expenses") 
                && !description.equalsIgnoreCase("Groceries")
                && !description.equalsIgnoreCase("Recreational")
                && !description.equalsIgnoreCase("Bills")) {
            JOptionPane.showMessageDialog(this, "Invalid expense type. Please enter 'Car Expenses', 'Recreational', 'Groceries', or 'Bills'.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid expense amount. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //Expense expense = new Expense(amount, description);
        //expenses.add(expense);
        //totalExpense += amount;

        SQLStatements.insertExpense(amount, description);
        
        expenseAmountField.setText("");
        expenseDescriptionField.setText("");
    }

    public void readExpenseFile() {
        try {
            File file = new File("C:/Users/Damian/git/Ewallet-SENG210-GroupOne/ExpenseFile.txt");
            Scanner scnr = new Scanner(file);
            scnr.useDelimiter(",");

            while (scnr.hasNextLine()) {
                String line = scnr.nextLine();
                String[] parts = line.split(",");

                if (parts.length == 2) {
                    double amount = Double.parseDouble(parts[0].trim());
                    String description = parts[1].trim();
                    
                    
                    SQLStatements.insertExpense(amount, description);
                    
                    
                   
                    totalExpense += amount;
                    System.out.println("done");
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
    	List rowValues = SQLStatements.expenseSize();
    	int rowSize = rowValues.size();
        StringBuilder report = new StringBuilder();
        report.append("Monthly Expense Report:\n");
        report.append("----------------------\n");
        totalExpense = 0;
        for (int i = 0; i < rowSize; i++) {
            Object tempVar = rowValues.get(i);
            String tempString = tempVar.toString();
        	Object[] rowresults = SQLStatements.selectExpense(tempString);
        	String objType = (String) rowresults[0];
        	String objAmount = (String) rowresults[1];
        	double addAmount = Double.parseDouble(objAmount);
        	totalExpense += addAmount;
        	String objUser = (String) rowresults[2];
        	//Expense expense = expenses.get(i);
            report.append("Expense Type: " + objType + " ($" + objAmount + ")\n");
        }

        // Total Expense
        report.append("----------------------\n");
        report.append("Expense Report Total: $" + totalExpense + "\n");
        report.append("----------------------\n");

        JOptionPane.showMessageDialog(this, report.toString(), "", JOptionPane.PLAIN_MESSAGE);

        return report.toString();
    }
    
    
    public String generateReportByType() {
        String description = expenseDescriptionField.getText();

        
        if (!description.equalsIgnoreCase("Car Expenses") 
                && !description.equalsIgnoreCase("Groceries")
                && !description.equalsIgnoreCase("Recreational")
                && !description.equalsIgnoreCase("Bills")) {
            JOptionPane.showMessageDialog(this, "Invalid expense type. Please enter 'Car Expenses', 'Recreational', 'Groceries', or 'Bills', into the Expense type field.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    	
    	
    	List rowValues = SQLStatements.expenseSizebyType(description);
    	int rowSize = rowValues.size();
        StringBuilder report = new StringBuilder();
        report.append("Expense Report by Type:\n");
        report.append("----------------------\n");
        totalExpense = 0;
        for (int i = 0; i < rowSize; i++) {
            Object tempVar = rowValues.get(i);
            String tempString = tempVar.toString();
        	Object[] rowresults = SQLStatements.selectExpenseByType(tempString);
        	String objType = (String) rowresults[0];
        	String objAmount = (String) rowresults[1];
        	double addAmount = Double.parseDouble(objAmount);
        	totalExpense += addAmount;
        	String objUser = (String) rowresults[2];
        	//Expense expense = expenses.get(i);
            report.append("Expense Type: " + objType + " ($" + objAmount + ")\n");
        }

        // Total Expense
        report.append("----------------------\n");
        report.append("Total Expense: $" + totalExpense + "\n");
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
