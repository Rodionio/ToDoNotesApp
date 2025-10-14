package com.example.todonotesapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Long=0,
    @ColumnInfo(name="content")
    val content:String,
    @ColumnInfo(name="title")
    val title:String,
    @ColumnInfo(name="is_done")
    val isDone:Boolean=false
)