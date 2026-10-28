package com.example.praktikum1_mobileapplication.data.repository

import com.example.praktikum1_mobileapplication.data.model.DummyTasks
import com.example.praktikum1_mobileapplication.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Repository layer yang mengatur sumber data tugas.
 * Pada Milestone 3 menggunakan in-memory stateflow (mock repository),
 * yang nantinya akan digabungkan dengan Room DAO pada Milestone 4.
 */
class TaskRepository {
    private val _tasksFlow = MutableStateFlow(DummyTasks.sampleTasks)
    val tasksFlow: Flow<List<Task>> = _tasksFlow.asStateFlow()

    fun getAllTasks(): List<Task> {
        return _tasksFlow.value
    }

    fun getTaskById(id: Long): Task? {
        return _tasksFlow.value.find { it.id == id }
    }

    fun insertTask(task: Task) {
        val newId = (_tasksFlow.value.maxOfOrNull { it.id } ?: 0L) + 1L
        val taskWithId = task.copy(id = newId)
        _tasksFlow.update { current -> listOf(taskWithId) + current }
    }

    fun updateTask(task: Task) {
        _tasksFlow.update { current ->
            current.map { if (it.id == task.id) task else it }
        }
    }

    fun deleteTask(taskId: Long) {
        _tasksFlow.update { current ->
            current.filter { it.id != taskId }
        }
    }
}
