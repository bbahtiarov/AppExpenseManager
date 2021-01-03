package com.example.appexpensemanager.data.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "transactionTable")
data class TransactionEntry(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val amount: Int = 0,
    val category: String = "",
    val description: String = "",
    val date: String = ""
) : Serializable