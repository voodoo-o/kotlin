package com.example.alistwithdetails.data.repository

import com.example.alistwithdetails.data.model.Repo
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    // Network
    suspend fun getRepos(user: String): List<Repo>
    suspend fun getRepoDetails(owner: String, repoName: String): Repo?

    // Database
    fun getFavoriteRepos(): Flow<List<Repo>>
    suspend fun getFavoriteRepoByName(owner: String, repoName: String): Repo?
    fun isFavorite(repoId: Long): Flow<Boolean>
    suspend fun addToFavorites(repo: Repo)
    suspend fun removeFromFavorites(repo: Repo)
}