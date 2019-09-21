package com.prashanth.sampleapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserModel(
    @SerializedName("userId") val userId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
): Serializable {
    override fun toString(): String {
        return "UserModel(userId=$userId, id=$id, title='$title', body='$body')"
    }
}
