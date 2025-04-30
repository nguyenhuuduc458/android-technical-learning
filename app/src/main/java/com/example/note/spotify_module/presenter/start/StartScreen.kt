package com.example.note.spotify_module.presenter.start

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.note.BuildConfig
import com.example.note.R
import com.example.note.core.compose.OutlineButtonWithIcon
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote

@Composable
fun StartScreen(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = Color(0xFF121212)),
    ) {
        Image(
            painter = painterResource(R.drawable.spotify_background),
            contentDescription = "Background image",
            modifier = modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
        )
        Text(
            text = "Millions of Songs.\n Free on Spotify.",
            modifier = modifier.fillMaxWidth(),
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium,
        )

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth(),
        ) {
            Column(modifier = modifier.fillMaxSize()) {
                Button(
                    modifier = modifier.fillMaxWidth(),
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1ED760)),
                ) {
                    Text(
                        text = "Sign up free",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = modifier.padding(10.dp),
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))
                OutlineButtonWithIcon(
                    modifier = modifier.fillMaxWidth(),
                    icon = R.drawable.google_ic,
                    onClick = { },
                    text = "Continue with Google",
                )
                Spacer(modifier = Modifier.padding(8.dp))
                OutlineButtonWithIcon(
                    modifier = modifier.fillMaxWidth(),
                    icon = R.drawable.facebook_ic,
                    onClick = { },
                    text = "Continue with Facebook",
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "Log in",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
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
                Toast
                    .makeText(context, "Login error ${throwable.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        },
    )
}

@Composable
@Preview(showSystemUi = true)
fun Preview() {
    StartScreen(modifier = Modifier.fillMaxWidth())
}
