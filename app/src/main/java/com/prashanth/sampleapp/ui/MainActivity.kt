package com.prashanth.sampleapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.google.android.material.snackbar.Snackbar
import com.prashanth.sampleapp.R
import com.prashanth.sampleapp.SampleApplication
import com.prashanth.sampleapp.adapter.UserModelListAdapter
import com.prashanth.sampleapp.contracts.APIContract
import com.prashanth.sampleapp.model.UserModel
import com.prashanth.sampleapp.presenter.UserModelPresenterImpl
import com.prashanth.sampleapp.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), APIContract.UserModelListView, OnRefreshListener {

    @Inject
    lateinit var presenterImpl: UserModelPresenterImpl

    @Inject
    lateinit var adapter: UserModelListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SampleApplication.component.inject(this)

        swipeRefreshLayout.setOnRefreshListener(this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
    }

    override fun onRefresh() {
        presenterImpl.fetchSampleResults(this)
    }


    override fun callStarted() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun callComplete() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun callFailed(statusCode: Int) {
        swipeRefreshLayout.isRefreshing = false
        showSnackBarErrorMessage(Utils.getErrorString(this, statusCode))
    }

    override fun onResponse(userModelList: ArrayList<UserModel>) {
        adapter.update(userModelList)
    }

    private fun showSnackBarErrorMessage(error: String) {
        Snackbar.make(
            findViewById<View>(android.R.id.content),
            error,
            Snackbar.LENGTH_LONG
        ).show()
    }

}
