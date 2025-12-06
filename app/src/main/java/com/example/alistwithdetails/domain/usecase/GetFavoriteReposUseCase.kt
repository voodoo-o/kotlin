package com.example.alistwithdetails.domain.usecase

import com.example.alistwithdetails.data.model.Repo
import com.example.alistwithdetails.data.repository.RepoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteReposUseCase @Inject constructor(private val repoRepository: RepoRepository) {
    fun execute(): Flow<List<Repo>> = repoRepository.getFavoriteRepos()
}