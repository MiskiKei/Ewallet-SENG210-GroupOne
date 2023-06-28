import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MonthlyIncomeTracker extends JFrame implements ActionListener {
    private JTextField incomeField;
    private JComboBox<String> monthComboBox;
    private double[] monthlyIncome;

    public MonthlyIncomeTracker() {
        setTitle("Monthly Income Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Initialize monthlyIncome array
        monthlyIncome = new double[12];

        // Income Label and Text Field
        JLabel incomeLabel = new JLabel("Enter Income:");
        incomeField = new JTextField(10);

        // Month Label and Combo Box
        JLabel monthLabel = new JLabel("Select Month:");
        String[] months = {"January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"};
        monthComboBox = new JComboBox<>(months);

        // Add Button
        JButton addButton = new JButton("Add Income");
        addButton.addActionListener(this);

        // Report Button
        JButton reportButton = new JButton("Generate Report");
        reportButton.addActionListener(this);

        // Layout
        setLayout(new FlowLayout());
        add(incomeLabel);
        add(incomeField);
        add(monthLabel);
        add(monthComboBox);
        add(addButton);
        add(reportButton);

        pack();
        setVisible(true);
        setSize(750,80);
        setLocationRelativeTo(null);
    }
        //action for both buttons
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Income")) {
            String incomeText = incomeField.getText();
            double income = Double.parseDouble(incomeText);
            int monthIndex = monthComboBox.getSelectedIndex();
            monthlyIncome[monthIndex] = income;
            JOptionPane.showMessageDialog(this, "Income added successfully!","", JOptionPane.PLAIN_MESSAGE);
            incomeField.setText("");
        } else if (e.getActionCommand().equals("Generate Report")) {
            generateReport();
        }
    }

    private void generateReport() {
        double totalIncome = 0.0;
        StringBuilder report = new StringBuilder();
        String[] months = {"January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"};

        report.append("Monthly Income Report:\n");
        report.append("----------------------\n");
        for (int i = 0; i < 12; i++) {
            report.append(months[i]).append(": $").append(monthlyIncome[i]).append("\n");
            totalIncome += monthlyIncome[i];
        }
        report.append("----------------------\n");
        report.append("Total Income: $").append(totalIncome).append("\n");

        JOptionPane.showMessageDialog(this, report.toString(),"", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MonthlyIncomeTracker();
            }
        });
    }
}
