package com.example.alistwithdetails.domain.usecase

import com.example.alistwithdetails.data.model.Repo
import com.example.alistwithdetails.data.repository.RepoRepository

class GetRepoDetailsUseCase(private val repoRepository: RepoRepository) {

    suspend fun execute(owner: String, repoName: String): Result<Repo?> {
        return try {
            val repo = repoRepository.getRepoDetails(owner, repoName)
            Result.success(repo)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}