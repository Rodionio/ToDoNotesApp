package com.example.todonotesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todonotesapp.data.local.entity.NoteEntity
import com.example.todonotesapp.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<NoteListState>(NoteListState.Loading)
    val uiState: StateFlow<NoteListState> = _uiState.asStateFlow()

    private val _note = MutableStateFlow<List<NoteEntity>>(emptyList())
    val note: StateFlow<List<NoteEntity>> = _note.asStateFlow()

    init {
        loadNotes()
    }

    fun loadNotes() {
        viewModelScope.launch {
            repository.getAll()
                .onStart {
                    _uiState.value = NoteListState.Loading
                }
                .catch { e -> _uiState.value = NoteListState.Error(e.message ?: "Unknown error") }
                .collect { notes ->
                    _note.value = notes
                    _uiState.value = if (notes.isEmpty()) {
                        NoteListState.Empty
                    } else {
                        NoteListState.Content(notes)
                    }
                }
        }
    }


    fun addNewNote(title: String, content: String) {
        viewModelScope.launch {
            repository.insert(NoteEntity(title = title, content = content))
            loadNotes()
        }
    }


    fun deleteNote(id: Long) {
        viewModelScope.launch {
            repository.delete(NoteEntity(id = id, content = "", title = ""))
            loadNotes()

        }

    }

    fun updateNote(id: Long, title: String, content: String) {
        viewModelScope.launch {
            repository.update(NoteEntity(id = id, title = title, content = content))
            loadNotes()
        }
    }

    fun startEditing(id: Long, title: String, content: String) {
        viewModelScope.launch {
            _uiState.value =
                NoteListState.EditingNote(id = id, editedTitle = title, editedContent = content)
        }

    }

    fun updateEditedTitle(newTitle: String){
        val currentState = _uiState.value
        if (currentState is NoteListState.EditingNote) {
            _uiState.value = currentState.copy(editedTitle = newTitle)

        }
    }

    fun updateEditedContent(newContent: String){
        val currentState = _uiState.value
        if (currentState is NoteListState.EditingNote) {
            _uiState.value = currentState.copy(editedContent = newContent)

        }
    }

    fun saveEditedNote(){
        val currentState =_uiState.value
        if (currentState is NoteListState.EditingNote){
            viewModelScope.launch{
                repository.update(
                    NoteEntity(
                        id = currentState.id,
                        title = currentState.editedTitle,
                        content = currentState.editedContent
                    )
                )
                loadNotes()
            }
        }
    }

    fun cancelEditing(){
        loadNotes()
    }

    fun toggleNoteDone(id:Long){
        viewModelScope.launch {
            repository.toggleDone(id)

        }
    }


}




