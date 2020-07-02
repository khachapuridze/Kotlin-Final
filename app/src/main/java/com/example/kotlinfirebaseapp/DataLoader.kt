package com.example.kotlinfirebaseapp
import android.util.Log
import android.util.Log.d
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


object DataLoader {
    var retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl("https://api.github.com/")
        .build()

    var service = retrofit.create(ApiService::class.java)
    fun getRepoList(customCallback: CustomCallback) {
        val call = service.getRepoList()
        call.enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    customCallback.onError(t.message.toString())
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    customCallback.onSuccess(response.body().toString())

                }

            }
        )
    }
    fun getUserRepos(user: String,customCallback: CustomCallback) {
        val call = service.getUserRepos(user)
        call.enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    customCallback.onError(t.message.toString())
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    d("user","${response.body().toString()}")
                    customCallback.onSuccess(response.body().toString())
                }
            }
        )
    }

    fun getUserDetails (user: String,customCallback: CustomCallback) {
        val call = service.getUserDetails(user)
        call.enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    customCallback.onError(t.message.toString())
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    customCallback.onSuccess(response.body().toString())
                }

            }
        )
    }

}
interface ApiService {
    @GET("users/khachapuridze/repos")
    fun getRepoList(): Call<String>
    @GET("users/{user}/repos")
    fun getUserRepos(@Path("user")user: String): Call<String>
    @GET("users/{user}")
    fun getUserDetails(@Path("user")user: String): Call<String>
}