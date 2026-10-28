package com.example.praktikum1_mobileapplication.data.model

/**
 * Model Data Utama untuk Tugas Mahasiswa.
 * Memenuhi spesifikasi field minimal:
 * - id: Pembeda unik setiap tugas
 * - mataKuliah: Nama mata kuliah terkait
 * - judul: Judul tugas
 * - deskripsi: Deskripsi detail tugas
 * - deadline: Tanggal & waktu batas pengumpulan
 * - prioritas: Tingkat urgensi tugas (Tinggi, Sedang, Rendah)
 * - status: Status pengerjaan tugas (Belum Dikerjakan, Dalam Proses, Selesai)
 */
data class Task(
    val id: Long = 0L,
    val mataKuliah: String,
    val judul: String,
    val deskripsi: String,
    val deadline: String,
    val prioritas: TaskPriority = TaskPriority.SEDANG,
    val status: TaskStatus = TaskStatus.BELUM
)
