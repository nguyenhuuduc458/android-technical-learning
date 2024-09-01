package com.example.note.note_module.presentation.note

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.note.note_module.domain.model.Note
import com.example.note.note_module.domain.util.NoteEvent
import com.example.note.note_module.domain.util.NoteState
import com.example.note.note_module.presentation.note.component.NoteItem
import com.example.note.note_module.presentation.note.component.OrderSection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    viewModel: NoteViewModel = koinViewModel(),
    onCreateItem: () -> Unit,
    onEditItem: (Note) -> Unit,
) {
    val uiState: NoteState by viewModel.noteState.collectAsState()
    val scope: CoroutineScope = rememberCoroutineScope()
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onCreateItem() },
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Create note")
            }
        },
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Yours note",
                    style = MaterialTheme.typography.titleMedium,
                )
                IconButton(onClick = { viewModel.onEvent(NoteEvent.ToggleOrderSection) }) {
                    Icon(imageVector = Icons.Default.List, contentDescription = "Showing sort view")
                }
            }
            AnimatedVisibility(
                visible = uiState.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically(),
            ) {
                OrderSection(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    noteOrder = uiState.noteOrder,
                    onOrderChange = { viewModel.onEvent(NoteEvent.Order(it)) },
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(uiState.notes) { note ->
                    NoteItem(
                        note = note,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onEditItem(note)
                                },
                        onDeleteClick = {
                            viewModel.onEvent(NoteEvent.DeleteNote(note))
                            scope.launch {
                                val result: SnackbarResult =
                                    snackbarHostState
                                        .showSnackbar(
                                            message = "Note deleted",
                                            actionLabel = "Undo",
                                            // Defaults to SnackbarDuration.Short
                                            duration = SnackbarDuration.Long,
                                        )

                                when (result) {
                                    SnackbarResult.ActionPerformed -> {
                                        viewModel.onEvent(NoteEvent.RestoreNote)
                                    }

                                    SnackbarResult.Dismissed -> {
                                        // do nothing
                                    }
                                }
                            }
                        },
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
