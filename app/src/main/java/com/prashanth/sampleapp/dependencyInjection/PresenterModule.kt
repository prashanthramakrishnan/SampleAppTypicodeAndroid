package com.prashanth.sampleapp.dependencyInjection

import com.prashanth.sampleapp.adapter.UserModelListAdapter
import com.prashanth.sampleapp.presenter.UserModelPresenterImpl
import com.prashanth.sampleapp.presenter.UserModelSearchPresenterImpl
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

    @Provides
    fun provideUserModelSearchPresenterImpl(): UserModelSearchPresenterImpl {
        return UserModelSearchPresenterImpl()
    }

}