package com.example.githubusers.di

import android.content.Context
import com.example.githubusers.data.local.room.UserFavoriteDatabase
import com.example.githubusers.data.remote.retrofit.ApiConfig
import com.example.githubusers.data.remote.retrofit.ApiService
import com.example.githubusers.data.repository.UserRepository
import com.example.githubusers.data.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): UserRepository{
        val database = UserFavoriteDatabase.getDatabase(context)
        val dao = database.userDao()
        val appExecutors = AppExecutors()
        return UserRepository.getInstance(dao, appExecutors)
    }
}