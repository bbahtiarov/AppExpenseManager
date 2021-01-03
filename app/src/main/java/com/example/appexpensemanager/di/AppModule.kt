package com.example.appexpensemanager.di

import android.content.Context
import androidx.room.Room
import com.example.appexpensemanager.data.repositories.TransactionRepository
import com.example.appexpensemanager.data.room.TransactionDatabase
import com.example.appexpensemanager.utils.factories.ViewModelProviderFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideTransactionDatabase (
        context: Context
    ) = Room.databaseBuilder(context.applicationContext, TransactionDatabase::class.java, "transaction_db").build()

    @Singleton
    @Provides
    fun provideNewsRepo(database: TransactionDatabase) = TransactionRepository(database)

    @Singleton
    @Provides
    fun provideBusinessFactory(
        transactionRepository: TransactionRepository
    ) = ViewModelProviderFactory(transactionRepository)

}