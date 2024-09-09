package com.example.note.spotify_module.presenter.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.note.R
import com.example.note.core.compose.BasicOutlineTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp1Screen() {
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
                text = "What's your email?",
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
//                    TextFieldDefaults.textFieldColors(
//                        containerColor = colorResource(R.color.gray),
//                        textColor = Color.Black,
//                    ),
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = "You will need to confirm this email later",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.gray)),
                ) {
                    Text("Next", color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    iconClickable: () -> Unit = {},
    headerText: String = "Create account",
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(R.drawable.chevronleft),
            contentDescription = "back button",
            modifier = Modifier.clickable { iconClickable() },
        )
        Text(
            text = headerText,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth().padding(end = 20.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
@Preview(showSystemUi = true)
fun Preview() {
    SignUp1Screen()
}
