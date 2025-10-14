package com.example.todonotesapp.repository

import com.example.todonotesapp.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAll(): Flow<List<NoteEntity>>
    suspend fun insert(note: NoteEntity)
    suspend fun update(note: NoteEntity)
    suspend fun delete(note: NoteEntity)
    suspend fun toggleDone(id: Long)


}