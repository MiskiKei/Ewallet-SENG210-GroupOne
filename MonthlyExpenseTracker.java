import java.util.Scanner;

public class MonthlyExpenseTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Get the number of expenses for the month
        System.out.print("Enter the number of expenses for the month: ");
        int numExpenses = scanner.nextInt();
        scanner.nextLine(); 
        
        double totalExpense = 0.0;
        
        // Details of each expense
        for (int i = 1; i <= numExpenses; i++) {
            System.out.println("Expense #" + i);
            
            // Expense description
            System.out.print("Enter the expense description: ");
            String description = scanner.nextLine();
            
            // Expense amount
            System.out.print("Enter the expense amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            
            totalExpense += amount;
            
            System.out.println(); // Add blank space so it looks nice
        }
        
        // Print the total monthly expense
        System.out.println("Total Monthly Expense: $" + totalExpense);
        
        scanner.close();
    }
}
