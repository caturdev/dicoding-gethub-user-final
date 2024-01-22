package com.example.githubuser.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.bloc.MainViewModel
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.service.response.GithubUserResponse
import com.example.githubuser.ui.adapter.GithubUserListAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    private val searchResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == SearchActivity.RESULT_CODE && result.data != null) {
            val resultValue = result.data?.getStringExtra(SearchActivity.EXTRA_SELECTED_VALUE)
            mainViewModel.getUsers(resultValue ?: "")
        }

        binding.emptyPlaceholder.visibility = View.GONE
        binding.emptyPlaceholderDesc.visibility = View.GONE
        binding.emptyPlaceholderDescSub.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        initLottieFiles()

        initRecyclerView()

        mainViewModel.users.observe(this) { user -> setUserListData(user) }

    }

    private fun setUserListData(user: List<GithubUserResponse>?): Unit {
        val adapter = GithubUserListAdapter()
        adapter.submitList(user)
        binding.rvUser.adapter = adapter
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
    }

    private fun initLottieFiles() {
        val githubLoading = binding.githubLoading
        githubLoading.setAnimationFromUrl("https://lottie.host/f4aa2a91-160f-40bf-927a-85ca4d9f1074/HesvD4FI65.json")

        val emptyPlaceholder = binding.emptyPlaceholder
        emptyPlaceholder.setAnimationFromUrl("https://lottie.host/17faed6a-c2bf-4130-8f6b-3702d625576b/LEJ9NRrh3p.json")
    }
}