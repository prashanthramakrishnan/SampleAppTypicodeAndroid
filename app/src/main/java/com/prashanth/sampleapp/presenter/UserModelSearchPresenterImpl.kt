package com.prashanth.sampleapp.presenter

import androidx.annotation.VisibleForTesting
import com.prashanth.sampleapp.SampleApplication
import com.prashanth.sampleapp.contracts.APIContract
import com.prashanth.sampleapp.model.UserModel
import java.util.*

class UserModelSearchPresenterImpl : APIContract.UserModelSearchPresenter {

    private lateinit var view: APIContract.UserModelSearchFilterView

    private lateinit var userModelList: ArrayList<UserModel>

    private lateinit var searchString: String

    constructor() {
        SampleApplication.component.inject(this)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    constructor(searchString: String) {
        this.searchString = searchString
    }

    override fun filterTitleResults(
        searchString: String,
        userModelList: ArrayList<UserModel>,
        view: APIContract.UserModelSearchFilterView
    ) {
        this.view = view
        this.userModelList = userModelList

        val monthList: List<UserModel> =
            userModelList.filter { userModel -> userModel.title.toLowerCase(Locale.US).contains(searchString) }

        this.view.onFilterApplied(monthList as ArrayList<UserModel>)
    }

    override fun onDestroy() {
    }
}