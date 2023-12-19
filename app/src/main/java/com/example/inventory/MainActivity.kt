/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.inventory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.inventory.ui.NoteCreate.CreateNoteScreen
import com.example.inventory.ui.NoteDetail.NoteDetailPage
import com.example.inventory.ui.NoteEdit.NoteEditScreen
import com.example.inventory.ui.NoteList.NoteListScreen

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: NotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = NoteViewModelFactory(PhotoNotesApp.getDao()).create(NotesViewModel::class.java)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Constants.NAVIGATION_NOTES_LIST ){
                //Notes List
                composable(Constants.NAVIGATION_NOTES_LIST){
                    NoteListScreen(navController = navController, viewModel = viewModel )
                }

                //note detail page
                composable(
                    Constants.NAVIGATION_NOTES_DETAIL,
                    arguments = listOf(navArgument(Constants.NAVIGATION_NOTES_ID_ARGUMENT){
                        type = NavType.IntType
                    })
                ){
                    navBackStackEntry ->
                    navBackStackEntry.arguments?.getInt(Constants.NAVIGATION_NOTES_ID_ARGUMENT)?.let{
                        NoteDetailPage(noteId = it, navController = navController, viewModel = viewModel )
                    }
                }

                composable(
                    Constants.NAVIGATION_NOTES_EDIT,
                    arguments = listOf(navArgument(Constants.NAVIGATION_NOTES_ID_ARGUMENT){
                        type = NavType.IntType
                    })
                ){
                        navBackStackEntry ->
                    navBackStackEntry.arguments?.getInt(Constants.NAVIGATION_NOTES_ID_ARGUMENT)?.let{
                        NoteEditScreen(noteId = it, navController = navController, viewModel = viewModel )
                    }
                }


                //note create page
                composable(
                    Constants.NAVIGATION_NOTES_CREATE
                ){
                    CreateNoteScreen(navController = navController, viewModel = viewModel)
                }


            }
        }



    }
}
