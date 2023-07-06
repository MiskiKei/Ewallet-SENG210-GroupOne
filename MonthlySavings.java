public class MonthlySavings {
    private static MonthlyIncomeTracker incomeTracker;
    private static MonthlyExpenseTracker expenseTracker;

    public MonthlySavings(MonthlyIncomeTracker incomeTracker, MonthlyExpenseTracker expenseTracker) {
        this.incomeTracker = incomeTracker;
        this.expenseTracker = expenseTracker;
    }

    public static double getTotalIncome() {
        double totalIncome = 0.0;

        if (incomeTracker != null) {
            for (MonthlyIncomeTracker.IncomeEntry entry : incomeTracker.incomeEntries) {
                totalIncome += entry.getAmount();
            }
        }

        return totalIncome;
    }

    public static double getTotalExpenses() {
        if (expenseTracker != null) {
            double totalExpenses = expenseTracker.totalExpense;
            return totalExpenses;
        }

        return 0.0;
    }






    public static double getSavingsAmount() {
        double totalIncome = getTotalIncome();
        double totalExpenses = getTotalExpenses();
        return totalIncome - totalExpenses;
    }

    public static void main(String[] args) {

    }
}

