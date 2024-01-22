package com.example.githubuser.service.database.repo

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser.service.database.LikesRoomDatabase
import com.example.githubuser.service.database.dao.LikesDao
import com.example.githubuser.service.database.entity.Likes
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LikesRepo(application: Application) {
    private val mNotesDao: LikesDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = LikesRoomDatabase.getDatabase(application)
        mNotesDao = db.noteDao()
    }

    fun getAllNotes(): LiveData<List<Likes>> = mNotesDao.getAllNotes()

    fun insert(likes: Likes) = executorService.execute { mNotesDao.insert(likes) }

    fun delete(likes: Likes) = executorService.execute { mNotesDao.delete(likes) }

    fun update(note: Likes) = executorService.execute { mNotesDao.update(note) }
}