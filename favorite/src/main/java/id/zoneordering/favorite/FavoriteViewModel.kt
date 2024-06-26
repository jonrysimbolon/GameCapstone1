package id.zoneordering.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.zoneordering.core.domain.usecase.DigimonUseCase

class FavoriteViewModel(digimonUseCase: DigimonUseCase): ViewModel() {
    val favoriteDigimon = digimonUseCase.getFavoriteDigimon().asLiveData()
}