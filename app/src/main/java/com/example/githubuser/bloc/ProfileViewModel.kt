package com.example.githubuser.bloc

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.service.config.ApiConfig
import com.example.githubuser.service.database.entity.Likes
import com.example.githubuser.service.database.repo.LikesRepo
import com.example.githubuser.service.response.GithubUserDetailResponse
import com.example.githubuser.service.response.GithubUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(application: Application) : ViewModel() {

    private val mNoteRepository: LikesRepo = LikesRepo(application)

    private val _user = MutableLiveData<GithubUserDetailResponse>()
    val user: LiveData<GithubUserDetailResponse> = _user

    private val _followers = MutableLiveData<List<GithubUserResponse>>()
    val followers: LiveData<List<GithubUserResponse>> = _followers

    private val _following = MutableLiveData<List<GithubUserResponse>>()
    val following: LiveData<List<GithubUserResponse>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserDetail(username: String) {
        val client = ApiConfig.getApiService().getUserDetail(username)

        client.enqueue(object : Callback<GithubUserDetailResponse> {

            override fun onResponse(
                call: Call<GithubUserDetailResponse>,
                response: Response<GithubUserDetailResponse>
            ) {
                // menyembunyikan loading indicator
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _user.value = responseBody ?: null
                    }
                } else {
                    Log.e(TAG, "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubUserDetailResponse>, t: Throwable) {
                // menyembunyikan loading indicator
                _isLoading.value = false
            }


        })

        // menampilkan loading indicator
        _isLoading.value = true
    }

    fun getUserFollowers(username: String) {
        val client = ApiConfig.getApiService().getUserFollowers(username)

        client.enqueue(object : Callback<List<GithubUserResponse>> {

            override fun onResponse(
                call: Call<List<GithubUserResponse>>,
                response: Response<List<GithubUserResponse>>
            ) {
                // menyembunyikan loading indicator
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _followers.value = responseBody ?: null
                    }
                } else {
                    Log.e(TAG, "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GithubUserResponse>>, t: Throwable) {
                // menyembunyikan loading indicator
                _isLoading.value = false
            }

        })

        // menampilkan loading indicator
        _isLoading.value = true
    }

    fun getUserFollowing(username: String) {
        val client = ApiConfig.getApiService().getUserFollowing(username)

        client.enqueue(object : Callback<List<GithubUserResponse>> {

            override fun onResponse(
                call: Call<List<GithubUserResponse>>,
                response: Response<List<GithubUserResponse>>
            ) {
                // menyembunyikan loading indicator
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _following.value = responseBody ?: null
                    }
                } else {
                    Log.e(TAG, "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GithubUserResponse>>, t: Throwable) {
                // menyembunyikan loading indicator
                _isLoading.value = false
            }

        })

        // menampilkan loading indicator
        _isLoading.value = true
    }

    fun insert(likes: Likes) {
        mNoteRepository.insert(likes)
    }

    fun update(likes: Likes) {
        mNoteRepository.update(likes)
    }

    fun delete(likes: Likes) {
        mNoteRepository.delete(likes)
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }

}