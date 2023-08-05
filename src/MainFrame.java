import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    private JFrame currentFrame; // Keep track of the currently open frame
    private JButton incomeTrackerButton;
    private JButton expenseTrackerButton;
    private JButton monthEstimatorButton;
    private JButton currencyChangeButton;
    private JButton summaryGeneratorButton;

    private MonthlyIncomeTracker incomeTracker;
    private MonthlyExpenseTracker expenseTracker;

    public MainFrame() {
    	SQLStatements Database = new SQLStatements();
//    	Database.createDatabase();
        setTitle("Wallet");
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

        summaryGeneratorButton = new JButton("Summary");
        summaryGeneratorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateSummary();
            }
        });

        setLayout(new FlowLayout());
        add(incomeTrackerButton);
        add(expenseTrackerButton);
        add(monthEstimatorButton);
        add(currencyChangeButton);
        add(summaryGeneratorButton);

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
        incomeTracker = new MonthlyIncomeTracker();
        incomeTracker.setVisible(true);
        currentFrame = incomeTracker; // Set the current frame as the opened frame
    }

    private void openExpenseTracker() {
        expenseTracker = new MonthlyExpenseTracker();
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

    private void generateSummary() {
        if (incomeTracker != null && expenseTracker != null) {
            closeCurrentFrame(); // Close the current frame
            SummaryGenerator summaryGenerator = new SummaryGenerator(incomeTracker, expenseTracker);
            summaryGenerator.setVisible(true);
            currentFrame = summaryGenerator; // Set the current frame as the opened frame
        } else {
            JOptionPane.showMessageDialog(this, "Please open Income Tracker and Expense Tracker first.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean showLoginBox() {
    	boolean isLoggedIn = false;
        LoginPanel loginPanel = new LoginPanel();
        int option = JOptionPane.showOptionDialog(
            null,
            loginPanel,
            "Login or Create Account",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            new String[]{"Login", "Create Account"},
            "Login"
        );

        if (option == 0) { // "Login" button clicked
            boolean loggedIn = loginPanel.login();
            if (loggedIn) {
                SwingUtilities.invokeLater(() -> new MainFrame());
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } else if (option == 1) { // "Create Account" button clicked
            boolean created = loginPanel.createAccount();
            if (created) {
                SwingUtilities.invokeLater(() -> new MainFrame());
            } else {
                JOptionPane.showMessageDialog(null, "Failed to create account.", "Account Creation Failed", JOptionPane.ERROR_MESSAGE);
            }
        } else { // Dialog closed or canceled
            System.exit(0);
        }
        return isLoggedIn;
    }
    
    public static class Main {
        public static void main(String[] args) {
        	if(showLoginBox()) {
                SwingUtilities.invokeLater(() -> {
                    MainFrame mainFrame = new MainFrame();
                });
        	}
        }
    }
}
