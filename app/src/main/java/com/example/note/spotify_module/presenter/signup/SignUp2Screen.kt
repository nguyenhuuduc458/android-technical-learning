package com.example.note.spotify_module.presenter.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.note.R
import com.example.note.core.compose.BasicOutlineTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp2Screen() {
    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.space_black))
            .padding(16.dp),
    ) {
        Header()
        Spacer(modifier = Modifier.padding(10.dp))
        Column(modifier = Modifier.padding(10.dp).fillMaxWidth()) {
            Text(
                text = "Create a password",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
            BasicOutlineTextField(
                modifier = Modifier.fillMaxWidth(),
                textValue = "",
                label = "",
                placeHolder = "",
                onValueChanged = { value ->
                },
//                colors =
//                TextFieldDefaults.textFieldColors(
//                    containerColor = colorResource(R.color.gray),
//                    textColor = Color.Black,
//                ),
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = "use at least 8 characters",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.gray)),
                ) {
                    Text("Create", color = Color.Black)
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun Preview2() {
    SignUp2Screen()
}
