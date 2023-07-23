import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
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
        add(exportButton);

        pack();
        setVisible(true);
        setSize(850, 100);
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Income")) {
            String incomeText = incomeField.getText();
            double income = 0.0;
            if (!incomeText.isEmpty()) {
                income = Double.parseDouble(incomeText);
            }
            String month = (String) monthComboBox.getSelectedItem();
            String incomeType = (String) incomeTypeComboBox.getSelectedItem();

            incomeEntries.add(new IncomeEntry(month, incomeType, income));

            incomeField.setText("");
        } else if (e.getActionCommand().equals("Generate Report")) {
            generateReport();
        } else if (e.getActionCommand().equals("Read File")){
            readIncomeFile();
        } else if (e.getActionCommand().equals("Export Report")) {
            exportReport();
        }
    }

    public void readIncomeFile() {
        String month;
        String incomeType;
        String income;

        try {
            File file = new File("IncomeFile");
            Scanner scnr = new Scanner(file);
            scnr.useDelimiter(",");
            while (scnr.hasNextLine()) {
                income = scnr.next();
                incomeType = scnr.next();
                month = scnr.next();
                incomeEntries.add(new IncomeEntry(month, incomeType, Double.parseDouble(income)));
            }
            System.out.println("Success!");
            scnr.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public StringBuilder generateReport() {
        StringBuilder report = new StringBuilder();
        Map<String, Map<String, Double>> monthIncomeMap = new TreeMap<>();
        Map<String, Double> incomeTypeTotalMap = new HashMap<>();
        double overallTotalIncome = 0.0;

        report.append("Monthly Income Report:\n");
        report.append("----------------------\n");

        for (IncomeEntry entry : incomeEntries) {
            String month = entry.getMonth();
            String incomeType = entry.getType();
            double income = entry.getAmount();

            if (!monthIncomeMap.containsKey(month)) {
                monthIncomeMap.put(month, new HashMap<>());
            }

            Map<String, Double> incomeMap = monthIncomeMap.get(month);
            double totalIncome = incomeMap.getOrDefault(incomeType, 0.0) + income;
            incomeMap.put(incomeType, totalIncome);

            double typeTotalIncome = incomeTypeTotalMap.getOrDefault(incomeType, 0.0) + income;
            incomeTypeTotalMap.put(incomeType, typeTotalIncome);

            overallTotalIncome += income;
        }

        for (Map.Entry<String, Map<String, Double>> monthEntry : monthIncomeMap.entrySet()) {
            String month = monthEntry.getKey();
            Map<String, Double> incomeMap = monthEntry.getValue();

            report.append(month).append(":\n");

            for (Map.Entry<String, Double> incomeEntry : incomeMap.entrySet()) {
                String incomeType = incomeEntry.getKey();
                double totalIncome = incomeEntry.getValue();

                report.append("  - ").append(incomeType).append(": $").append(totalIncome).append("\n");
            }

        }

        report.append("----------------------\n");


        for (Map.Entry<String, Double> incomeTypeEntry : incomeTypeTotalMap.entrySet()) {
            String incomeType = incomeTypeEntry.getKey();
            double typeTotalIncome = incomeTypeEntry.getValue();

            report.append(incomeType).append(" Total: $").append(typeTotalIncome).append("\n");
        }
        report.append("----------------------\n");
        report.append("Overall Total Income: $").append(overallTotalIncome).append("\n");
        report.append("----------------------\n");

        JOptionPane.showMessageDialog(this, report.toString(), "", JOptionPane.PLAIN_MESSAGE);



        return report;
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
