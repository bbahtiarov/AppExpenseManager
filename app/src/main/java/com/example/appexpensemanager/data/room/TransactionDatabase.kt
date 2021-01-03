package com.example.appexpensemanager.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.appexpensemanager.data.models.TransactionEntry

@Database(entities = [TransactionEntry::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TransactionDatabase : RoomDatabase(){
    abstract  fun getTransactionDao() : TransactionDao
}