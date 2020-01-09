package ksayker.weather.di

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ksayker.weather.model.repository.CitiesRepository
import ksayker.weather.model.repository.CitiesRepositoryImpl
import ksayker.weather.model.repository.WeatherRepository
import ksayker.weather.model.repository.WeatherRepositoryImpl
import ksayker.weather.model.repository.db.DataBase
import ksayker.weather.model.repository.rest.NetworkService
import ksayker.weather.view.helper.MessageFactory
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(context: Context) : WeatherRepository {
        return WeatherRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideCitiesRepository(context: Context) : CitiesRepository {
        return CitiesRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): DataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            DataBase::class.java,
            "database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService {
        return NetworkService()
    }

    @Provides
    @Singleton
    fun provideMessageFactory(context: Context): MessageFactory{
        return MessageFactory(context)
    }
}