package com.example.alistwithdetails.data.repository

import com.example.alistwithdetails.data.db.FavoriteRepoDao
import com.example.alistwithdetails.data.model.Repo
import com.example.alistwithdetails.data.network.GithubApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkRepoRepository @Inject constructor(
    private val apiService: GithubApiService,
    private val dao: FavoriteRepoDao
) : RepoRepository {

    override suspend fun getRepos(user: String): List<Repo> {
        return apiService.getRepos(user)
    }

    override suspend fun getRepoDetails(owner: String, repoName: String): Repo? {
        return apiService.getRepoDetails(owner, repoName)
    }

    override fun getFavoriteRepos(): Flow<List<Repo>> {
        return dao.getFavoriteRepos()
    }

    override suspend fun getFavoriteRepoByName(owner: String, repoName: String): Repo? {
        return dao.getRepoByName(owner, repoName)
    }

    override fun isFavorite(repoId: Long): Flow<Boolean> {
        return dao.isFavorite(repoId)
    }

    override suspend fun addToFavorites(repo: Repo) {
        dao.insert(repo)
    }

    override suspend fun removeFromFavorites(repo: Repo) {
        dao.delete(repo)
    }
}
