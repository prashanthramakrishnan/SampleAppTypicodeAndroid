package com.prashanth.sampleapp.contracts

import com.prashanth.sampleapp.model.UserModel

interface APIContract {

    interface View {

        fun callStarted()

        fun callComplete()

        fun callFailed(statusCode: Int)
    }

    interface UserModelListView : View {

        fun onResponse(userModelList: ArrayList<UserModel>)
    }

    interface Presenter {

        fun onDestroy()
    }

    interface UserModelPresenter : Presenter {

        fun fetchSampleResults(view: UserModelListView)
    }

}