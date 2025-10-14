package com.example.todonotesapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

import com.example.todonotesapp.data.local.dao.NoteDao
import com.example.todonotesapp.data.local.entity.NoteEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Database(entities = [NoteEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "note.db"
                )

                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback())
                    .build().also { INSTANCE = it }


            }

    }

    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    val dao = database.noteDao()
                    val existing = dao.getAll().first()
                    if (existing.isEmpty()) {
                        dao.insert(
                            NoteEntity(
                                title = "First note!",
                                content = "Write your first note!"
                            )
                        )
                    }
                }
            }
        }
    }
}




