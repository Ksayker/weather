package ksayker.weather.di

import android.app.Application

class ComponentManager(application: Application) {
    var repositoryComponent: RepositoryComponent
        private set

    init {
        repositoryComponent = DaggerRepositoryComponent.builder()
            .application(application)
            .build()
    }
}