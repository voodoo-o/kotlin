package com.example.alistwithdetails.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "favorite_repos")
data class Repo(
    @PrimaryKey
    @SerialName("id")
    val id: Long,

    @SerialName("name")
    val name: String,

    @Embedded(prefix = "owner_") // This will create a column named "owner_login"
    @SerialName("owner")
    val owner: RepoOwner,

    @SerialName("description")
    val description: String?,

    @SerialName("stargazers_count")
    val stars: Int,

    @SerialName("forks_count")
    val forks: Int,

    @SerialName("language")
    val language: String?
)

@Serializable
data class RepoOwner(
    @SerialName("login")
    val login: String
)
