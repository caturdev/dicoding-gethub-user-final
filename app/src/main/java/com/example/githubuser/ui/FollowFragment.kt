package com.example.githubuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.bloc.ProfileViewModel
import com.example.githubuser.databinding.FragmentFollowBinding
import com.example.githubuser.service.response.GithubUserResponse
import com.example.githubuser.ui.adapter.GithubUserListAdapter

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentFollowBinding.bind(view)
        super.onViewCreated(binding.root, savedInstanceState)

        initLottieFiles()

        val username = arguments?.getString(GITHUB_USERNAME)
        val position = arguments?.getInt(ARG_SECTION_NUMBER)

        if (position == 1) profileViewModel.getUserFollowers(username ?: "")
        if (position == 2) profileViewModel.getUserFollowing(username ?: "")

        profileViewModel.followers.observe(viewLifecycleOwner) { users: List<GithubUserResponse> ->
            setUserFollow(users)
        }
        profileViewModel.following.observe(viewLifecycleOwner) { users: List<GithubUserResponse> ->
            setUserFollow(users)
        }
        profileViewModel.isLoading.observe(viewLifecycleOwner) { isLoading: Boolean ->
            showLoading(isLoading)
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        binding.rvFollow.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollow.addItemDecoration(itemDecoration)
    }

    private fun setUserFollow(users: List<GithubUserResponse>) {
        val adapter = GithubUserListAdapter()
        adapter.submitList(users)
        binding.rvFollow.adapter = adapter
    }

    private fun showLoading(loading: Boolean?) {

    }

    private fun initLottieFiles() {
        val githubLoading = binding.githubLoading
        githubLoading.setAnimationFromUrl("https://lottie.host/f4aa2a91-160f-40bf-927a-85ca4d9f1074/HesvD4FI65.json")
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val GITHUB_USERNAME = "github_username"
    }
}