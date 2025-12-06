package com.example.alistwithdetails.domain.usecase

import com.example.alistwithdetails.data.model.Repo
import com.example.alistwithdetails.data.repository.RepoRepository
import javax.inject.Inject

class GetReposUseCase @Inject constructor(private val repoRepository: RepoRepository) {

    suspend fun execute(user: String): Result<List<Repo>> {
        return try {
            val repos = repoRepository.getRepos(user)
            Result.success(repos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}