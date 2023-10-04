package com.example.githubusers.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity data class FavoriteUser(
    @PrimaryKey(autoGenerate = false)
    var username: String,
    var avatarUrl: String
) :Parcelable
