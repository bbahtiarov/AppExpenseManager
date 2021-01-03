package com.example.appexpensemanager.presentation.addTransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appexpensemanager.data.models.TransactionEntry
import com.example.appexpensemanager.data.repositories.TransactionRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddTransactionViewModel @Inject constructor(private val transactionRepository: TransactionRepository) : ViewModel() {

    fun insert(transaction: TransactionEntry) = viewModelScope.launch {
        transactionRepository.insertExpense(transaction)
    }

}