package com.example.appexpensemanager.data.room

import androidx.room.*
import com.example.appexpensemanager.data.models.TransactionEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT SUM(amount) FROM transactionTable WHERE category=:category")
    fun getTotalAmountByCategory(category: String) : Flow<Int>

    @Query("select * from transactionTable")
    fun loadAllTransactions(): Flow<List<TransactionEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(transactionEntry: TransactionEntry?): Long

    @Delete
    suspend fun deleteExpense(transactionEntry: TransactionEntry?)

    @Update()
    suspend fun updateExpenseDetails(transactionEntry: TransactionEntry?)

}