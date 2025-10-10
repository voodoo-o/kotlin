package com.example.alistwithdetails.data.repository

import com.example.alistwithdetails.data.model.Repo

interface RepoRepository {
    fun getRepos(): List<Repo>
    fun getRepoById(id: Long): Repo?
}

class MockRepoRepository : RepoRepository {

    private val repos = listOf(
        Repo(
            id = 1,
            name = "Jetpack Compose",
            owner = "Google",
            description = "Современный декларативный набор инструментов для создания нативного пользовательского интерфейса Android. Упрощает и ускоряет разработку UI, позволяя описывать экраны с помощью функций Kotlin.",
            stars = 21500,
            forks = 2200,
            language = "Kotlin"
        ),
        Repo(
            id = 2,
            name = "Ktor",
            owner = "JetBrains",
            description = "Асинхронный фреймворк от создателей Kotlin для создания микросервисов, веб-приложений и многого другого. Отличается гибкостью и низким порогом вхождения.",
            stars = 13800,
            forks = 1100,
            language = "Kotlin"
        ),
        Repo(
            id = 3,
            name = "OkHttp",
            owner = "Square",
            description = "Эффективный HTTP-клиент для Android, Kotlin и Java. Является стандартом де-факто в индустрии и лежит в основе многих других библиотек, включая Retrofit.",
            stars = 48000,
            forks = 9800,
            language = "Kotlin"
        ),
        Repo(
            id = 4,
            name = "Dagger Hilt",
            owner = "Google",
            description = "Библиотека для внедрения зависимостей (Dependency Injection) в Android, построенная поверх Dagger. Значительно уменьшает количество шаблонного кода и упрощает управление зависимостями.",
            stars = 11200,
            forks = 1100,
            language = "Kotlin"
        ),
        Repo(
            id = 5,
            name = "Retrofit",
            owner = "Square",
            description = "Типобезопасный HTTP-клиент, который позволяет декларативно описывать API с помощью аннотаций и легко интегрируется с различными конвертерами (например, Gson, Moshi).",
            stars = 44500,
            forks = 7800,
            language = "Kotlin"
        ),
        Repo(
            id = 6,
            name = "Room",
            owner = "Google",
            description = "Часть Android Jetpack, предоставляющая удобную абстракцию над SQLite для более надежной работы с базами данных. Проверяет SQL-запросы на этапе компиляции.",
            stars = 23000,
            forks = 3000,
            language = "Kotlin"
        ),
        Repo(
            id = 7,
            name = "Coil",
            owner = "Coil Contributors",
            description = "Современная библиотека для загрузки изображений, полностью написанная на Kotlin и использующая корутины. Отличается скоростью, легкостью и расширяемостью.",
            stars = 9900,
            forks = 650,
            language = "Kotlin"
        )
    )

    override fun getRepos(): List<Repo> {
        return repos
    }

    override fun getRepoById(id: Long): Repo? {
        return repos.find { it.id == id }
    }
}
