package com.example.note.spotify_module.presenter.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.note.BuildConfig
import com.example.note.account_module.presentation.login.LoginScreen
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
) {
    val context: Context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize(), ) {
       OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = {
           login(context)
       }) {
           Text(text = "Authentication")
       }
    }
}

fun login(context: Context) {
    val redirectUri = "https://www.note.com/redirect-auth"
    var spotifyAppRemote: SpotifyAppRemote? = null

    val connectionParams =
        ConnectionParams
            .Builder(BuildConfig.SPOTIFY_CLIENT_ID)
            .setRedirectUri(redirectUri)
            .showAuthView(true)
            .build()

    SpotifyAppRemote.connect(
        context,
        connectionParams,
        object : Connector.ConnectionListener {
            override fun onConnected(appRemote: SpotifyAppRemote) {
                spotifyAppRemote = appRemote
                Toast.makeText(context, "login success", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(throwable: Throwable) {
                Toast.makeText(context, "Login error ${throwable.message}", Toast.LENGTH_SHORT).show()

            }
        },
    )
}

@Composable
@Preview(showSystemUi = true)
fun Preview() {
    SignInScreen(modifier = Modifier.fillMaxWidth())
}
