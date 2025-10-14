package com.example.todonotesapp.viewmodel

import com.example.todonotesapp.data.local.entity.NoteEntity

sealed class NoteListState {
    object Loading : NoteListState()
    object Empty : NoteListState()
    data class Content(val notes: List<NoteEntity>) : NoteListState()
    data class Error(val message: String) : NoteListState()

    data class EditingNote(
        val id: Long,
        val editedTitle: String,
        val editedContent: String,
    ) : NoteListState()

}