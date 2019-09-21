package com.prashanth.sampleapp.presenter

import androidx.annotation.VisibleForTesting
import com.prashanth.sampleapp.SampleApplication
import com.prashanth.sampleapp.contracts.APIContract
import com.prashanth.sampleapp.model.UserModel
import com.prashanth.sampleapp.network.SampleResultsAPI
import com.prashanth.sampleapp.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class UserModelPresenterImpl : APIContract.UserModelPresenter {

    @Inject
    lateinit var api: SampleResultsAPI

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private lateinit var view: APIContract.UserModelListView

    constructor() {
        SampleApplication.component.inject(this)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    constructor(api: SampleResultsAPI) {
        this.api = api
    }


    override fun onDestroy() {
        compositeDisposable.clear()
    }

    override fun fetchSampleResults(view: APIContract.UserModelListView) {
        this.view = view
        view.callStarted()
        val disposable = api.getSampleResults()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ArrayList<UserModel>>() {
                override fun onNext(response: ArrayList<UserModel>) {
                    view.onResponse(response)
                }

                override fun onError(e: Throwable) {
                    Timber.e(e, "Call failed")
                    view.callFailed(Utils.returnResponseCode(e))
                }

                override fun onComplete() {
                    view.callComplete()
                }
            })
        compositeDisposable.add(disposable)
    }
}