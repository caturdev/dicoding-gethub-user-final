package com.example.githubuser.service.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.githubuser.service.database.entity.Likes

@Dao
interface LikesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(likes: Likes)

    @Update
    fun update(likes: Likes)

    @Delete
    fun delete(likes: Likes)

    @Query("SELECT * from likes ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Likes>>
}