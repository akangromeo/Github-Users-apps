package com.example.githubusers.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusers.data.local.entity.FavoriteUser
import com.example.githubusers.data.repository.UserRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val userRepository: UserRepository): ViewModel() {

    fun getFavorite() = userRepository.getFavorite()

    fun getFavoriteByUsername(user: String) =
        userRepository.getFavoriteByUsername(user)

    fun saveFavorite(username: String, avatarUrl:String) {
        viewModelScope.launch {
            userRepository.setFavoriteUser(username, avatarUrl)
        }
    }

    fun deleteFavorite(favoriteUser: FavoriteUser){
        viewModelScope.launch {
            userRepository.deleteFavoriteUser(favoriteUser)
        }
    }

}