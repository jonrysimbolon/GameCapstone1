package id.zoneordering.gamecapstone1.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.zoneordering.core.data.Resource
import id.zoneordering.core.domain.model.Digimon
import id.zoneordering.core.domain.usecase.DigimonUseCase
import kotlinx.coroutines.launch

class MainViewModel(private val digimonUseCase: DigimonUseCase) : ViewModel() {

    private val _listOfDigimon: MutableLiveData<Resource<List<Digimon>>> = MutableLiveData()
    val listOfDigimon: MutableLiveData<Resource<List<Digimon>>> get() = _listOfDigimon

    fun getDigimon() {
        try {
            viewModelScope.launch {
                digimonUseCase.getAllDigimon().collect {
                    _listOfDigimon.value = it
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}