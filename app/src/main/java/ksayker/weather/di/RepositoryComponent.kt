package ksayker.weather.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ksayker.weather.model.repository.CitiesRepositoryImpl
import ksayker.weather.model.repository.WeatherRepositoryImpl
import ksayker.weather.view.fragment.AddCityViewModel
import ksayker.weather.view.fragment.CitiesListViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface RepositoryComponent {

    fun inject(viewModel: CitiesListViewModel)

    fun inject(viewModel: AddCityViewModel)

    fun inject(repository: WeatherRepositoryImpl)

    fun inject(citiesRepositoryImpl: CitiesRepositoryImpl)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): RepositoryComponent
    }
}