package com.prashanth.sampleapp.ui

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
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
import com.prashanth.sampleapp.presenter.UserModelSearchPresenterImpl
import com.prashanth.sampleapp.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), APIContract.UserModelListView,
    OnRefreshListener, TextView.OnEditorActionListener, APIContract.UserModelSearchFilterView,
    UserModelListAdapter.OnItemClickListener, UserModelListAdapter.OnItemLongClickListener {

    @Inject
    lateinit var userModelPresenterImpl: UserModelPresenterImpl

    @Inject
    lateinit var searchPresenterImpl: UserModelSearchPresenterImpl

    @Inject
    lateinit var adapter: UserModelListAdapter

    private lateinit var userModelList: ArrayList<UserModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SampleApplication.component.inject(this)

        search_edittext.setOnEditorActionListener(this)
        swipe_refresh_layout.setOnRefreshListener(this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
        adapter.setOnItemClickListener(this, this)
    }

    override fun onRefresh() {
        userModelPresenterImpl.fetchSampleResults(this)
    }

    override fun callStarted() {
        swipe_refresh_layout.isRefreshing = true
    }

    override fun callComplete() {
        swipe_refresh_layout.isRefreshing = false
    }

    override fun callFailed(statusCode: Int) {
        swipe_refresh_layout.isRefreshing = false
        showSnackBarErrorMessage(Utils.getErrorString(this, statusCode))
    }

    override fun onResponse(userModelList: ArrayList<UserModel>) {
        this.userModelList = userModelList
        adapter.update(userModelList)
    }

    override fun onFilter(userModelList: ArrayList<UserModel>) {
        this.userModelList = userModelList
        adapter.update(userModelList)
    }

    override fun onItemClick(item: UserModel) {
        showAlertDialog(item, false)
    }

    override fun onItemLongClick(item: UserModel) {
        showAlertDialog(item, true)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            val searchString = v!!.text.toString()
            if (searchString.isNotEmpty()) {
                onSearchClicked(searchString.toLowerCase(Locale.US))

            }
            return true
        }
        return false
    }

    private fun onSearchClicked(searchString: String) {
        hideKeyboard()
        searchPresenterImpl.filterTitleResults(searchString, userModelList, this)
    }

    private fun showSnackBarErrorMessage(error: String) {
        Snackbar.make(
            findViewById<View>(android.R.id.content),
            error,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun hideKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    private fun showAlertDialog(item: UserModel, shouldDelete: Boolean) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(resources.getString(R.string.app_name))
        if (shouldDelete) {
            alertDialog.setMessage(resources.getString(R.string.delete_post))
        } else {
            alertDialog.setMessage(resources.getString(R.string.post_id, item.id.toString()))
        }
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton(
            resources.getString(R.string.ok)) { dialog, _ ->
            if (shouldDelete) {
                userModelList.remove(item)
                adapter.notifyDataSetChanged()
                dialog.cancel()
            } else {
                dialog.cancel()
            }
        }
        alertDialog.setNegativeButton(resources.getString(R.string.cancel))
        { dialog, _ -> dialog.cancel() }
        val dialog = alertDialog.create()
        dialog.show()
    }

}
