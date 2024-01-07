import java.util.List;

public class ExpenseService {
    private ExpenseRepository expenseRepository;

    public ExpenseService() {
        this.expenseRepository = new ExpenseRepositoryImpl();
    }

    public void addExpense(Expense expense) {
        expenseRepository.addExpense(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.getAllExpenses();
    }

    public void updateExpense(Expense expense) {
        expenseRepository.updateExpense(expense);
    }

    public void deleteExpense(int id) {
        expenseRepository.deleteExpense(id);
    }

    public Expense getExpenseById(int id) {
        return expenseRepository.getExpenseById(id);
    }
}
