package com.example.alistwithdetails.domain.usecase

import com.example.alistwithdetails.data.model.Repo
import com.example.alistwithdetails.data.repository.RepoRepository
import javax.inject.Inject

class GetRepoDetailsUseCase @Inject constructor(private val repoRepository: RepoRepository) {

    suspend fun execute(owner: String, repoName: String): Result<Repo?> {
        return try {
            // First, try to get the repo from the local database (favorites)
            val localRepo = repoRepository.getFavoriteRepoByName(owner, repoName)
            if (localRepo != null) {
                return Result.success(localRepo)
            }

            // If not in favorites, get it from the network
            val networkRepo = repoRepository.getRepoDetails(owner, repoName)
            Result.success(networkRepo)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}