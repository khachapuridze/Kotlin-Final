package com.example.kotlinfirebaseapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinfirebaseapp.authenticate.LoginActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import kotlinx.android.synthetic.main.user_repos_layout.*
import kotlin.collections.ArrayList


class UserRepoActivity : AppCompatActivity() {

    lateinit var providers:List<AuthUI.IdpConfig>
    val REQUEST_CODE = 1999

    //    Recycler adapter
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var holder: RecyclerViewAdapter.ViewHolder
    private var repoList = ArrayList<RepoModel.Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_repos_layout)
        init()
    }

    private fun init() {
        var auth:FirebaseAuth = FirebaseAuth.getInstance()
        userTextView.text = auth.currentUser?.email
        fetchData();
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerViewAdapter(repoList,this)
        recyclerView.adapter = adapter

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

        goBackButton.setOnClickListener {
            onBackPressed()
        }

    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun fetchData() {
        searchUserEditText.hideKeyboard()
        val repoName:String = intent.getStringExtra("name")
        submitButton.setOnClickListener {

            DataLoader.getUserRepos(searchUserEditText.text.toString(),object : CustomCallback {
                override fun onSuccess(result: String) {
                    super.onSuccess(result)
                    val model: Array<RepoModel.Data>? = Gson().fromJson(result, Array<RepoModel.Data>::class.java)
                    d("name","${model}")
                    if (model != null) {
                        repoList.clear();
                        repoList.addAll(model)
                        adapter.notifyDataSetChanged()
                    }
                    searchUserEditText.text.clear()

                }

                override fun onError(error: String) {
                    super.onError(error)
                }
            })

        }
        DataLoader.getUserRepos(repoName, object :CustomCallback {
            override fun onSuccess(result: String) {
                super.onSuccess(result)
                val model: Array<RepoModel.Data>? = Gson().fromJson(result, Array<RepoModel.Data>::class.java)
                if (model != null) {
                    repoList.addAll(model)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onError(error: String) {
                super.onError(error)
            }
        })
    }

}
