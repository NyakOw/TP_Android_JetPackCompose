package fr.eni.ecole.enishop.service

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fr.eni.ecole.enishop.bo.Article
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Locale.Category

interface ArticleApiService {

    @GET("products/{id}")
    suspend fun getArticleData(
        @Path("id") id : Long,
    ): Article

    @GET("products/")
    suspend fun getAllArticleData(): List<Article>

    @GET("products/categories")
    suspend fun getCategories(): List<String>

    companion object {

        private val BASE_URL = "https://fakestoreapi.com/"

        private val moshi by lazy {
            Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        }

        val articleApiService: ArticleApiService by lazy {
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                MoshiConverterFactory.create(
                    moshi
                )
            ).build().create(ArticleApiService::class.java)
        }
    }
}