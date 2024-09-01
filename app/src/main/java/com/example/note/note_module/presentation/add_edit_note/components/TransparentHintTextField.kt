package com.example.note.note_module.presentation.add_edit_note.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun TransparentHintTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),
    isHintVisible: Boolean = false,
    singleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit,
    onValueChange: (String) -> Unit,
) {
    Box(
        modifier = modifier,
    ) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        onFocusChange(it)
                    },
        )
        if (isHintVisible) {
            Text(text = hint, style = textStyle, color = Color.DarkGray)
        }
    }
}
