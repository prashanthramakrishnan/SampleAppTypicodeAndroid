package com.prashanth.sampleapp.presenter

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import com.prashanth.sampleapp.contracts.APIContract
import com.prashanth.sampleapp.model.UserModel
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserModelSearchPresenterImplTest {

    @Mock
    private lateinit var view: APIContract.UserModelSearchFilterView

    companion object {
        @Before
        @Throws(Exception::class)
        fun setUp() {
            MockitoAnnotations.initMocks(this)
        }
    }

    @Test
    fun whenSearchStringIsPresentInTheList() {
        val presenter = UserModelSearchPresenterImpl("title1")
        presenter.filterTitleResults("title1", userModelList(), view)
        argumentCaptor<ArrayList<UserModel>>().apply {
            verify(view, times(1)).onFilterApplied(capture())
            assertEquals(1, allValues.size)
            assertEquals("Some random title1", allValues[0][0].title)
        }
    }

    @Test
    fun whenSearchStringIsNotPresentInTheList() {
        val presenter = UserModelSearchPresenterImpl("ok")
        presenter.filterTitleResults("ok", userModelList(), view)
        argumentCaptor<ArrayList<UserModel>>().apply {
            verify(view, times(1)).onFilterApplied(capture())
            assertEquals(0, allValues[0].size)
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
}