package com.example.alistwithdetails.data.network

import com.example.alistwithdetails.data.model.Repo
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {

    @GET("users/{user}/repos")
    suspend fun getRepos(@Path("user") user: String): List<Repo>

    @GET("repos/{owner}/{repo_name}")
    suspend fun getRepoDetails(
        @Path("owner") owner: String,
        @Path("repo_name") repoName: String
    ): Repo

    companion object {
        const val GITHUB_API_URL = "https://api.github.com/"
    }
}