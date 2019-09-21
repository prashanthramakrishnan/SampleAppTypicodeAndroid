package com.prashanth.sampleapp.dependencyInjection

import com.prashanth.sampleapp.presenter.UserModelPresenterImpl
import com.prashanth.sampleapp.presenter.UserModelSearchPresenterImpl
import com.prashanth.sampleapp.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkDaggerModule::class, ApplicationModule::class, PresenterModule::class])
interface AppDaggerGraph {
    fun inject(target: MainActivity)
    fun inject(sampleResultsPresenterImpl: UserModelPresenterImpl)
    fun inject(searchPresenterImpl: UserModelSearchPresenterImpl)
}