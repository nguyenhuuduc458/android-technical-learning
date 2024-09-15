package com.example.note.spotify_module.presenter.artists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.note.R
//import com.example.note.core.compose.BasicSearchView
import com.example.note.spotify_module.presenter.signup.Header

@Composable
fun ChoseArtistScreen() {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.space_black))
                .padding(16.dp),
    ) {
        Header(headerText = "Choose 3 or more artists you like.")
        Column(modifier = Modifier.padding(10.dp).fillMaxWidth()) {
//            BasicSearchView(modifier = Modifier, "")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewChoseArtist() {
    ChoseArtistScreen()
}
