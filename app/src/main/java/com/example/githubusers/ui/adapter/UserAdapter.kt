package com.example.githubusers.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubusers.data.remote.response.User
import com.example.githubusers.databinding.ItemUserBinding
import com.example.githubusers.ui.detailuser.DetailUserActivity

class UserAdapter:  ListAdapter<User, UserAdapter.MyViewHolder>(DIFF_CALLBACK){

    class MyViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
                fun bind(user: User){
                    binding.tvUsername.text = user.login
                    Glide.with(binding.ivProfile.context)
                        .load(user.avatarUrl)
                        .into(binding.ivProfile)
                }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailUserActivity::class.java)
            with(intent) {
                putExtra(DetailUserActivity.NAME, user.login)
                putExtra(DetailUserActivity.AVATAR_URL, user.avatarUrl)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>(){
            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}