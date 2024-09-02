package com.example.note.core.retrofit.authentication

import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TokenApi {
    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun getAccessToken(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
    ): TokenDto

    companion object {
        operator fun invoke(retrofit: Retrofit): TokenApi = retrofit.create(TokenApi::class.java)
    }
}
