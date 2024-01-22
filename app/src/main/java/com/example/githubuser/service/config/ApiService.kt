package com.example.githubuser.service.config

import com.example.githubuser.service.response.GithubUserDetailResponse
import com.example.githubuser.service.response.GithubUserResponse
import com.example.githubuser.service.response.GithubUsersResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    // Method untuk mengambil data user berdasarkan querystring (username)
    @GET("search/users")
    fun getUsers(@Query("q") name: String): Call<GithubUsersResponse>

    // Method untuk mengambil data detail user berdasarkan username
    @GET("users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<GithubUserDetailResponse>

    // Method untuk mengambil list data followers
    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<List<GithubUserResponse>>

    // Method untuk mengambil list data following
    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<List<GithubUserResponse>>
}