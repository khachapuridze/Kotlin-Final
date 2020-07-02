package com.example.kotlinfirebaseapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.kotlinfirebaseapp.authenticate.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.logOutButton
import kotlinx.android.synthetic.main.activity_main.searchUserEditText
import kotlinx.android.synthetic.main.activity_main.submitButton
import kotlinx.android.synthetic.main.activity_main.userTextView
import kotlinx.android.synthetic.main.user_repos_layout.*


class MainActivity : AppCompatActivity() {


    lateinit var userDetail :GithubUserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        var auth:FirebaseAuth = FirebaseAuth.getInstance()
        userTextView.text = auth.currentUser?.email
        fetchData();
        searchUserEditText.setOnClickListener {
            searchUserEditText.hint = ""
        }
        goToRepoButton.setOnClickListener {
            val intent = Intent(this,UserRepoActivity::class.java)
            intent.putExtra("name",userDetail.login)
            startActivity(intent)
        }
        
        searchUserEditText.setOnClickListener {
            searchUserEditText.hint = ""
        }

        logOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this,
                LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun fetchData() {
        userDetailContainer.visibility = View.INVISIBLE
        submitButton.setOnClickListener {
            userDetailContainer.isEnabled = true
            DataLoader.getUserDetails(searchUserEditText.text.toString(),object : CustomCallback {
                override fun onSuccess(result: String) {
                    super.onSuccess(result)
//                    val model: Array<RepoModel.Data>? = Gson().fromJson(result, Array<RepoModel.Data>::class.java)
                    val model = Gson().fromJson(result,GithubUserModel::class.java)
                    if (model != null) {
                        userDetail = model
                        Glide.with(this@MainActivity).load(userDetail.avatarUrl).into(
                            userImageView
                        )
                        userNameTextView.text = "Name: " + userDetail.name
                        companyTextView.text = "Company: " +  userDetail.company
                        followersTextView.text = "Followers: " + userDetail.followers
                        followingTextView.text = "Following: " + userDetail.following
                        publicReposTextView.text = "Repositories: " +  userDetail.publicRepos
                        userDetailContainer.visibility = View.VISIBLE
                    }
                    searchUserEditText.text.clear()

                }

                override fun onError(error: String) {
                    super.onError(error)
                }
            })

        }
    }

}
