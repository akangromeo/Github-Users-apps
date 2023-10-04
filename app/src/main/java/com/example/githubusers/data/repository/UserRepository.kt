package com.example.githubusers.data.repository

import androidx.lifecycle.LiveData
import com.example.githubusers.data.local.room.UserDao
import com.example.githubusers.data.local.entity.FavoriteUser
import com.example.githubusers.data.utils.AppExecutors


class UserRepository private constructor(
    private val userDao: UserDao,
    private val appExecutors: AppExecutors
) {
   fun getFavorite(): LiveData<List<FavoriteUser>>{
       return userDao.getFavoriteUser()
   }

    fun getFavoriteByUsername(username: String): LiveData<Boolean>{
        return userDao.getFavoriteUserByUsername(username)
    }

    fun setFavoriteUser(username: String, avatarUrl: String){
        appExecutors.diskIO.execute {
            var user = FavoriteUser(
                username,
                avatarUrl
            )
            userDao.insertFavorite(user)
        }
    }

    fun deleteFavoriteUser(user: FavoriteUser){
        appExecutors.diskIO.execute {
            userDao.delete(user)
        }
    }

    companion object{
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userDao: UserDao,
            appExecutors: AppExecutors
        ): UserRepository =
            instance ?: synchronized(this){
                instance ?: UserRepository(userDao, appExecutors)
            }. also { instance = it }
    }

}