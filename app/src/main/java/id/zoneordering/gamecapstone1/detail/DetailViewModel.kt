package id.zoneordering.gamecapstone1.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.zoneordering.core.domain.model.Digimon
import id.zoneordering.core.domain.usecase.DigimonUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DetailViewModel(private val digimonUseCase: DigimonUseCase) : ViewModel() {

    private val _digimon: MutableLiveData<Digimon> = MutableLiveData()
    val digimon: MutableLiveData<Digimon> get() = _digimon

    fun setFavoriteDigimon(digimon: Digimon, newStatus: Boolean) =
        viewModelScope.launch {
            digimonUseCase.setFavoriteDigimon(digimon, newStatus)
            getDigimonByName(digimon.name)
        }

    fun getDigimonByName(name: String) {
        try {
            viewModelScope.launch {
                val digimonResult: Flow<Digimon> = digimonUseCase.getDigimon(name)
                digimonResult.collect {
                    _digimon.value = it
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}