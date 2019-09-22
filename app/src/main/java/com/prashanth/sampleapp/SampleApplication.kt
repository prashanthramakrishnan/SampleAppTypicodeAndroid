package com.prashanth.sampleapp

import android.app.Application
import com.prashanth.sampleapp.dependencyInjection.*
import timber.log.Timber

open class SampleApplication : Application() {

    companion object {
        lateinit var component: AppDaggerGraph
    }

    protected open fun daggerComponent(application: SampleApplication): DaggerAppDaggerGraph.Builder {
        return DaggerAppDaggerGraph.builder()
            .networkDaggerModule(NetworkDaggerModule(BuildConfig.URL))
            .applicationModule(ApplicationModule(this))
            .presenterModule(PresenterModule())
    }

    override fun onCreate() {
        super.onCreate()
        component = daggerComponent(this).build()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}