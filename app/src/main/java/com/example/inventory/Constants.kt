package com.example.inventory

import com.example.inventory.model.Note

object Constants {
    const val TABLE_NAME = "notes"
    const val DATABASE_NAME = "notesDatabase"
    val noteDetailPlaceHolder = Note(
        note = "Cannot find note details",
        id = 0,
        title = "Cannot find note details"
    )
}