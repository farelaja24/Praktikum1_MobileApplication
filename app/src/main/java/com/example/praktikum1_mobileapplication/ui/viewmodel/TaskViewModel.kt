package com.example.praktikum1_mobileapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikum1_mobileapplication.data.local.TaskDatabase
import com.example.praktikum1_mobileapplication.data.model.Task
import com.example.praktikum1_mobileapplication.data.model.TaskPriority
import com.example.praktikum1_mobileapplication.data.model.TaskStatus
import com.example.praktikum1_mobileapplication.data.repository.TaskRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel untuk mengelola state dan business logic aplikasi Manajemen Tugas Mahasiswa.
 * Berinteraksi dengan Room Database melalui TaskRepository.
 */
class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
    }

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedFilter = MutableStateFlow<TaskStatus?>(null)
    val selectedFilter: StateFlow<TaskStatus?> = _selectedFilter.asStateFlow()

    // Filtered tasks berdasarkan search query dan status filter
    val filteredTasks: StateFlow<List<Task>> = combine(_tasks, _searchQuery, _selectedFilter) { taskList, query, filter ->
        taskList.filter { task ->
            val matchesSearch = task.judul.contains(query, ignoreCase = true) ||
                    task.mataKuliah.contains(query, ignoreCase = true)
            val matchesFilter = filter == null || task.status == filter
            matchesSearch && matchesFilter
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            repository.tasksFlow.collect { taskList ->
                _tasks.value = taskList
            }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setFilter(status: TaskStatus?) {
        _selectedFilter.value = status
    }

    fun addTask(mataKuliah: String, judul: String, deskripsi: String, deadline: String, prioritas: TaskPriority, status: TaskStatus) {
        viewModelScope.launch {
            val newTask = Task(
                mataKuliah = mataKuliah,
                judul = judul,
                deskripsi = deskripsi,
                deadline = deadline,
                prioritas = prioritas,
                status = status
            )
            repository.insertTask(newTask)
        }
    }

    fun updateTask(task: Task, mataKuliah: String, judul: String, deskripsi: String, deadline: String, prioritas: TaskPriority, status: TaskStatus) {
        viewModelScope.launch {
            val updated = task.copy(
                mataKuliah = mataKuliah,
                judul = judul,
                deskripsi = deskripsi,
                deadline = deadline,
                prioritas = prioritas,
                status = status
            )
            repository.updateTask(updated)
        }
    }

    fun updateTaskStatus(task: Task, newStatus: TaskStatus) {
        viewModelScope.launch {
            val updated = task.copy(status = newStatus)
            repository.updateTask(updated)
        }
    }

    fun deleteTask(taskId: Long) {
        viewModelScope.launch {
            repository.deleteTask(taskId)
        }
    }
}
