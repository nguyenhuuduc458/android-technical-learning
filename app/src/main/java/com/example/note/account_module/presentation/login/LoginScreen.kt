package com.example.note.account_module.presentation.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.note.R
import com.example.note.core.compose.BasicButton
import com.example.note.core.compose.BasicOutlinePasswordTextField
import com.example.note.core.compose.BasicOutlineTextField
import com.example.note.ui.theme.customColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    onSignUp: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    viewModel: LoginViewModel = koinViewModel(),
) {
    val uiState: LoginUiState by viewModel.uiState.collectAsState()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .padding(bottom = 10.dp, top = 20.dp)
    ) {
        val (logoRef, titleRef, inputRef, bottomRef) = createRefs()
        val horizontalGuideline = createGuidelineFromTop(0.70f)
        Image(
            painter = painterResource(id = R.drawable.logo), contentDescription = "logo",
            modifier = Modifier.constrainAs(logoRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = "Welcome\nBack", style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier
                .padding(top = 30.dp)
                .constrainAs(titleRef) {
                    start.linkTo(parent.start)
                    top.linkTo(logoRef.bottom)
                }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(inputRef) {
                    top.linkTo(titleRef.bottom)
                    bottom.linkTo(horizontalGuideline)
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicOutlineTextField(
                modifier = Modifier.fillMaxWidth(),
                textValue = uiState.username,
                label = "Username",
                placeHolder = "Username",
                onValueChanged = { username ->
                    viewModel.enterUsername(username)
                },
            )
            Spacer(modifier = Modifier.height(20.dp))
            var passwordVisible by remember { mutableStateOf(false) }
            BasicOutlinePasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                textValue = uiState.password,
                label = "Password",
                placeHolder = "Password",
                onValueChanged = { password ->
                    viewModel.enterPassword(password)
                },
                passwordVisible = passwordVisible,
                toggleClick = { isVisible ->
                    passwordVisible = !isVisible
                },
                onDone = { viewModel.login() }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(bottomRef) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            BasicButton(value = "Login") {
                viewModel.login()
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Don't have an account?", fontSize = 20.sp)
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "Sign up",
                    color = customColor,
                    fontSize = 20.sp,
                    modifier = Modifier.clickable {
                        onSignUp()
                    })
            }
        }
        val context = LocalContext.current
        LaunchedEffect(uiState) {
            when {
                uiState.isLoggedIn -> {
                    onLoginSuccess()
                }

                uiState.errorMessage != null -> {
                    Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun preview() {
    LoginScreen()
}