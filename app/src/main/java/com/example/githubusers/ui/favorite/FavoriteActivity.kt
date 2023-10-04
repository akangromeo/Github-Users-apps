package com.example.githubusers.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.data.local.entity.FavoriteUser
import com.example.githubusers.data.remote.response.User
import com.example.githubusers.databinding.ActivityFavoriteBinding
import com.example.githubusers.ui.adapter.UserAdapter


class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private val favoriteViewModel by viewModels<FavoriteViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar?.title = "Favorite User"

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvFavoriteUser.setHasFixedSize(true)
            rvFavoriteUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavoriteUser.adapter = adapter
        }
        favoriteViewModel.getFavorite()?.observe(this, {
            if (it != null){
                val list = mapList(it)
                adapter.submitList(list)
            }
        })
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User>{
        val listUser = ArrayList<User>()
        for (user in users){
            val userMapped = User(
                user.username,
                user.avatarUrl
            )
            listUser.add(userMapped)
        }
        return listUser
    }
}