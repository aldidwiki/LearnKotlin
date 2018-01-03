package com.nrohmen.learnkotlin

import retrofit.http.GET
import retrofit.http.Path
import rx.Observable

interface Service {
    @GET("users/{iduser}")
    fun getUser(@Path("iduser") username: String): Observable<Github>
}