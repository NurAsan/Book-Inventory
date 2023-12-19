package com.example.inventory.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.inventory.model.Note

@Database(version = 1, entities = [Note::class])
abstract class NotesDatabase  : RoomDatabase(){
    abstract fun NotesDao(): NotesDao
}