package com.example.appexpensemanager.presentation.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.appexpensemanager.data.repositories.TransactionRepository
import javax.inject.Inject

class StatisticViewModel @Inject constructor(private val transactionRepository: TransactionRepository) :
    ViewModel() {

    val savedTransactions = transactionRepository.getSavedTransaction().asLiveData()
    val totalAmountHealth = transactionRepository.getTotalAmountByCategory("Здоровье").asLiveData()
    val totalAmountSupermarkets = transactionRepository.getTotalAmountByCategory("Супермаркеты").asLiveData()
    val totalAmountTravel = transactionRepository.getTotalAmountByCategory("Путешествия").asLiveData()
    val totalAmountClothes = transactionRepository.getTotalAmountByCategory("Одежда").asLiveData()
    val totalAmountMovies = transactionRepository.getTotalAmountByCategory("Фильмы").asLiveData()
    val totalAmountRestaurants = transactionRepository.getTotalAmountByCategory("Рестораны и кафе").asLiveData()
    val totalAmountOther = transactionRepository.getTotalAmountByCategory("Другое").asLiveData()

}