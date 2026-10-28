package com.example.praktikum1_mobileapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.praktikum1_mobileapplication.data.model.DummyTasks
import com.example.praktikum1_mobileapplication.data.model.Task
import com.example.praktikum1_mobileapplication.data.model.TaskStatus
import com.example.praktikum1_mobileapplication.ui.screen.TaskDetailScreen
import com.example.praktikum1_mobileapplication.ui.screen.TaskFormScreen
import com.example.praktikum1_mobileapplication.ui.screen.TaskListScreen
import com.example.praktikum1_mobileapplication.ui.theme.Praktikum1_MobileApplicationTheme

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
fun TaskStudentApp() {
    var taskList by remember { mutableStateOf(DummyTasks.sampleTasks) }
    var currentScreen by remember { mutableStateOf<Screen>(Screen.List) }

    when (val screen = currentScreen) {
        is Screen.List -> {
            TaskListScreen(
                tasks = taskList,
                onTaskClick = { task -> currentScreen = Screen.Detail(task) },
                onAddTaskClick = { currentScreen = Screen.Add },
                onStatusChange = { task, newStatus ->
                    taskList = taskList.map {
                        if (it.id == task.id) it.copy(status = newStatus) else it
                    }
                }
            )
        }
        is Screen.Detail -> {
            // Find latest version of task in taskList
            val currentTask = taskList.find { it.id == screen.task.id } ?: screen.task
            TaskDetailScreen(
                task = currentTask,
                onBackClick = { currentScreen = Screen.List },
                onEditClick = { currentScreen = Screen.Edit(currentTask) },
                onDeleteClick = {
                    taskList = taskList.filter { it.id != currentTask.id }
                    currentScreen = Screen.List
                },
                onStatusChange = { newStatus ->
                    taskList = taskList.map {
                        if (it.id == currentTask.id) it.copy(status = newStatus) else it
                    }
                }
            )
        }
        is Screen.Add -> {
            TaskFormScreen(
                task = null,
                onBackClick = { currentScreen = Screen.List },
                onSaveTask = { mataKuliah, judul, deskripsi, deadline, prioritas, status ->
                    val newTask = Task(
                        id = (taskList.maxOfOrNull { it.id } ?: 0L) + 1L,
                        mataKuliah = mataKuliah,
                        judul = judul,
                        deskripsi = deskripsi,
                        deadline = deadline,
                        prioritas = prioritas,
                        status = status
                    )
                    taskList = listOf(newTask) + taskList
                    currentScreen = Screen.List
                }
            )
        }
        is Screen.Edit -> {
            TaskFormScreen(
                task = screen.task,
                onBackClick = { currentScreen = Screen.Detail(screen.task) },
                onSaveTask = { mataKuliah, judul, deskripsi, deadline, prioritas, status ->
                    val updatedTask = screen.task.copy(
                        mataKuliah = mataKuliah,
                        judul = judul,
                        deskripsi = deskripsi,
                        deadline = deadline,
                        prioritas = prioritas,
                        status = status
                    )
                    taskList = taskList.map {
                        if (it.id == updatedTask.id) updatedTask else it
                    }
                    currentScreen = Screen.Detail(updatedTask)
                }
            )
        }
    }
}
