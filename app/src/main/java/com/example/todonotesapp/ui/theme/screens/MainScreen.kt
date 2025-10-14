package com.example.todonotesapp.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.todonotesapp.ui.theme.components.NoteListColumn
import com.example.todonotesapp.viewmodel.NoteListState
import com.example.todonotesapp.viewmodel.NoteViewModel

@Composable
fun MainScreen(viewModel: NoteViewModel, navController: NavHostController) {

    val state by viewModel.uiState.collectAsState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        when (state) {
            is NoteListState.Loading -> {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Loading...",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            is NoteListState.Empty -> {
                NoteListColumn(notes = emptyList(), viewModel,navController = navController)

                Text(
                    text = "Now, you don't have any tasks",
                    modifier = Modifier.padding(10.dp).align(Alignment.Center),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

            }

            is NoteListState.Content -> {
                val notes = (state as NoteListState.Content).notes
                NoteListColumn(notes = notes, viewModel,navController = navController)
            }

            is NoteListState.Error -> {
                Text("ERROR")
            }
            is NoteListState.EditingNote -> {

            }
        }



    }


}


