package com.example.note.core.retrofit

import com.example.note.core.retrofit.authentication.TokenApiService
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class OAuthAuthenticator
    @Inject
    constructor(
        private val apiService: TokenApiService,
    ) : Authenticator {
        override fun authenticate(
            route: Route?,
            response: Response,
        ): Request? {
            // Avoid retrying if we've already attempted token refresh multiple times
//            if (responseCount(response) >= 3) return null

            // Get the current refresh token
//            val refreshToken = TokenManager.getRefreshToken() ?: return null

            // Synchronously request a new token using the refresh token
            val newToken = "fds"
//                runBlocking {
//                    try {
//                        val response = apiService.refreshToken(refreshToken) // Your refresh API call
//                        if (response.isSuccessful) {
//                            val newAccessToken = response.body()?.accessToken
//                            tokenManager.saveAccessToken(newAccessToken) // Save new token
//                            newAccessToken
//                        } else {
//                            null
//                        }
//                    } catch (e: Exception) {
//                        null
//                    }
//                }

            // Retry the original request with the new token, if obtained
            return newToken.let {
                response.request
                    .newBuilder()
                    .header("Authorization", "Bearer $it")
                    .build()
            }
        }
    }
