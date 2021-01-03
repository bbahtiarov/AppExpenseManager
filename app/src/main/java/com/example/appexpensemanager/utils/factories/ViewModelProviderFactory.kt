package com.example.appexpensemanager.utils.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appexpensemanager.data.repositories.TransactionRepository
import com.example.appexpensemanager.presentation.statistics.StatisticViewModel
import com.example.appexpensemanager.presentation.addTransaction.AddTransactionViewModel
import com.example.appexpensemanager.presentation.expenses.ExpensesViewModel
import com.example.appexpensemanager.presentation.updateTransaction.UpdateTransactionViewModel

class ViewModelProviderFactory (
    private val transactionRepository: TransactionRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddTransactionViewModel::class.java)){
            return AddTransactionViewModel(transactionRepository) as T
        }
        if(modelClass.isAssignableFrom(StatisticViewModel::class.java)){
            return StatisticViewModel(transactionRepository) as T
        }
        if(modelClass.isAssignableFrom(ExpensesViewModel::class.java)){
            return ExpensesViewModel(transactionRepository) as T
        }
        if(modelClass.isAssignableFrom(UpdateTransactionViewModel::class.java)){
            return UpdateTransactionViewModel(transactionRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}