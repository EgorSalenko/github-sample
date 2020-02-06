package io.esalenko.github.sample.app.data.network

import io.esalenko.github.sample.app.data.model.search.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface SearchService {
    @GET("search/repositories")
    suspend fun search(
        @Header("Authorization") token: String,
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("page") page: Int,
        @Query("per_page") limit: Int
    ): SearchResponse
}
