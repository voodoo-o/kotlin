package com.example.alistwithdetails.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.alistwithdetails.data.model.Repo

@Database(entities = [Repo::class], version = 2, exportSchema = false) // <--- Version incremented
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteRepoDao(): FavoriteRepoDao
}