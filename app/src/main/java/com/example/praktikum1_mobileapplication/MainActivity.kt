package com.example.praktikum1_mobileapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.praktikum1_mobileapplication.data.model.Task
import com.example.praktikum1_mobileapplication.ui.screen.TaskDetailScreen
import com.example.praktikum1_mobileapplication.ui.screen.TaskFormScreen
import com.example.praktikum1_mobileapplication.ui.screen.TaskListScreen
import com.example.praktikum1_mobileapplication.ui.theme.Praktikum1_MobileApplicationTheme
import com.example.praktikum1_mobileapplication.ui.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Praktikum1_MobileApplicationTheme {
                TaskStudentApp()
            }
        }
    }
}

sealed class Screen {
    object List : Screen()
    data class Detail(val task: Task) : Screen()
    object Add : Screen()
    data class Edit(val task: Task) : Screen()
}

@Composable
fun TaskStudentApp(viewModel: TaskViewModel = viewModel()) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.List) }
    val allTasks by viewModel.tasks.collectAsStateWithLifecycle()

    when (val screen = currentScreen) {
        is Screen.List -> {
            TaskListScreen(
                viewModel = viewModel,
                onTaskClick = { task -> currentScreen = Screen.Detail(task) },
                onAddTaskClick = { currentScreen = Screen.Add }
            )
        }
        is Screen.Detail -> {
            val currentTask = allTasks.find { it.id == screen.task.id } ?: screen.task
            TaskDetailScreen(
                task = currentTask,
                onBackClick = { currentScreen = Screen.List },
                onEditClick = { currentScreen = Screen.Edit(currentTask) },
                onDeleteClick = {
                    viewModel.deleteTask(currentTask.id)
                    currentScreen = Screen.List
                },
                onStatusChange = { newStatus ->
                    viewModel.updateTaskStatus(currentTask, newStatus)
                }
            )
        }
        is Screen.Add -> {
            TaskFormScreen(
                task = null,
                onBackClick = { currentScreen = Screen.List },
                onSaveTask = { mataKuliah, judul, deskripsi, deadline, prioritas, status ->
                    viewModel.addTask(mataKuliah, judul, deskripsi, deadline, prioritas, status)
                    currentScreen = Screen.List
                }
            )
        }
        is Screen.Edit -> {
            TaskFormScreen(
                task = screen.task,
                onBackClick = { currentScreen = Screen.Detail(screen.task) },
                onSaveTask = { mataKuliah, judul, deskripsi, deadline, prioritas, status ->
                    viewModel.updateTask(screen.task, mataKuliah, judul, deskripsi, deadline, prioritas, status)
                    val updated = screen.task.copy(
                        mataKuliah = mataKuliah,
                        judul = judul,
                        deskripsi = deskripsi,
                        deadline = deadline,
                        prioritas = prioritas,
                        status = status
                    )
                    currentScreen = Screen.Detail(updated)
                }
            )
        }
    }
}
