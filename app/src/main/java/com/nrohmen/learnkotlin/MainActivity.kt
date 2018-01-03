package com.nrohmen.learnkotlin

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //initialized gson
        val gson = GsonBuilder().create()
        //initialized retrofit
        val retrofit: Retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.github.com/")
                .build()
        val service: Service = retrofit.create(
                Service::class.java)
        val usernameinput = findViewById<TextInputEditText>(R.id.usernameinput)
        val btnok = findViewById<Button>(R.id.btnok)
        val userinput = usernameinput.text
        //get data from github by username
        btnok.setOnClickListener(View.OnClickListener { v ->
            service.getUser("" + userinput)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { user ->
                                getData(user)
                                Toast.makeText(this, userinput, Toast.LENGTH_SHORT).show()
                            },
                            { error ->
                                Log.e("Error", error.message)
                                Toast.makeText(this, "User not Found", Toast.LENGTH_SHORT).show()
                            }
                    )

        })
    }

    private fun getData(user: Github?) {
        val image = findViewById<ImageView>(R.id.image)
        val name = findViewById<TextView>(R.id.name)
        val location = findViewById<TextView>(R.id.location)
        val id = findViewById<TextView>(R.id.id)

        Glide.with(this).load(user?.avatarUrl).into(image) //tampil gambar
        name.text = user?.name // tampil nama
        location.text = user?.location //tampil lokasi
        id.text = user?.id //tampil id
    }
}