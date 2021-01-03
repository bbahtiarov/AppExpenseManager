package com.example.appexpensemanager.presentation.updateTransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appexpensemanager.data.models.TransactionEntry
import com.example.appexpensemanager.data.repositories.TransactionRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateTransactionViewModel @Inject constructor(private val transactionRepository: TransactionRepository) : ViewModel() {

    fun update(transactionEntry: TransactionEntry) = viewModelScope.launch {
        transactionRepository.updateExpenseDetails(transactionEntry)
    }

}