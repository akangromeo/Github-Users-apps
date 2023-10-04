package com.example.githubusers.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubusers.data.local.entity.FavoriteUser



@Dao
interface UserDao {

    @Query("SELECT * FROM FavoriteUser")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(favoriteUser: FavoriteUser)

    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT EXISTS(SELECT * FROM FavoriteUser WHERE username = :username)")
    fun getFavoriteUserByUsername(username: String): LiveData<Boolean>
}