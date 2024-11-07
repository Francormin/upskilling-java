package com.example.demomvc.service;

import com.example.demomvc.model.Transaction;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demomvc.util.ValidationUtil.validateFieldsOnCreation;
import static com.example.demomvc.util.ValidationUtil.validateFieldsOnUpdate;

@Service
public class TransactionService {

    private final List<Transaction> transactions = new ArrayList<>();

    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactions.stream()
            .filter(transaction -> transaction.getId().equals(id))
            .findFirst();
    }

    public void addTransaction(Transaction transaction) throws Exception {
        validateFieldsOnCreation(transaction);
        transactions.add(transaction);
    }

    public int updateTransaction(Long id, Transaction transaction) throws Exception {
        Optional<Transaction> transactionFound = getTransactionById(id);

        if (transactionFound.isPresent()) {
            validateFieldsOnUpdate(transaction);
            Transaction transactionToUpdate = transactionFound.get();

            if (transaction.getAmount() != null) {
                transactionToUpdate.setAmount(transaction.getAmount());
            }
            if (transaction.getDescription() != null) {
                transactionToUpdate.setDescription(transaction.getDescription());
            }
            if (transaction.getDate() != null) {
                transactionToUpdate.setDate(transaction.getDate());
            }

            return 1;
        }

        return 0;
    }

    public int deleteTransaction(Long id) {
        Optional<Transaction> transaction = getTransactionById(id);

        if (transaction.isPresent()) {
            Transaction transactionToDelete = transaction.get();
            transactions.remove(transactionToDelete);
            return 1;
        }

        return 0;
    }

}
