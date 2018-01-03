package com.nrohmen.learnkotlin

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Github { //Model

    @SerializedName("avatar_url")
    @Expose
    open var avatarUrl: String? = null

    @SerializedName("name")
    @Expose
    open var name : String? = null

    @SerializedName("location")
    @Expose
    open var location: String? = null

    @SerializedName ("id")
    @Expose
    open var id: String? = null

}
