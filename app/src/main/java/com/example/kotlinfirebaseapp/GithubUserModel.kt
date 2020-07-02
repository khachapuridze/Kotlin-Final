package com.example.kotlinfirebaseapp

import com.google.gson.annotations.SerializedName

class GithubUserModel {
    var login = ""
    var id = 0
    var name = ""
    var company = ""
    @SerializedName("full_name")
    var fullName = ""
    var followers = 0
    var following = 0
    @SerializedName("public_repos")
    var publicRepos = 0
    @SerializedName("avatar_url")
    var avatarUrl = ""

}