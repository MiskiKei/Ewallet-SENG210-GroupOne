import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MonthEstimator {
    public JFrame frame;
    private JTextField itemField;
    private JTextField priceField;
    private JLabel resultLabel;
    private MonthlySavings monthlySavings;

    public MonthEstimator(MonthlyIncomeTracker incomeTracker, MonthlyExpenseTracker expenseTracker) {
        MonthlySavings monthlySavings = new MonthlySavings(incomeTracker, expenseTracker);

        frame = new JFrame("Savings Estimate");

        frame.setLayout(new FlowLayout());

        JLabel itemLabel = new JLabel("Item: ");
        itemField = new JTextField(10);
        JLabel priceLabel = new JLabel("Price: ");
        priceField = new JTextField(10);
        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new CalculateButtonListener());
        resultLabel = new JLabel();

        frame.add(itemLabel);
        frame.add(itemField);
        frame.add(priceLabel);
        frame.add(priceField);
        frame.add(calculateButton);
        frame.add(resultLabel);

        frame.pack();
        frame.setSize(850, 100);
        frame.setLocationRelativeTo(null);
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String item = itemField.getText();
            double price = Double.parseDouble(priceField.getText());
            double monthlySavingsAmount = monthlySavings.getSavingsAmount();
            int months = calculateMonthsToSave(price, monthlySavingsAmount);
            resultLabel.setText("It will take " + months + " months to save up for " + item + ".");
        }
    }

    public static int calculateMonthsToSave(double price, double monthlySavings) {
        if (monthlySavings <= 0) {
            return -1; // Handle invalid savings amount
        }

        double totalMonths = price / monthlySavings;
        return (int) Math.ceil(totalMonths);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame();
        });
    }
}
