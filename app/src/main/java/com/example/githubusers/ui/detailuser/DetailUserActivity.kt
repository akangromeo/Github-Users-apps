package com.example.githubusers.ui.detailuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubusers.R
import com.example.githubusers.data.local.entity.FavoriteUser
import com.example.githubusers.data.remote.response.DetailUserResponse
import com.example.githubusers.databinding.ActivityDetailUserBinding
import com.example.githubusers.ui.adapter.FollowsPagerAdapter
import com.example.githubusers.ui.favorite.ViewModelFactory
import com.example.githubusers.ui.favorite.FavoriteViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val detailUserViewModel by viewModels<DetailUserViewModel>()
    private val favoriteViewModel by viewModels<FavoriteViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val login = intent.getStringExtra(NAME) ?: "null"
        val avatarUrl = intent.getStringExtra(AVATAR_URL) ?: "null"

        detailUserViewModel.getDetailUser(login)

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailUserViewModel.user.observe(this) { user ->
            if (user != null) {
                setDetailUserData(user)
            }
        }

        detailUserViewModel.toastbarText.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        val followsPagerAdapter = FollowsPagerAdapter(this)
        followsPagerAdapter.username = login
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = followsPagerAdapter
        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val toggleButton = binding.toggleButton
        var isFavorite = true
        favoriteViewModel.getFavoriteByUsername(login).observe(this) {
            if (it) {
                isFavorite = true
                toggleButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        toggleButton.context,
                        R.drawable.ic_favorite
                    )
                )
            } else {
                isFavorite = false
                toggleButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        toggleButton.context,
                        R.drawable.ic_favorite_border
                    )
                )
            }
        }

        val users = FavoriteUser(login, avatarUrl)
        binding.toggleButton.setOnClickListener {
            if (isFavorite) {
                favoriteViewModel.deleteFavorite(users)
            } else {
                favoriteViewModel.saveFavorite(login, avatarUrl)
            }
        }

        binding.ivShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this Mobile Legend Hero History App!")
            intent.putExtra(Intent.EXTRA_TEXT, "https://api.github.com/search/users?q=${login}")
            startActivity(Intent.createChooser(intent, "Share Via"))
        }

    }

    private fun setDetailUserData(user: DetailUserResponse) {
        binding.apply {

            Glide.with(ivUserDetail.context)
                .load(user.avatarUrl)
                .into(
                    ivUserDetail
                )
            tvNameDetail.text = user.name

            tvUsernameDetail.text = user.login

            tvFollowersDetail.text = user.followers.toString() + " Followers"

            tvFollowingDetail.text = user.following.toString() + " Following"

            viewPager.visibility = View.VISIBLE
            tabLayout.visibility = View.VISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val NAME = "name"
        const val AVATAR_URL = "avatar_url"
        const val ID = "id"
        private val TAB_TITLES = intArrayOf(
            R.string.followers_tab,
            R.string.following_tab
        )
    }

}