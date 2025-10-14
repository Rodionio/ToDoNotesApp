package com.example.todonotesapp.repository

import com.example.todonotesapp.data.local.dao.NoteDao
import com.example.todonotesapp.data.local.entity.NoteEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NoteRepositoryImpl(
    private val noteDao: NoteDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : NoteRepository {

    override fun getAll(): Flow<List<NoteEntity>> {
        return noteDao.getAll()
    }

    override suspend fun insert(note: NoteEntity) {
        withContext(dispatcher) {
            noteDao.insert(note)
        }

    }

    override suspend fun update(note: NoteEntity) {
        withContext(dispatcher) {
            noteDao.update(note)
        }
    }

   override suspend fun delete(note: NoteEntity) {
       withContext(dispatcher) {
           noteDao.delete(note)
       }
    }

    override suspend fun toggleDone(id: Long) {
        withContext(dispatcher) {
            noteDao.toggleDoneById(id)
        }
    }


}
