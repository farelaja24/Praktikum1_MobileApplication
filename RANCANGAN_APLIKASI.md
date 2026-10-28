# Rancangan Aplikasi: Task Student (Aplikasi Manajemen Tugas Mahasiswa Offline)

Dokumen ini berisi rancangan untuk **Milestone 1** pengembangan aplikasi mobile manajemen tugas mahasiswa berbasis offline.

---

## 1. Tema & Skenario Aplikasi
- **Jenis Aplikasi:** Aplikasi Manajemen Tugas Mahasiswa Berbasis Offline.
- **Tujuan:** Membantu mahasiswa mencatat, memantau, dan memperbarui status pengerjaan tugas dari berbagai mata kuliah secara teratur tanpa memerlukan koneksi internet.

---

## 2. Model Data (`Task`)
Setiap tugas disimpan dengan struktur data sebagai berikut:

| Field | Tipe Data | Keterangan |
| :--- | :--- | :--- |
| `id` | `Long` | Primary Key unik tugas |
| `mataKuliah` | `String` | Nama mata kuliah (contoh: "Pemrograman Mobile") |
| `judul` | `String` | Judul tugas (contoh: "Laporan Praktikum 1") |
| `deskripsi` | `String` | Penjelasan detail tugas |
| `deadline` | `String` | Tanggal & waktu batas pengumpulan |
| `prioritas` | `TaskPriority` | Tingkat prioritas: `TINGGI`, `SEDANG`, `RENDAH` |
| `status` | `TaskStatus` | Status pengerjaan: `BELUM`, `PROSES`, `SELESAI` |

---

## 3. Rancangan Layar (Screen Overview)

1. **Layar Utama (Daftar Tugas - `TaskListScreen`):**
   - Menampilkan ringkasan statistik (jumlah tugas total, belum, proses, selesai).
   - Menampilkan daftar kartu tugas (`TaskItemCard`) yang memuat judul, matkul, badge prioritas, deadline, dan badge status.
   - Filter & pencarian tugas berdasarkan status atau kata kunci.
   - Floating Action Button (FAB) `+` untuk berpindah ke layar Tambah Tugas.

2. **Layar Detail Tugas (`TaskDetailScreen`):**
   - Menampilkan detail lengkap suatu tugas (Mata Kuliah, Judul, Deskripsi, Deadline, Prioritas, Status).
   - Tombol Aksi: Edit Tugas, Hapus Tugas (dengan Dialog Konfirmasi), dan Cepat Ubah Status.

3. **Layar Form Input / Edit Tugas (`TaskFormScreen`):**
   - Form input serbaguna untuk Menambah Tugas Baru atau Mengubah Tugas yang Ada.
   - Field: Mata Kuliah, Judul, Deskripsi, Batas Waktu, Pilihan Prioritas (Radio/Chips), Pilihan Status.
   - Tombol "Simpan" dengan validasi input dasar.

---

## 4. Alur Pengguna (User Flow)

```
[ Buka Aplikasi ]
       │
       ▼
┌──────────────────────────────┐
│  Daftar Tugas (TaskList)     │◄───────────────────┐
└──────────────┬───────────────┘                    │
               │                                    │
     ┌─────────┴──────────┐                         │
     ▼                    ▼                         │
┌──────────────┐   ┌──────────────┐                 │
│ Detail Tugas │   │ Form Input   │                 │
│ (TaskDetail) │   │ (Tambah)     │                 │
└──────┬───────┘   └──────┬───────┘                 │
       │                  │                         │
       ├──────────────────┼─► [ Simpan Data ] ──────┤
       │                  │                         │
       ▼                  │                         │
┌──────────────┐          │                         │
│ Form Input   │──────────┘                         │
│ (Edit)       │                                    │
└──────────────┘                                    │
       │                                            │
       ▼                                            │
┌──────────────┐                                    │
│ Hapus Tugas  │──────────► [ Data Terhapus ] ──────┘
│ (Konfirmasi) │
└──────────────┘
```

---

## 5. Rencana Arsitektur & Lapisan Data

Aplikasi menerapkan pola arsitektur resmi Android:
`Compose UI` -> `ViewModel` -> `Repository` -> `Room DAO` -> `Database`

1. **UI Layer:** Jetpack Compose screens (`TaskListScreen`, `TaskDetailScreen`, `TaskFormScreen`).
2. **State & Navigation Layer:** Jetpack Navigation & ViewModel memegang `StateFlow<List<Task>>`.
3. **Data Layer:** Room Local Database (`TaskDatabase`, `TaskDao`, `TaskRepository`).
