package io.esalenko.github.sample.app.data.network

import io.esalenko.github.sample.app.data.model.repos.UserRepo
import retrofit2.http.GET
import retrofit2.http.Query


interface ReposService {

    @GET("user/repos")
    suspend fun getUserRepos(
        @Query("visibility") visibility: String = "all",
        @Query("affiliation") affiliation: String = "owner",
        @Query("sort") sort: String = "updated",
        @Query("direction") direction: String = "desc"
    ): Array<UserRepo>

}
