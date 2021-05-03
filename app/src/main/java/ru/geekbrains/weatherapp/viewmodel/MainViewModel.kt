package ru.geekbrains.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.weatherapp.model.AppState
import ru.geekbrains.weatherapp.model.Repository
import ru.geekbrains.weatherapp.model.RepositoryImpl

class MainViewModel(private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
                    private val repository: Repository = RepositoryImpl()) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getWeatherFromLocalSource() = getDataFromLocalSource()

    fun getWeatherFromRemoteSource() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            Thread.sleep(1000)
            liveDataToObserve.postValue(AppState.Success(repository.getWeatherFromLocalStorage()))
        }.start()
    }
}