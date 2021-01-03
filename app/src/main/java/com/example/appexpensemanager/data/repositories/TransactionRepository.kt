package com.example.appexpensemanager.data.repositories

import com.example.appexpensemanager.data.models.TransactionEntry
import com.example.appexpensemanager.data.room.TransactionDatabase

class TransactionRepository(private val db: TransactionDatabase) {

    fun getTotalAmountByCategory(category: String) = db.getTransactionDao().getTotalAmountByCategory(category)

    fun getSavedTransaction() = db.getTransactionDao().loadAllTransactions()

    suspend fun insertExpense(transactionEntry: TransactionEntry) =
        db.getTransactionDao().insertExpense(transactionEntry)

    suspend fun removeExpense(transactionEntry: TransactionEntry) =
        db.getTransactionDao().deleteExpense(transactionEntry)

    suspend fun updateExpenseDetails(transactionEntry: TransactionEntry) =
        db.getTransactionDao().updateExpenseDetails(transactionEntry)

}