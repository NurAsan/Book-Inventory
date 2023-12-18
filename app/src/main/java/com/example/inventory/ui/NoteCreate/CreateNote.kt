package com.example.inventory.ui.NoteCreate

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
fun CreateNoteScreen(
    navController: NavController,
    viewModel: NotesViewModel,
){


    val currentNote = remember{
        mutableStateOf("")
    }

    val currentTitle = remember{
        mutableStateOf("")
    }

    val currentPhotos = remember{
        mutableStateOf("")
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
        }
    )


    PhotoNotesTheme{
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background){
            Scaffold(
                topBar = { GenericAppBar(
                    title = "Create Note",
                    onIconClick = {
                        viewModel.createNote(
                            currentTitle.value,
                            currentNote.value,
                            currentPhotos.value
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
                    if(currentPhotos.value.isNotEmpty()){
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
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor =  Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        onValueChange = {value ->
                            currentTitle.value = value
                            saveButtonState.value= currentTitle.value!= "" && currentNote.value !=""
                        },
                        label = { Text("Title") }
                    )
                    Spacer(modifier = Modifier.padding(12.dp))
                    TextField(
                        value = currentNote.value,
                        modifier = Modifier.
                            fillMaxHeight(0.5f)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor =  Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        onValueChange = {value ->
                            currentNote.value = value
                            saveButtonState.value= currentTitle.value!= "" && currentNote.value !=""
                        },
                        label = { Text("Body") }
                    )
                }


            }
        }
    }




}