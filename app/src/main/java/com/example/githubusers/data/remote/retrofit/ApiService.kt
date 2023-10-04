package com.example.githubusers.data.remote.retrofit

import com.example.githubusers.data.remote.response.DetailUserResponse
import com.example.githubusers.data.remote.response.FollowsResponse
import com.example.githubusers.data.remote.response.GithubResponse
import retrofit2.http.*
import retrofit2.Call

interface ApiService {

    @GET("search/users")
    fun getSearchUser(@Query("q") query: String): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username")username: String): Call<List<FollowsResponse>>

    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username")username: String): Call<List<FollowsResponse>>

}