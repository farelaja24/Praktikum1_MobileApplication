package com.example.praktikum1_mobileapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikum1_mobileapplication.data.model.Task
import com.example.praktikum1_mobileapplication.data.model.TaskPriority
import com.example.praktikum1_mobileapplication.data.model.TaskStatus
import com.example.praktikum1_mobileapplication.data.repository.TaskRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel untuk mengelola state dan business logic aplikasi Manajemen Tugas Mahasiswa.
 * Mengimplementasikan pemisahan lapisan (Separation of Concerns): UI -> ViewModel -> Repository.
 */
class TaskViewModel(
    private val repository: TaskRepository = TaskRepository()
) : ViewModel() {

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
        val newTask = Task(
            mataKuliah = mataKuliah,
            judul = judul,
            deskripsi = deskripsi,
            deadline = deadline,
            prioritas = prioritas,
            status = status
        )
        repository.insertTask(newTask)
        loadTasks()
    }

    fun updateTask(task: Task, mataKuliah: String, judul: String, deskripsi: String, deadline: String, prioritas: TaskPriority, status: TaskStatus) {
        val updated = task.copy(
            mataKuliah = mataKuliah,
            judul = judul,
            deskripsi = deskripsi,
            deadline = deadline,
            prioritas = prioritas,
            status = status
        )
        repository.updateTask(updated)
        loadTasks()
    }

    fun updateTaskStatus(task: Task, newStatus: TaskStatus) {
        val updated = task.copy(status = newStatus)
        repository.updateTask(updated)
        loadTasks()
    }

    fun deleteTask(taskId: Long) {
        repository.deleteTask(taskId)
        loadTasks()
    }
}
