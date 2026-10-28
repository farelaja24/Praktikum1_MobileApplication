package com.example.praktikum1_mobileapplication.data.model

/**
 * Data awal (dummy/mock) untuk keperluan prototipe dan pengetesan UI pada Milestone 1 & 2.
 */
object DummyTasks {
    val sampleTasks = listOf(
        Task(
            id = 1L,
            mataKuliah = "Pemrograman Mobile",
            judul = "Laporan Praktikum 1 - Offline App",
            deskripsi = "Membuat aplikasi manajemen tugas mahasiswa berbasis offline dengan Jetpack Compose & Room Database.",
            deadline = "2026-03-05 23:59",
            prioritas = TaskPriority.TINGGI,
            status = TaskStatus.PROSES
        ),
        Task(
            id = 2L,
            mataKuliah = "Basis Data Lanjut",
            judul = "Desain Skema E-Commerce",
            deskripsi = "Membuat ERD dan normalisasi basis data untuk sistem e-commerce sampai 3NF.",
            deadline = "2026-03-08 17:00",
            prioritas = TaskPriority.SEDANG,
            status = TaskStatus.BELUM
        ),
        Task(
            id = 3L,
            mataKuliah = "Jaringan Komputer",
            judul = "Simulasi Routing Cisco Packet Tracer",
            deskripsi = "Konfigurasi OSPF dan VLAN pada 3 router dan 4 switch.",
            deadline = "2026-03-02 12:00",
            prioritas = TaskPriority.RENDAH,
            status = TaskStatus.SELESAI
        )
    )
}
