package com.example.note.core.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
// import androidx.compose.material3.TextFieldDefaults.OutlinedTextFieldDecorationBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.note.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicOutlineTextField(
    modifier: Modifier,
    textValue: String,
    label: String,
    placeHolder: String,
    onValueChanged: (String) -> Unit,
//    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = textValue,
        onValueChange = {
            onValueChanged(it)
        },
        label = {
            Text(label, color = Color.LightGray)
        },
        placeholder = {
            Text(placeHolder, color = Color.LightGray)
        },
//        colors = colors,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicOutlinePasswordTextField(
    modifier: Modifier,
    textValue: String,
    label: String,
    placeHolder: String,
    onValueChanged: (String) -> Unit,
    passwordVisible: Boolean,
    toggleClick: (Boolean) -> Unit,
    onDone: () -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = textValue,
        onValueChange = {
            onValueChanged(it)
        },
        label = {
            Text(label, color = Color.LightGray)
        },
        placeholder = {
            Text(placeHolder, color = Color.LightGray)
        },
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            // Add an eye icon to toggle password visibility
            val image = if (passwordVisible) R.drawable.eye_show else R.drawable.eye_hide
            val contentDescription =
                if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = {
                toggleClick(passwordVisible)
            }) {
                Icon(
                    painter = painterResource(id = image),
                    contentDescription = contentDescription,
                )
            }
        },
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
        keyboardActions =
            KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    onDone()
                },
            ),
    )
}

// @OptIn(ExperimentalMaterial3Api::class)
// @Composable
// fun BasicSearchView(
//    modifier: Modifier = Modifier,
//    searchValue: String,
//    onValueChanged: (String) -> Unit = {},
//    onDone: () -> Unit = {},
//    colors: TextFieldColors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
// ) {
//    val keyboardController = LocalSoftwareKeyboardController.current
//    BasicTextField(
//        value = searchValue,
//        onValueChange = { onValueChanged(it) },
//        modifier = modifier.fillMaxWidth().height(40.dp),
//        singleLine = true,
//        keyboardOptions =
//            KeyboardOptions(
//                keyboardType = KeyboardType.Text,
//                imeAction = ImeAction.Done,
//            ),
//        keyboardActions =
//            KeyboardActions(
//                onDone = {
//                    keyboardController?.hide()
//                    onDone()
//                },
//            ),
//        decorationBox = { innerTextField ->
//            OutlinedTextFieldDecorationBox(
//                value = searchValue,
//                innerTextField = innerTextField,
//                enabled = true,
//                singleLine = true,
//                visualTransformation = VisualTransformation.None,
//                interactionSource = remember { MutableInteractionSource() },
//                leadingIcon = {
//                    Icon(
//                        imageVector = Icons.Default.Search,
//                        contentDescription = "Search icon",
//                    )
//                },
//                placeholder = { Text("Search", color = Color.LightGray, modifier = modifier.fillMaxSize()) },
//                colors = colors,
//            )
//        },
//    )
// }

@Composable
fun BasicButton(
    modifier: Modifier = Modifier,
    value: String = "",
    onClick: () -> Unit = {},
) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6875F5)), // Button background color
        shape = RoundedCornerShape(8.dp),
        modifier =
            modifier
                .fillMaxWidth()
                .height(50.dp),
    ) {
        Text(
            text = value,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun OutlineButtonWithIcon(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    contentDescription: String = "",
    onClick: () -> Unit,
    text: String = "",
) {
    OutlinedButton(onClick = onClick, modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(icon), contentDescription = contentDescription)
            Text(
                text,
                color = Color.White,
                modifier = modifier.padding(10.dp),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun DefaultRadioButton(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors =
                RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.primary,
                    unselectedColor = MaterialTheme.colorScheme.onBackground,
                ),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}
