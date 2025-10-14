package com.example.todonotesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todonotesapp.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface NoteDao {
    @Query("SELECT * FROM notes" )
    fun getAll(): Flow<List<NoteEntity>>
    @Update
    suspend fun update(note: NoteEntity)
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(note: NoteEntity)
    @Delete
    suspend fun delete(note: NoteEntity)

    @Query("UPDATE notes SET is_done = CASE WHEN is_done = 0 THEN 1 ELSE 0 END WHERE id = :id")
    suspend fun toggleDoneById(id: Long)






}