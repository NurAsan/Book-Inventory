package com.example.inventory.ui.NoteDetail

import android.annotation.SuppressLint
import android.icu.text.CaseMap.Title
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.inventory.Constants
import com.example.inventory.Constants.noteDetailPlaceHolder
import com.example.inventory.NotesViewModel
import com.example.inventory.R
import com.example.inventory.ui.GenericAppBar
import com.example.inventory.ui.theme.PhotoNotesTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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

    PhotoNotesTheme{
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background){
            Scaffold(
                topBar = { GenericAppBar(
                    title = note.value.title,
                    onIconClick = { navController.navigate(Constants.noteDetailNavigation(note.value.id?:0))},
                    icon = {
                           Icon(
                               imageVector = ImageVector.vectorResource(id=R.drawable.edit_note),
                               contentDescription = stringResource(id = R.string.edit_note),
                               tint = Color.Black
                           )
                    },
                    iconState = remember{
                        mutableStateOf(true)
                    }
                )}
            ) {
                Column(modifier = Modifier.fillMaxSize()){
                    if(note.value.imageUri != null && note.value.imageUri!!.isNotEmpty()){
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest
                                    .Builder(LocalContext.current)
                                    .data(data= Uri.parse(note.value.imageUri))
                                    .build()
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxHeight(0.3f)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Text(
                        text = note.value.title,
                        modifier = Modifier.padding(top= 24.dp,start=24.dp,end=24.dp),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = note.value.dateUpdated,
                        modifier = Modifier.padding(12.dp),
                        fontSize = 26.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = note.value.note,
                        modifier = Modifier.padding(12.dp),
                        fontSize = 30.sp
                    )
                }



            }
        }
    }

}