package com.example.appexpensemanager.presentation.expenses

import androidx.lifecycle.*
import com.example.appexpensemanager.data.models.TransactionEntry
import com.example.appexpensemanager.data.repositories.TransactionRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExpensesViewModel @Inject constructor(private val transactionRepository: TransactionRepository) : ViewModel() {
    val transactions: LiveData<List<TransactionEntry>> = transactionRepository.getSavedTransaction().asLiveData()

    fun saveTransaction(transactionEntry: TransactionEntry) = viewModelScope.launch {
        transactionRepository.insertExpense(transactionEntry)
    }

    fun deleteTransaction(transactionEntry: TransactionEntry) = viewModelScope.launch {
        transactionRepository.removeExpense(transactionEntry)
    }
}