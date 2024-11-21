package dao.impl;

import config.DatabaseConfig;
import config.ExpenseCategorySerializer;
import dao.DAO;
import entities.Expense;
import entities.ExpenseCategory;
import exceptions.InvalidExpenseAmountException;
import utils.NotificationUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO implements DAO<Expense> {
    private final Connection conn;

    public ExpenseDAO() {
        this.conn = DatabaseConfig.getDatabaseConnection();
    }

    @Override
    public List<Expense> getAll() {
        List<Expense> expenses = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM expenses");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                Double amount = rs.getDouble("amount");
                String date = rs.getString("date");
                ExpenseCategory expenseCategory = ExpenseCategorySerializer.deserialize(
                        rs.getString("expense_category")
                );
                String description = rs.getString("description");

                Expense expense = new Expense(id, amount, date, expenseCategory, description);
                expenses.add(expense);
            }

            rs.close();
            ps.close();
        } catch (SQLException | InvalidExpenseAmountException e) {
            throw new RuntimeException(e);
        }

        return expenses;
    }

    @Override
    public Expense getById(int id) {
        ExpenseCategory expenseCategory = new ExpenseCategory();
        Expense expense = new Expense(expenseCategory);

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM expenses WHERE id = ?");

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                expense.setId(rs.getInt("id"));
                expense.setAmount(rs.getDouble("amount"));
                expense.setDate(rs.getString("date"));
                expense.setCategory(ExpenseCategorySerializer.deserialize(
                        rs.getString("expense_category")
                ));
                expense.setDescription(rs.getString("description"));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return expense;
    }

    @Override
    public void add(Expense expense) {
        Expense newExpense = new Expense();
        newExpense.setAmount(expense.getAmount());
        newExpense.setDate(expense.getDate());
        newExpense.setCategory(expense.getCategory());
        newExpense.setDescription(expense.getDescription());

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO expenses (amount, date, expense_category, description) VALUES (?, ?, ?, ?)"
            );

            ps.setDouble(1, newExpense.getAmount());
            ps.setString(2, newExpense.getDate());
            ps.setString(3, ExpenseCategorySerializer.serialize(newExpense.getCategory()));
            ps.setString(4, newExpense.getDescription());
            ps.executeUpdate();

            ps.close();
            NotificationUtils.showOnSuccess("Gasto agregado exitosamente.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int id, Expense expense) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE expenses SET amount = ?, date = ?, expense_category = ?, description = ? WHERE id = ?"
            );

            ps.setDouble(1, expense.getAmount());
            ps.setString(2, expense.getDate());
            ps.setObject(3, expense.getCategory());
            ps.setString(4, expense.getDescription());
            ps.setInt(5, id);
            int intReturned = ps.executeUpdate();

            ps.close();
            if (intReturned == 1) {
                NotificationUtils.showOnSuccess("Gasto con ID " + id + " actualizado exitosamente.");
            } else {
                NotificationUtils.showOnError("Gasto con ID " + id + " no encontrado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM expenses WHERE id = ?");

            ps.setInt(1, id);
            int intReturned = ps.executeUpdate();

            ps.close();
            if (intReturned == 1) {
                NotificationUtils.showOnSuccess("Gasto con ID " + id + " eliminado exitosamente.");
            } else {
                NotificationUtils.showOnError("Gasto con ID " + id + " no encontrado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}