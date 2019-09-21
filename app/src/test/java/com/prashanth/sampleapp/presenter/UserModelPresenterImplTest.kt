package com.prashanth.sampleapp.presenter

import com.prashanth.sampleapp.contracts.APIContract
import com.prashanth.sampleapp.model.UserModel
import com.prashanth.sampleapp.network.SampleResultsAPI
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserModelPresenterImplTest {

    @Mock
    private lateinit var view: APIContract.UserModelListView

    companion object {
        @Before
        @Throws(Exception::class)
        fun setUp() {
            MockitoAnnotations.initMocks(this)
        }

        @BeforeClass
        @JvmStatic
        fun setupClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { schedulerCallable -> Schedulers.trampoline() }
            RxJavaPlugins.setIoSchedulerHandler { scheduler -> Schedulers.trampoline() }
        }
    }

    @Test
    fun getDataAndLoadView() {
        val presenter = UserModelPresenterImpl(provideSampleResultsAPI(true))
        presenter.fetchSampleResults(view)
        Mockito.verify(view, times(1)).callComplete()
        Mockito.verify(view, times(1)).onResponse(userModelList())
    }

    @Test
    fun getDataAndLoadViewFail() {
        val presenter = UserModelPresenterImpl(provideSampleResultsAPI(false))
        presenter.fetchSampleResults(view)
        Mockito.verify(view, times(1)).callFailed(0)
    }

    fun provideSampleResultsAPI(success: Boolean): SampleResultsAPI {
        if (success) {
            return MockAPISuccess()
        }
        return MockAPIFailure()
    }

    private inner class MockAPISuccess : SampleResultsAPI {

        override fun getSampleResults(): Observable<ArrayList<UserModel>> {
            return Observable.just(userModelList())
        }
    }

    private fun userModelList(): ArrayList<UserModel> {
        val model1 = UserModel(1, 1234, "Some random title1", "Some random body1")
        val model2 = UserModel(2, 12345, "Some random title2", "Some random body2")
        val modelList = ArrayList<UserModel>()
        modelList.add(model1)
        modelList.add(model2)
        return modelList
    }

    private inner class MockAPIFailure : SampleResultsAPI {

        override fun getSampleResults(): Observable<ArrayList<UserModel>> {
            return Observable.error(Throwable("Error"))
        }
    }
}