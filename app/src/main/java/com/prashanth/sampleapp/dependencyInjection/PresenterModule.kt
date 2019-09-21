package com.prashanth.sampleapp.dependencyInjection

import com.prashanth.sampleapp.adapter.UserModelListAdapter
import com.prashanth.sampleapp.presenter.UserModelPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    fun provideUserModelListAdapter(): UserModelListAdapter {
        return UserModelListAdapter()
    }

    @Provides
    fun provideSampleResultsPresenterImpl(): UserModelPresenterImpl {
        return UserModelPresenterImpl()
    }

}