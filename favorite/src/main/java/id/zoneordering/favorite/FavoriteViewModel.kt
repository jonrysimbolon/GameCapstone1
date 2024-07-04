package id.zoneordering.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.zoneordering.core.domain.model.Digimon
import id.zoneordering.core.domain.usecase.DigimonUseCase
import kotlinx.coroutines.launch

class FavoriteViewModel(private val digimonUseCase: DigimonUseCase): ViewModel() {

    private val _favoriteDigimon: MutableLiveData<List<Digimon>> = MutableLiveData()
    val favoriteDigimon: MutableLiveData<List<Digimon>> get() = _favoriteDigimon

    init{
        getDigimon()
    }

    private fun getDigimon() {
        try {
            viewModelScope.launch {
                digimonUseCase.getFavoriteDigimon().collect {
                    _favoriteDigimon.value = it
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}