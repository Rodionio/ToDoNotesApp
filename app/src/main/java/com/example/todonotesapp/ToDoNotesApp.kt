package com.example.todonotesapp

import android.app.Application
import com.example.todonotesapp.data.local.AppDatabase
import com.example.todonotesapp.repository.NoteRepository
import com.example.todonotesapp.repository.NoteRepositoryImpl
import kotlinx.coroutines.Dispatchers

class ToDoNotesApp: Application() {
    lateinit var noteRepository: NoteRepository
        private set
    override fun onCreate(){
        super.onCreate()

        val dao = AppDatabase.getInstance(this).noteDao()

        noteRepository = NoteRepositoryImpl(
            noteDao = dao,
            dispatcher = Dispatchers.IO
        )
    }
}