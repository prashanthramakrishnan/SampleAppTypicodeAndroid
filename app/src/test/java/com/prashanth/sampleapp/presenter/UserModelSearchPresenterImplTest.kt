package com.prashanth.sampleapp.presenter

import com.prashanth.sampleapp.contracts.APIContract
import com.prashanth.sampleapp.model.UserModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserModelSearchPresenterImplTest {

    @Mock
    private lateinit var view: APIContract.UserModelSearchFilterView

    private var searchString: String = "title1"

    companion object {
        @Before
        @Throws(Exception::class)
        fun setUp() {
            MockitoAnnotations.initMocks(this)
        }
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T

    @Test
    fun getDataAndLoadView() {
        val presenter = UserModelSearchPresenterImpl(searchString)
        presenter.filterTitleResults(searchString, userModelList(), view)
        Mockito.verify(view, times(1)).onFilter(any())
    }


    private fun userModelList(): ArrayList<UserModel> {
        val model1 = UserModel(1, 1234, "Some random title1", "Some random body1")
        val model2 = UserModel(2, 12345, "Some random title2", "Some random body2")
        val modelList = ArrayList<UserModel>()
        modelList.add(model1)
        modelList.add(model2)
        return modelList
    }
}