import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class MonthlyIncomeTracker extends JFrame implements ActionListener {
    private JTextField incomeField;
    private JComboBox<String> monthComboBox;
    private JComboBox<String> incomeTypeComboBox;
    public ArrayList<IncomeEntry> incomeEntries;
    public double totalIncome;
    static class IncomeEntry {
        private String month;
        private String type;
        private double amount;

        public IncomeEntry(String month, String type, double amount) {
            this.month = month;
            this.type = type;
            this.amount = amount;
        }

        public String getMonth() {
            return month;
        }

        public String getType() {
            return type;
        }

        public double getAmount() {
            return amount;
        }
    }

    public MonthlyIncomeTracker() {
        setTitle("Monthly Income Tracker");

        incomeEntries = new ArrayList<>(); 

        // Income Label and Text Field
        JLabel incomeLabel = new JLabel("Enter Income:");
        incomeField = new JTextField(10);

        // Month Label and Combo Box
        JLabel monthLabel = new JLabel("Select Month:");
        String[] months = {"January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"};
        monthComboBox = new JComboBox<>(months);

        // Income Type Label and Combo Box
        JLabel incomeTypeLabel = new JLabel("Select Income Type:");
        String[] incomeTypes = {"Salary", "Bonus", "Investments", "Other"};
        incomeTypeComboBox = new JComboBox<>(incomeTypes);

        // Add Button
        JButton addButton = new JButton("Add Income");
        addButton.addActionListener(this);

        // Report Button
        JButton reportButton = new JButton("Generate Report");
        reportButton.addActionListener(this);

        // Read File Button
        JButton fileButton = new JButton("Read File");
        fileButton.addActionListener(this);
        
        // Generate Report By type Button
        JButton generateReportTypeButton = new JButton("Report By Type");
        generateReportTypeButton.addActionListener(this);

        // Export Button
        JButton exportButton = new JButton("Export Report");
        exportButton.addActionListener(this);

        // Layout
        setLayout(new FlowLayout());
        add(incomeLabel);
        add(incomeField);
        add(incomeTypeLabel);
        add(incomeTypeComboBox);
        add(monthLabel);
        add(monthComboBox);
        add(addButton);
        add(fileButton);
        add(reportButton);
        add(generateReportTypeButton);
        add(exportButton);
       

        pack();
        setVisible(true);
        setSize(850, 100);
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) { ///ADDING A TYPE OF INCOME
        if (e.getActionCommand().equals("Add Income")) {
        	addIncome();
        } else if (e.getActionCommand().equals("Generate Report")) {
            generateReport();
        } else if (e.getActionCommand().equals("Read File")){
            readIncomeFile();
        } else if (e.getActionCommand().equals("Export Report")) {
            exportReport();
        } else if (e.getActionCommand().equals("Report By Type")){
        	generateIncomeReportByType(); ///CHANGEEEEEEE
        }
    }

    private void addIncome() {
        String incomeText = incomeField.getText();
        String month = (String) monthComboBox.getSelectedItem();
        String incomeType = (String) incomeTypeComboBox.getSelectedItem();
         
        if (incomeText.isEmpty()) { // Check if incomeText is empty
            JOptionPane.showMessageDialog(this, "Please enter a valid amount for income", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        double amount;
        try {
            amount = Double.parseDouble(incomeText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid income amount. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }   
        
        SQLStatements.insertIncome(amount, incomeType, month); 
        totalIncome += amount;
        
       // System.out.println("Income added successfully to the database!");
        
        JOptionPane.showMessageDialog(this, "Income Was Successfully Added!");

        incomeField.setText("");		
    }

    public void readIncomeFile() {
        try {
            File file = new File("src/IncomeFile.txt");
            Scanner scnr = new Scanner(file);

            while (scnr.hasNextLine()) {
                String line = scnr.nextLine();
                String[] parts = line.split(",");

                if (parts.length == 3) {
                    double amount = Double.parseDouble(parts[0].trim());
                    String incomeType = parts[1].trim();
                    String month = parts[2].trim();
                    //add user here later
                    
                    SQLStatements.insertIncome(amount, incomeType, month);
                    totalIncome += amount;
                } else {
                    System.out.println("Invalid line: " + line);
                }
            }

            JOptionPane.showMessageDialog(this, "Income Was Successfully Added!");
            
            scnr.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred: File not found.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("An error occurred: Invalid number format.");
            e.printStackTrace();
        }
    }


    public String generateReport() {
        
    	List<Object[]> allIncomeData = SQLStatements.selectAllIncome(); // Fetch all income data
    	StringBuilder report = new StringBuilder();
    	report.append("Monthly Income Report:\n");
    	report.append("--------------------------------------------------\n");
    	double totalIncome = 0;

    	for (Object[] rowResults : allIncomeData) {
    	    String objType = (String) rowResults[0];
    	    String objAmount = (String) rowResults[1];
    	    String objMonth = (String) rowResults[2];
    	    double addAmount = Double.parseDouble(objAmount);
    	    totalIncome += addAmount;
    	    String objUser = (String) rowResults[3];

    	    report.append("Income Type: " + objType + " ($" + objAmount + ") - Month: " + objMonth + " - User: " + objUser + "\n");
    	}

    	// Total Income
    	report.append("--------------------------------------------------\n");
    	report.append("Income Report Total: $" + totalIncome + "\n");
    	report.append("--------------------------------------------------\n");

    	JOptionPane.showMessageDialog(this, report.toString(), "Monthly Income Report", JOptionPane.PLAIN_MESSAGE);

    	return report.toString();

    }
    
    public String generateIncomeReportByType() {
        String incomeType = (String) incomeTypeComboBox.getSelectedItem();
        List<Object[]> rowValues = SQLStatements.selectIncomeByType(incomeType); // Assuming selectIncomeByType returns List<Object[]>
        int rowSize = rowValues.size(); //Number of rows 

        StringBuilder report = new StringBuilder();
        report.append("Income Report by Type: \n");
        report.append("--------------------------------------------------\n");
        double totalIncome = 0;

        for (Object[] rowresults : rowValues) { 
            if (rowresults != null) { // Check if there is data for the income type
                String objType = (String) rowresults[0];
                String objAmount = (String) rowresults[1];
                double addAmount = Double.parseDouble(objAmount);
                totalIncome += addAmount;
                String objMonth = (String) rowresults[2];
                String objUser = (String) rowresults[3];
                report.append("Income Type: " + objType + " ($" + objAmount + ") - Month: " + objMonth + " - User: " + objUser + "\n");
            }
        }

        // Total Income
        report.append("--------------------------------------------------\n");
        report.append("Total Income: " + ": $" + totalIncome + "\n");
        report.append("--------------------------------------------------\n");

        JOptionPane.showMessageDialog(this, report.toString(), "Income Report", JOptionPane.PLAIN_MESSAGE);
        return report.toString();
    }




    public void exportReport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                FileWriter writer = new FileWriter(fileToSave);
                writer.write(generateReport().toString());
                writer.close();
                JOptionPane.showMessageDialog(this, "Report exported successfully.", "Export Report", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error exporting report.", "Export Report", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MonthlyIncomeTracker::new);
    }
}
