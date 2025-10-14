package com.example.todonotesapp.ui.theme.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.todonotesapp.ui.theme.components.NoteDetailCard
import com.example.todonotesapp.viewmodel.NoteListState
import com.example.todonotesapp.viewmodel.NoteViewModel


@Composable
fun NoteDetailScreen(viewModel: NoteViewModel, noteId: Long, onBack: () -> Unit) {

    val notes by viewModel.note.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val note = notes.find { it.id == noteId }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            notes.isEmpty() -> Text(
                "Loading...",
                Modifier.align(Alignment.Center),
                fontSize = 30.sp
            )

            note == null -> Text(
                "Note not found!",
                Modifier.align(Alignment.Center),
                fontSize = 30.sp
            )

            else -> {
                val currentUiState = uiState
                when (currentUiState) {
                    is NoteListState.EditingNote -> {
                        if (currentUiState.id == note.id) {
                            NoteEditCardScreen(
                                title = currentUiState.editedTitle,
                                content = currentUiState.editedContent,
                                onTitleChange = { viewModel.updateEditedTitle(it) },
                                onContentChange = { viewModel.updateEditedContent(it) },
                                onSave = { viewModel.saveEditedNote() },
                                onCancel = { viewModel.cancelEditing() }
                            )
                        } else {
                            NoteDetailCard(
                                note = note,
                                onEditClick = {
                                    viewModel.startEditing(note.id, note.title, note.content)
                                },
                                onBack = onBack
                            )
                        }
                    }
                    else -> {
                        NoteDetailCard(
                            note = note,
                            onEditClick = {
                                viewModel.startEditing(note.id, note.title, note.content)
                            },
                            onBack = onBack
                        )
                    }
                }
            }
        }
    }
}