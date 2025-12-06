package com.example.alistwithdetails.domain.usecase

import com.example.alistwithdetails.data.model.Repo
import com.example.alistwithdetails.data.repository.RepoRepository
import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(private val repoRepository: RepoRepository) {
    suspend fun execute(repo: Repo) = repoRepository.addToFavorites(repo)
}