package com.example.alistwithdetails.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.alistwithdetails.data.model.Repo
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteRepoDao {

    @Query("SELECT * FROM favorite_repos ORDER BY name ASC")
    fun getFavoriteRepos(): Flow<List<Repo>>

    @Query("SELECT * FROM favorite_repos WHERE owner_login = :owner AND name = :repoName LIMIT 1")
    suspend fun getRepoByName(owner: String, repoName: String): Repo?

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_repos WHERE id = :repoId LIMIT 1)")
    fun isFavorite(repoId: Long): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repo: Repo)

    @Delete
    suspend fun delete(repo: Repo)
}