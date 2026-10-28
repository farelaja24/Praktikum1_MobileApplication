package com.example.praktikum1_mobileapplication.data.model

/**
 * Enum yang merepresentasikan status dari suatu tugas mahasiswa.
 */
enum class TaskStatus(val displayName: String) {
    BELUM("Belum Dikerjakan"),
    PROSES("Dalam Proses"),
    SELESAI("Selesai")
}
