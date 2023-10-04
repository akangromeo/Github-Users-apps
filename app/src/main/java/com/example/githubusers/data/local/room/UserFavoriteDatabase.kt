package com.example.githubusers.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubusers.data.local.entity.FavoriteUser
import com.example.githubusers.data.remote.response.User

@Database(entities = [FavoriteUser::class], version = 1)
abstract class UserFavoriteDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object{
        @Volatile
        private var INSTANCE: UserFavoriteDatabase? = null
        fun getDatabase(context: Context): UserFavoriteDatabase {
            if (INSTANCE == null){
                synchronized(FavoriteUser::class.java){
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            UserFavoriteDatabase::class.java, "FavoriteUser.db"
                        ).build()
                    }
                }
            return INSTANCE as UserFavoriteDatabase
        }

    }
}