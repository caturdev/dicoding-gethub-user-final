package com.example.githubuser.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ComponentUserItemBinding
import com.example.githubuser.model.GithubUser
import com.example.githubuser.service.response.GithubUserResponse
import com.example.githubuser.ui.ProfileActivity

class GithubUserListAdapter :
    ListAdapter<GithubUserResponse, GithubUserListAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val binding: ComponentUserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GithubUserResponse) {
            // menampilkan username
            binding.tvUsername.text = item.login

            // menampilkan user ID
            binding.tvUserId.text = "ID ${item.id}"

            // menampilkan user avatar
            Glide
                .with(binding.imageView.context)
                .load(item.avatarUrl)
                .into(binding.imageView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ComponentUserItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        val githubUser = GithubUser(
            item.login
        )

        holder.itemView.setOnClickListener { view ->
            val moveIntent = Intent(holder.itemView.context, ProfileActivity::class.java)
            moveIntent.putExtra(ProfileActivity.GITHUB_USER, githubUser)
            view.context.startActivity(moveIntent)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubUserResponse>() {
            override fun areItemsTheSame(
                oldItem: GithubUserResponse,
                newItem: GithubUserResponse
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: GithubUserResponse,
                newItem: GithubUserResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}