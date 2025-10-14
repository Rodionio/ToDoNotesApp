package com.example.todonotesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.todonotesapp.navigation.Navigation
import com.example.todonotesapp.viewmodel.NoteViewModel
import com.example.todonotesapp.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as ToDoNotesApp
        val factory = ViewModelFactory(app.noteRepository)
        val viewModel: NoteViewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]

        setContent {


            val navController = rememberNavController()
            Navigation(navController = navController, viewModel = viewModel)


        }
    }
}



