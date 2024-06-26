package id.zoneordering.gamecapstone1.di


import id.zoneordering.core.domain.usecase.DigimonInteractor
import id.zoneordering.core.domain.usecase.DigimonUseCase
import id.zoneordering.core.domain.usecase.SettingPreferences
import id.zoneordering.core.domain.usecase.ThemeUseCase
import id.zoneordering.gamecapstone1.detail.DetailViewModel
import id.zoneordering.gamecapstone1.home.MainViewModel
import id.zoneordering.gamecapstone1.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<DigimonUseCase> { DigimonInteractor(get()) }
    factory<ThemeUseCase> { SettingPreferences(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { SettingViewModel(get()) }
}