package com.example.inventory.ui.NoteDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.example.inventory.Constants.noteDetailPlaceHolder
import com.example.inventory.NotesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun NoteDetailPage(
    noteId: Int,
    navController: NavController,
    viewModel: NotesViewModel
){
    val scope = rememberCoroutineScope()

    val note = remember{
        mutableStateOf(noteDetailPlaceHolder)
    }

    LaunchedEffect(true){
        scope.launch(Dispatchers.IO){
            note.value = viewModel.getNote(noteId)?: noteDetailPlaceHolder
        }
    }

}