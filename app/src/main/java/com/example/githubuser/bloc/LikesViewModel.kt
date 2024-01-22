package com.example.githubuser.bloc

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.githubuser.service.database.entity.Likes
import com.example.githubuser.service.database.repo.LikesRepo

class LikesViewModel(application: Application) : ViewModel() {
    private val mNoteRepository: LikesRepo = LikesRepo(application)

    fun insert(likes: Likes) {
        mNoteRepository.insert(likes)
    }

    fun update(likes: Likes) {
        mNoteRepository.update(likes)
    }

    fun delete(likes: Likes) {
        mNoteRepository.delete(likes)
    }
}