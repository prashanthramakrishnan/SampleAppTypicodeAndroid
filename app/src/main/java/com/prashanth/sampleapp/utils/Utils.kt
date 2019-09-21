package com.prashanth.sampleapp.utils

import android.content.Context
import com.prashanth.sampleapp.R
import retrofit2.HttpException
import java.net.UnknownHostException

class Utils {

    companion object {

        fun returnResponseCode(throwable: Throwable): Int {
            return when (throwable) {
                is HttpException -> throwable.response()!!.code()
                is UnknownHostException -> return 100
                else -> return 0
            }
        }

        fun getErrorString(context: Context, statusCode: Int): String {
            return when (statusCode) {
                100 -> context.getString(R.string.no_internet)
                0 -> context.getString(R.string.unknown_error)
                404 -> context.getString(R.string.problem_with_server)
                else -> context.getString(R.string.generic_error)
            }
        }

    }

}