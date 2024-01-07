import java.util.List;

public interface ExpenseRepository {
    public void addExpense(Expense expense);
    public List<Expense> getAllExpenses();
    public void updateExpense(Expense expense);
    public void deleteExpense(int id);
    public Expense getExpenseById(int id);
}
