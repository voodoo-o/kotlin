package com.example.alistwithdetails.data.model

data class Repo(
    val id: Long,
    val name: String,
    val owner: String,
    val description: String?,
    val stars: Int,
    val forks: Int,
    val language: String?
)