package com.example.inventory.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.inventory.model.Note

@Database(entities = [
    Note::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun NotesDao(): NotesDao
}