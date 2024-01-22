package com.example.githubuser.service.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuser.service.database.dao.LikesDao
import com.example.githubuser.service.database.entity.Likes

@Database(entities = [Likes::class], version = 1)
abstract class LikesRoomDatabase : RoomDatabase() {
    abstract fun noteDao(): LikesDao

    companion object {
        @Volatile
        private var INSTANCE: LikesRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): LikesRoomDatabase {
            if (INSTANCE == null) {
                synchronized(LikesRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        LikesRoomDatabase::class.java, "note_database"
                    ).build()
                }
            }
            return INSTANCE as LikesRoomDatabase
        }
    }
}