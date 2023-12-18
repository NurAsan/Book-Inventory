package com.example.inventory.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Entity(tableName = Constants.TABLE_NAME, indices = [Index(value = ["id"], unique = true)])
data class Note (
    @PrimaryKey(autoGenerate = true)    val id: Int? = null,
    @ColumnInfo(name = "note")          val note: String,
    @ColumnInfo(name = "title")         val title: String,
    @ColumnInfo(name = "dateUpdated")   val dateUpdated: String = getDateCreated(),
    @ColumnInfo(name = "imageUri")     val imageUri: String? = null
)

fun getDateCreated(): String {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}