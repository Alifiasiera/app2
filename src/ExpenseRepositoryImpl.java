import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseRepositoryImpl implements ExpenseRepository {
    private static final String INSERT_EXPENSE_SQL = "INSERT INTO expenses (description, amount, date) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_EXPENSES_SQL = "SELECT * FROM expenses";
    private static final String UPDATE_EXPENSE_SQL = "UPDATE expenses SET description = ?, amount = ?, date = ? WHERE id = ?";
    private static final String DELETE_EXPENSE_SQL = "DELETE FROM expenses WHERE id = ?";
    private static final String SELECT_EXPENSE_BY_ID_SQL = "SELECT * FROM expenses WHERE id = ?";

    public void addExpense(Expense expense) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EXPENSE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, expense.getDescription());
            preparedStatement.setDouble(2, expense.getAmount());
            preparedStatement.setDate(3, new java.sql.Date(expense.getDate().getTime()));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating expense failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    expense.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating expense failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_EXPENSES_SQL)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("description");
                double amount = resultSet.getDouble("amount");
                Date date = resultSet.getDate("date");
                expenses.add(new Expense(id, description, amount, date));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenses;
    }

    public void updateExpense(Expense expense) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EXPENSE_SQL)) {

            preparedStatement.setString(1, expense.getDescription());
            preparedStatement.setDouble(2, expense.getAmount());
            preparedStatement.setDate(3, new java.sql.Date(expense.getDate().getTime()));
            preparedStatement.setInt(4, expense.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteExpense(int id) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EXPENSE_SQL)) {

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Expense getExpenseById(int id) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EXPENSE_BY_ID_SQL)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String description = resultSet.getString("description");
                    double amount = resultSet.getDouble("amount");
                    Date date = resultSet.getDate("date");
                    return new Expense(id, description, amount, date);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
