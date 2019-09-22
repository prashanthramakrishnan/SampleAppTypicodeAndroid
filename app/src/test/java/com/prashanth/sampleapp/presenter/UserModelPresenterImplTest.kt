package com.prashanth.sampleapp.presenter

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import com.prashanth.sampleapp.contracts.APIContract
import com.prashanth.sampleapp.model.UserModel
import com.prashanth.sampleapp.network.SampleResultsAPI
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.net.SocketTimeoutException
import java.net.UnknownHostException

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
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        }
    }

    @Test
    fun fetchDataAndLoadView() {
        val presenter = UserModelPresenterImpl(provideSampleResultsAPI(true))
        presenter.fetchSampleResults(view)
        verify(view, times(1)).callComplete()
        argumentCaptor<ArrayList<UserModel>>().apply {
            verify(view, times(1)).onAPIResponse(capture())
            assertEquals(2, allValues[0].size)
            assertEquals(1234, allValues[0][0].id)
        }
    }

    @Test
    fun fetchDataAndLoadViewFailSocketTimeoutException() {
        val presenter = UserModelPresenterImpl(provideSampleResultsAPI(false))
        presenter.fetchSampleResults(view)
        argumentCaptor<Int>().apply {
            verify(view, times(1)).callFailed(capture())
            assertEquals(150, allValues[0])
        }
    }

    @Test
    fun fetchDataAndLoadViewFailUnknownHostException() {
        val presenter = UserModelPresenterImpl(MockAPIFailureUnknownHostException())
        presenter.fetchSampleResults(view)
        argumentCaptor<Int>().apply {
            verify(view, times(1)).callFailed(capture())
            assertEquals(100, allValues[0])
        }
    }

    @Test
    fun fetchDataAndLoadViewFailAnyOtherException() {
        val presenter = UserModelPresenterImpl(MockAPIFailureAnyOtherException())
        presenter.fetchSampleResults(view)
        argumentCaptor<Int>().apply {
            verify(view, times(1)).callFailed(capture())
            assertEquals(0, allValues[0])
        }
    }


    private fun provideSampleResultsAPI(success: Boolean): SampleResultsAPI {
        if (success) {
            return MockAPISuccess()
        }
        return MockAPIFailureSocketTimeoutException()
    }

    private fun userModelList(): ArrayList<UserModel> {
        val model1 = UserModel(1, 1234, "Some random title1", "Some random body1")
        val model2 = UserModel(2, 12345, "Some random title2", "Some random body2")
        val modelList = ArrayList<UserModel>()
        modelList.add(model1)
        modelList.add(model2)
        return modelList
    }


    private inner class MockAPISuccess : SampleResultsAPI {

        override fun getSampleResults(): Observable<ArrayList<UserModel>> {
            return Observable.just(userModelList())
        }
    }


    private inner class MockAPIFailureSocketTimeoutException : SampleResultsAPI {

        override fun getSampleResults(): Observable<ArrayList<UserModel>> {
            return Observable.error { SocketTimeoutException() }
        }
    }

    private inner class MockAPIFailureUnknownHostException : SampleResultsAPI {

        override fun getSampleResults(): Observable<ArrayList<UserModel>> {
            return Observable.error { UnknownHostException() }
        }
    }

    private inner class MockAPIFailureAnyOtherException : SampleResultsAPI {

        override fun getSampleResults(): Observable<ArrayList<UserModel>> {
            return Observable.error { RuntimeException() }
        }
    }
}