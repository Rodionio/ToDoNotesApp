package com.example.todonotesapp.ui.theme.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todonotesapp.data.local.entity.NoteEntity
import com.example.todonotesapp.ui.theme.animation.StrikeThroughText
import com.example.todonotesapp.viewmodel.NoteViewModel


@Composable
fun NoteListColumn(
    notes: List<NoteEntity>,
    viewModel: NoteViewModel,
    navController: NavController
) {
    val addNoteShowDialog = remember { mutableStateOf(false) }
    val deleteNoteShowDialog = remember { mutableStateOf(false) }
    val noteIdToDelete = remember { mutableStateOf<Long?>(null) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(Color.White),


        ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(top = 15.dp),
            shape = RoundedCornerShape(15.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

                ) {

                Text(
                    modifier = Modifier,
                    text = "Your to do list",
                    fontSize = 40.sp,
                    fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                    color = Color.White

                )
            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize(0.8f)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(10.dp),

                ) {
                items(notes) { note ->
                    val scale by animateFloatAsState(
                        targetValue = if (note.isDone) 0.9f else 1f,
                        animationSpec = tween(durationMillis = 300),
                        label = "CardScale"
                    )
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .scale(scale)
                            .padding(4.dp)
                            .clickable { navController.navigate("note_detail/${note.id}") },
                        shape = RoundedCornerShape(15.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .height(130.dp)
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            StrikeThroughText(
                                text = note.title,
                                isDone = note.isDone,
                                fontSize = 23.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

                            StrikeThroughText(
                                text = note.content,
                                isDone = note.isDone,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black,
                                maxLines = 3, // Increased maxLines for content
                                overflow = TextOverflow.Ellipsis
                            )
                        }


                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(8.dp),
                            onClick = { viewModel.toggleNoteDone(note.id) },
                            colors = ButtonDefaults.buttonColors(Color.Black)
                        ) {
                            Text(text = if (note.isDone) "Not Done" else "Done")
                        }
                        Button(
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(8.dp),
                            onClick = {
                                noteIdToDelete.value = note.id
                                deleteNoteShowDialog.value = true
                            },
                            colors = ButtonDefaults.buttonColors(Color.Black)
                        ) {
                            Text(text = "Delete note")
                        }

                    }

                }

            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End),
                onClick = {
                    addNoteShowDialog.value = true
                },
                colors = ButtonDefaults.buttonColors(Color.Black)
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add",
                    modifier = Modifier.size(20.dp)
                )
                Text(text = "Add new task", fontSize = 20.sp)

            }
        }

        if (addNoteShowDialog.value) {
            AddNoteDialog(
                showDialog = addNoteShowDialog,
                onDismiss = { addNoteShowDialog.value = false },
                onConfirm = { title, content ->
                    viewModel.addNewNote(title, content)
                    addNoteShowDialog.value = false
                })
        }

        if (deleteNoteShowDialog.value) {
            DeleteAlertDialog(
                showDialog = deleteNoteShowDialog,
                onDismiss = { deleteNoteShowDialog.value = false },
                onConfirm = {
                    val id = noteIdToDelete.value
                    if (id != null) {
                        viewModel.deleteNote(id)
                    }
                    deleteNoteShowDialog.value = false
                }
            )
        }

    }


}


@Composable
fun TextFieldTitle(title: MutableState<String>) {

    TextField(
        value = title.value,
        onValueChange = { title.value = it },
        label = { Text(text = "Title", fontSize = 15.sp) }
    )

}

@Composable
fun TextFieldContent(content: MutableState<String>) {

    TextField(
        value = content.value,
        onValueChange = { content.value = it },
        label = { Text(text = "Content", fontSize = 15.sp) }
    )

}


@Composable
fun AddNoteDialog(
    showDialog: MutableState<Boolean>,
    onDismiss: () -> Unit,
    onConfirm: (title: String, content: String) -> Unit
) {

    val title = remember { mutableStateOf("") }
    val content = remember { mutableStateOf("") }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = "Add new task") },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        TextFieldTitle(title)
                        TextFieldContent(content)
                    }


                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm(title.value, content.value)
                        showDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(Color.Black)
                ) {
                    Text(text = "Save")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        onDismiss()
                        showDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(Color.Black)
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}


@Composable
fun DeleteAlertDialog(
    showDialog: MutableState<Boolean>,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = "Delete this note?") },
            confirmButton = {
                Button(
                    onClick = { onConfirm() },
                    colors = ButtonDefaults.buttonColors(Color.Black)
                ) {
                    Text(text = "Delete")
                }
            },
            dismissButton = {
                Button(
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(Color.Black)

                )
                {
                    Text("Cancel")
                }
            }
        )
    }
}
