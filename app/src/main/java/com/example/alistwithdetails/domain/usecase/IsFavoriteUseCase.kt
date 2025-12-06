package com.example.alistwithdetails.domain.usecase

import com.example.alistwithdetails.data.repository.RepoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(private val repoRepository: RepoRepository) {
    fun execute(repoId: Long): Flow<Boolean> = repoRepository.isFavorite(repoId)
}