# Walkthrough - Task Student (Aplikasi Manajemen Tugas Mahasiswa Offline)

Aplikasi manajemen tugas mahasiswa berbasis offline telah selesai dikembangkan melalui **5 Milestone** terstruktur yang memenuhi seluruh ketentuan teknis dan fungsional.

---

## 📋 Ringkasan Milestone & Commit Git yang Disarankan

Untuk memenuhi ketentuan minimal 5 commit bermakna pada repositori GitHub Anda, lakukan commit pada setiap akhir milestone berikut:

1. **Commit 1:** `docs: add initial app design and data model (Milestone 1)`
2. **Commit 2:** `feat: implement Compose UI screens (list, form, detail, edit) (Milestone 2)`
3. **Commit 3:** `feat: implement ViewModel, Repository, and state management (Milestone 3)`
4. **Commit 4:** `feat: implement Room Database local CRUD operations (Milestone 4)`
5. **Commit 5:** `test: final polish, testing, and documentation (Milestone 5)`

---

## 🏗️ Arsitektur & Lapisan Solusi (Separation of Concerns)

Aplikasi menerapkan arsitektur standar industri Android:
```
Compose UI -> ViewModel -> Repository -> Room DAO -> SQLite Database
```

### 1. Model Data (`Task`)
- **Field Utama:** `id` (Long, Primary Key unik), `mataKuliah` (String), `judul` (String), `deskripsi` (String), `deadline` (String), `prioritas` (`TaskPriority`: Tinggi, Sedang, Rendah), `status` (`TaskStatus`: Belum, Proses, Selesai).

### 2. Komponen Utama yang Dibuat:
- **UI Layer:**
  - [TaskItemCard.kt](file:///C:/Users/ROBINW/AndroidStudioProjects/Praktikum1_MobileApplication/app/src/main/java/com/example/praktikum1_mobileapplication/ui/components/TaskItemCard.kt) (Kartu tugas dengan badge prioritas & status)
  - [TaskListScreen.kt](file:///C:/Users/ROBINW/AndroidStudioProjects/Praktikum1_MobileApplication/app/src/main/java/com/example/praktikum1_mobileapplication/ui/screen/TaskListScreen.kt) (Daftar tugas, statistik, pencarian, dan filter)
  - [TaskDetailScreen.kt](file:///C:/Users/ROBINW/AndroidStudioProjects/Praktikum1_MobileApplication/app/src/main/java/com/example/praktikum1_mobileapplication/ui/screen/TaskDetailScreen.kt) (Detail tugas & ubah status cepat)
  - [TaskFormScreen.kt](file:///C:/Users/ROBINW/AndroidStudioProjects/Praktikum1_MobileApplication/app/src/main/java/com/example/praktikum1_mobileapplication/ui/screen/TaskFormScreen.kt) (Form Tambah / Edit Tugas dengan validasi)
- **ViewModel & State Management:**
  - [TaskViewModel.kt](file:///C:/Users/ROBINW/AndroidStudioProjects/Praktikum1_MobileApplication/app/src/main/java/com/example/praktikum1_mobileapplication/ui/viewmodel/TaskViewModel.kt) (Mengelola `StateFlow` untuk reaktifitas UI dan memanggil coroutines)
- **Data & Local Persistence Layer:**
  - [TaskRepository.kt](file:///C:/Users/ROBINW/AndroidStudioProjects/Praktikum1_MobileApplication/app/src/main/java/com/example/praktikum1_mobileapplication/data/repository/TaskRepository.kt) (Abstraksi akses data)
  - [TaskEntity.kt](file:///C:/Users/ROBINW/AndroidStudioProjects/Praktikum1_MobileApplication/app/src/main/java/com/example/praktikum1_mobileapplication/data/local/TaskEntity.kt) (Tabel SQLite Room)
  - [TaskDao.kt](file:///C:/Users/ROBINW/AndroidStudioProjects/Praktikum1_MobileApplication/app/src/main/java/com/example/praktikum1_mobileapplication/data/local/TaskDao.kt) (Query CRUD: Insert, Update, Delete, GetAll)
  - [TaskDatabase.kt](file:///C:/Users/ROBINW/AndroidStudioProjects/Praktikum1_MobileApplication/app/src/main/java/com/example/praktikum1_mobileapplication/data/local/TaskDatabase.kt) (Singleton database dengan inisialisasi data awal)

---

## ✅ Verifikasi & Pengujian
- **Build Status:** Sukses (`assembleDebug` passed without errors).
- **Offline Capability:** 100% berjalan secara lokal menggunakan Room Database (SQLite). Data tetap tersimpan aman saat aplikasi ditutup/dibuka kembali.
- **UI Responsiveness:** Menggunakan Material 3 dengan tata letak yang bersih, intuitif, dan responsif.
