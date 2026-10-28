package com.example.praktikum1_mobileapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.praktikum1_mobileapplication.data.model.DummyTasks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Room Database utama aplikasi Task Student.
 * Menyimpan data secara lokal (offline) agar tidak hilang saat aplikasi ditutup.
 */
@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                )
                    .addCallback(TaskDatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class TaskDatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateInitialData(database.taskDao())
                    }
                }
            }
        }

        private suspend fun populateInitialData(dao: TaskDao) {
            if (dao.getTaskCount() == 0) {
                DummyTasks.sampleTasks.forEach { task ->
                    dao.insertTask(TaskEntity.fromTask(task).copy(id = 0L)) // let autoGenerate assign id
                }
            }
        }
    }
}
