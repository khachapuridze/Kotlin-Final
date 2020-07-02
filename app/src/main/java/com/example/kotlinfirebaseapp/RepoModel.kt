package com.example.kotlinfirebaseapp

import com.google.gson.annotations.SerializedName

class RepoModel {
    lateinit var data: ArrayList<Data>
    class Data() {
        var id = 0
        var name = ""
        @SerializedName("full_name")
        var fullName = ""
        lateinit var owner: Owner
        class Owner {
            var id = 0
            @SerializedName("avatar_url")
            var avatarUrl = ""
        }
        @SerializedName("stargazers_count")
        var stargazersCount = 0
        var forks = 0
    }


}