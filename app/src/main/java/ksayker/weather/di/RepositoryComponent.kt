package ksayker.weather.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
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

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): RepositoryComponent
    }
}