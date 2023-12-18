package com.example.inventory.ui.NoteEdit

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.inventory.Constants
import com.example.inventory.NotesViewModel
import com.example.inventory.PhotoNotesApp
import com.example.inventory.R
import com.example.inventory.model.Note
import com.example.inventory.ui.GenericAppBar
import com.example.inventory.ui.theme.PhotoNotesTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteEditScreen(
    noteId: Int,
    navController: NavController,
    viewModel: NotesViewModel,
){
    val scope = rememberCoroutineScope()
    val note =  remember{ mutableStateOf((Constants.noteDetailPlaceHolder)) }
    val currentNote = remember{
        mutableStateOf(note.value.note)
    }
    val currentTitle = remember{
        mutableStateOf(note.value.title)
    }
    val currentPhotos = remember{
        mutableStateOf(note.value.imageUri)
    }
    val saveButtonState = remember{
        mutableStateOf(false)
    }

    val getImageRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = {uri->
            if(uri != null){
                PhotoNotesApp.getUriPermission(uri)
            }
            currentPhotos.value = uri.toString()
            if(currentPhotos.value != note .value.imageUri){
                saveButtonState.value = true
            }
        }
    )

    LaunchedEffect(true){
        scope.launch(Dispatchers.IO){
            note.value = viewModel.getNote(noteId?: 0)?: Constants.noteDetailPlaceHolder
            currentNote.value = note.value.note
            currentTitle.value = note.value.title
            currentPhotos.value = note.value.imageUri

        }
    }


    PhotoNotesTheme{
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background){
            Scaffold(
                topBar = { GenericAppBar(
                    title = "Edit Note",
                    onIconClick = {
                        viewModel.updateNote(
                            Note(
                                id=note.value.id,
                                note = note.value.note,
                                title = note.value.title,
                                imageUri = note.value.imageUri
                            )
                        )
                        navController.popBackStack()
                    },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id= R.drawable.save),
                            contentDescription = stringResource(id = R.string.save_note),
                            tint = Color.Black
                        )
                    },
                    iconState = saveButtonState
                )
                },
                floatingActionButton = {
                    NotesFab(
                        contentDescription= stringResource(id = R.string.add_photo),
                        action = {
                            getImageRequest.launch(arrayOf(("image/*")))
                        },
                        icon = R.drawable.camera
                    )
                }
            ) {
                Column(modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize()){
                    if(currentPhotos.value!= null && currentPhotos.value!!.isNotEmpty()){
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest
                                    .Builder(LocalContext.current)
                                    .data(data= Uri.parse(currentPhotos.value))
                                    .build()
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxHeight(0.3f)
                                .fillMaxWidth()
                                .padding(6.dp),

                            contentScale = ContentScale.Crop
                        )
                    }
                    TextField(
                        value = currentTitle.value,
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor =  Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        onValueChange = {value ->
                            currentTitle.value = value
                            if(currentTitle.value != note.value.title){
                                saveButtonState.value = true
                            } else if(currentNote.value == note.value.note &&
                                currentTitle.value == note.value.title){
                                saveButtonState.value  = false
                            }
                        },
                        label = {Text("Title")}
                    )
                    Spacer(modifier = Modifier.padding(12.dp))
                    TextField(
                        value = currentNote.value,
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor =  Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        onValueChange = {value ->
                            currentNote.value = value
                            if(currentNote.value != note.value.note){
                                saveButtonState.value = true
                            } else if(currentNote.value == note.value.note &&
                                currentTitle.value == note.value.title){
                                saveButtonState.value  = false
                            }
                        },
                        label = {Text("Body")}
                    )
                }


            }
        }
    }
    
    
    
    
}