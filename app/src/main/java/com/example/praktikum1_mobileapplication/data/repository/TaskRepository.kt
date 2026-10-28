package com.example.praktikum1_mobileapplication.data.repository

import com.example.praktikum1_mobileapplication.data.local.TaskDao
import com.example.praktikum1_mobileapplication.data.local.TaskEntity
import com.example.praktikum1_mobileapplication.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository layer yang mengatur sumber data tugas menggunakan Room DAO.
 * Menyediakan data secara offline-first sesuai ketentuan teknis.
 */
class TaskRepository(private val taskDao: TaskDao) {

    val tasksFlow: Flow<List<Task>> = taskDao.getAllTasks().map { entities ->
        entities.map { it.toTask() }
    }

    suspend fun insertTask(task: Task) {
        val entity = TaskEntity.fromTask(task).copy(id = 0L) // autoGenerate
        taskDao.insertTask(entity)
    }

    suspend fun updateTask(task: Task) {
        val entity = TaskEntity.fromTask(task)
        taskDao.updateTask(entity)
    }

    suspend fun deleteTask(taskId: Long) {
        taskDao.deleteTaskById(taskId)
    }
}
