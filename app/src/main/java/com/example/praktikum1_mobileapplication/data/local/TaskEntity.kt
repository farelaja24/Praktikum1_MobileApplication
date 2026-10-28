package com.example.praktikum1_mobileapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.praktikum1_mobileapplication.data.model.Task
import com.example.praktikum1_mobileapplication.data.model.TaskPriority
import com.example.praktikum1_mobileapplication.data.model.TaskStatus

/**
 * Room Entity yang merepresentasikan tabel "tasks" di database lokal SQLite.
 */
@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val mataKuliah: String,
    val judul: String,
    val deskripsi: String,
    val deadline: String,
    val prioritas: TaskPriority,
    val status: TaskStatus
) {
    fun toTask(): Task {
        return Task(
            id = id,
            mataKuliah = mataKuliah,
            judul = judul,
            deskripsi = deskripsi,
            deadline = deadline,
            prioritas = prioritas,
            status = status
        )
    }

    companion object {
        fun fromTask(task: Task): TaskEntity {
            return TaskEntity(
                id = task.id,
                mataKuliah = task.mataKuliah,
                judul = task.judul,
                deskripsi = task.deskripsi,
                deadline = task.deadline,
                prioritas = task.prioritas,
                status = task.status
            )
        }
    }
}
