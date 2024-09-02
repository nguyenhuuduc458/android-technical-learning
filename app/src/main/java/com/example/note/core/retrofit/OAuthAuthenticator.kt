package com.example.note.core.retrofit

import com.example.note.BuildConfig
import com.example.note.core.retrofit.authentication.TokenApi
import com.example.note.core.retrofit.authentication.TokenDto
import com.example.note.core.sharepreference.SecureSharePreferenceUtil
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class OAuthAuthenticator : Authenticator {
    override fun authenticate(
        route: Route?,
        response: Response,
    ): Request? {
        if (response.request().header("Authorization") == null) {
            val accessToken = SecureSharePreferenceUtil.accessToken
            return response
                .request()
                .newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()
        }

        if (response.code() == 401) {
            synchronized(this) {
                return runBlocking {
                    try {
                        val tokenDto: TokenDto =
                            TokenApi(tokenRetrofit).getAccessToken(
                                BuildConfig.SPOTIFY_GRANT_TYPE,
                                BuildConfig.SPOTIFY_CLIENT_ID,
                                BuildConfig.SPOTIFY_CLIENT_SECRET,
                            )
                        SecureSharePreferenceUtil.accessToken = tokenDto.accessToken
                        return@runBlocking response
                            .request()
                            .newBuilder()
                            .header("Authorization", "Bearer ${tokenDto.accessToken}")
                            .build()
                    } catch (e: Exception) {
                        return@runBlocking null
                    }
                }
            }
        }
        return response.request()
    }
}
