package com.example.alistwithdetails.data.repository

import com.example.alistwithdetails.data.model.Repo
import com.example.alistwithdetails.data.network.RetrofitClient

interface RepoRepository {
    suspend fun getRepos(user: String): List<Repo>
    suspend fun getRepoDetails(owner: String, repoName: String): Repo?
}

class NetworkRepoRepository : RepoRepository {

    private val apiService = RetrofitClient.apiService

    override suspend fun getRepos(user: String): List<Repo> {
        return apiService.getRepos(user)
    }

    override suspend fun getRepoDetails(owner: String, repoName: String): Repo? {
        return apiService.getRepoDetails(owner, repoName)
    }
}