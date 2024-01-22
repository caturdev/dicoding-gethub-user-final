package com.example.githubuser.bloc

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.service.config.ApiConfig
import com.example.githubuser.service.response.GithubUserResponse
import com.example.githubuser.service.response.GithubUsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : ViewModel() {
    private val _users = MutableLiveData<List<GithubUserResponse>>()
    val users: LiveData<List<GithubUserResponse>> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        _isLoading.value = false
    }

    fun getUsers(username: String) {
        // menampilkan loading indicator
        _isLoading.value = true

        // mengosongkan list user supaya tidak bertumpuk
        _users.value = listOf()

        val service = ApiConfig.getApiService().getUsers(username)

        service.enqueue(object : Callback<GithubUsersResponse> {
            override fun onResponse(
                call: Call<GithubUsersResponse>,
                response: Response<GithubUsersResponse>
            ) {
                // hide loading indicator
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GithubUsersResponse>, t: Throwable) {
                // hide loading indicator
                _isLoading.value = false
            }

        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}
