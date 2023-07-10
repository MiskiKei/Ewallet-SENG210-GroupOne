import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    private JFrame currentFrame; // Keep track of the currently open frame
    private JButton incomeTrackerButton;
    private JButton expenseTrackerButton;
    private JButton monthEstimatorButton;
    private JButton currencyChangeButton;
    private JButton reportExporterButton;

    public MainFrame() {
        setTitle("Main Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        incomeTrackerButton = new JButton("Income Tracker");
        incomeTrackerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeCurrentFrame(); // Close the current frame
                openIncomeTracker();
            }
        });

        expenseTrackerButton = new JButton("Expense Tracker");
        expenseTrackerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeCurrentFrame(); // Close the current frame
                openExpenseTracker();
            }
        });

        monthEstimatorButton = new JButton("Month Estimator");
        monthEstimatorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeCurrentFrame(); // Close the current frame
                openMonthEstimator();
            }
        });

        currencyChangeButton = new JButton("Currency Change");
        currencyChangeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeCurrentFrame(); // Close the current frame
                openCurrencyChange();
            }
        });

        reportExporterButton = new JButton("Report Exporter");
        reportExporterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeCurrentFrame(); // Close the current frame
                openReportExporter();
            }
        });

        setLayout(new FlowLayout());
        add(incomeTrackerButton);
        add(expenseTrackerButton);
        add(monthEstimatorButton);
        add(currencyChangeButton);
        add(reportExporterButton);

        pack();
        setVisible(true);
    }

    private void closeCurrentFrame() {
        if (currentFrame != null) {
            currentFrame.dispose(); // Close the current frame
            currentFrame = null; // Reset the current frame reference
        }
    }

    private void openIncomeTracker() {
        MonthlyIncomeTracker incomeTracker = new MonthlyIncomeTracker();
        incomeTracker.setVisible(true);
        currentFrame = incomeTracker; // Set the current frame as the opened frame
    }

    private void openExpenseTracker() {
        MonthlyExpenseTracker expenseTracker = new MonthlyExpenseTracker();
        expenseTracker.setVisible(true);
        currentFrame = expenseTracker; // Set the current frame as the opened frame
    }

    private void openMonthEstimator() {
        MonthlyIncomeTracker incomeTracker = null;
        MonthlyExpenseTracker expenseTracker = null;

        // Check if the corresponding classes are already created
        for (Frame frame : JFrame.getFrames()) {
            if (frame instanceof MonthlyIncomeTracker) {
                incomeTracker = (MonthlyIncomeTracker) frame;
            } else if (frame instanceof MonthlyExpenseTracker) {
                expenseTracker = (MonthlyExpenseTracker) frame;
            }
        }

        if (incomeTracker != null && expenseTracker != null) {
            MonthlySavings monthlySavings = new MonthlySavings(incomeTracker, expenseTracker);
            new MonthEstimator(incomeTracker, expenseTracker).frame.setVisible(true);
            currentFrame = new MonthEstimator(incomeTracker, expenseTracker).frame; // Set the current frame as the opened frame
        } else {
            JOptionPane.showMessageDialog(this, "Please open Income Tracker and Expense Tracker first.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openCurrencyChange() {
        DropDownCurrencyChange currencyChange = new DropDownCurrencyChange();
        currencyChange.setVisible(true);
        currentFrame = currencyChange; // Set the current frame as the opened frame
    }

    private void openReportExporter() {
        ReportExporter reportExporter = new ReportExporter();
        reportExporter.setVisible(true);
        currentFrame = reportExporter; // Set the current frame as the opened frame
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame();
            }
        });
    }
}
