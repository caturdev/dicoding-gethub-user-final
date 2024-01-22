package com.example.githubuser.ui

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.bloc.ProfileViewModel
import com.example.githubuser.databinding.ActivityProfileBinding
import com.example.githubuser.model.GithubUser
import com.example.githubuser.service.database.entity.Likes
import com.example.githubuser.service.response.GithubUserDetailResponse
import com.example.githubuser.ui.adapter.FollowPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var toolbar: Toolbar
    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = binding.topAppBar
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.imageView.visibility = View.GONE

        initLottieFiles()

        val githubUser = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<GithubUser>(GITHUB_USER, GithubUser::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<GithubUser>(GITHUB_USER)
        }

        profileViewModel.getUserDetail(githubUser?.username ?: "")

        profileViewModel.user.observe(this) { user ->
            setUserData(user)

            val viewPager: ViewPager2 = binding.viewPager

            val sectionPagerAdapter = FollowPagerAdapter(this)
            sectionPagerAdapter.username = githubUser?.username ?: ""

            viewPager.adapter = sectionPagerAdapter
            val tabs: TabLayout = binding.tabs

            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Followers ${user.followers.toString()}"
                    1 -> "Following ${user.following.toString()}"
                    else -> ""
                }
            }.attach()
        }

        supportActionBar?.elevation = 0f

    }

    private fun initLottieFiles() {
        val githubLoading = binding.githubLoading
        githubLoading.setAnimationFromUrl("https://lottie.host/f4aa2a91-160f-40bf-927a-85ca4d9f1074/HesvD4FI65.json")
    }

    private fun setUserData(user: GithubUserDetailResponse) {

        // menampilkan user avatar
        Glide
            .with(binding.imageView.context)
            .load(user.avatarUrl)
            .into(binding.imageView)

        binding.githubLoading.visibility = View.GONE
        binding.imageView.visibility = View.VISIBLE

        // show name data
        binding.name.text = user.name

        // show username data
        binding.username.text = "@${user.login}"

        // show location data
        binding.location.text = user.location

        // show company data
        binding.company.text = user.company

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )

        const val GITHUB_USER = "github_user"
    }

}