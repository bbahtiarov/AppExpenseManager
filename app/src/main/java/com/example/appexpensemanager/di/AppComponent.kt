package com.example.appexpensemanager.di

import com.example.appexpensemanager.data.room.TransactionDatabase
import com.example.appexpensemanager.utils.factories.ViewModelProviderFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun getViewModelFactory() : ViewModelProviderFactory
    fun getDatabase() : TransactionDatabase

}