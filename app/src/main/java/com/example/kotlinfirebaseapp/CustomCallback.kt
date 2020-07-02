package com.example.kotlinfirebaseapp

interface CustomCallback {
    fun onSuccess(result: String){}
    fun onError(error: String){}
}