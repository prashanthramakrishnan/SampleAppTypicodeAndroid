package com.prashanth.sampleapp.network


import com.prashanth.sampleapp.model.UserModel
import io.reactivex.Observable
import retrofit2.http.GET


interface SampleResultsAPI {

    @GET("/posts/")
    fun getSampleResults(): Observable<ArrayList<UserModel>>

}