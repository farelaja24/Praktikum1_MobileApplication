package com.example.praktikum1_mobileapplication.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.praktikum1_mobileapplication.data.model.Task
import com.example.praktikum1_mobileapplication.data.model.TaskPriority
import com.example.praktikum1_mobileapplication.data.model.TaskStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFormScreen(
    task: Task? = null, // If null -> Add Mode, If not null -> Edit Mode
    onBackClick: () -> Unit,
    onSaveTask: (mataKuliah: String, judul: String, deskripsi: String, deadline: String, prioritas: TaskPriority, status: TaskStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    var mataKuliah by remember { mutableStateOf(task?.mataKuliah ?: "") }
    var judul by remember { mutableStateOf(task?.judul ?: "") }
    var deskripsi by remember { mutableStateOf(task?.deskripsi ?: "") }
    var deadline by remember { mutableStateOf(task?.deadline ?: "2026-03-10 23:59") }
    var prioritas by remember { mutableStateOf(task?.prioritas ?: TaskPriority.SEDANG) }
    var status by remember { mutableStateOf(task?.status ?: TaskStatus.BELUM) }

    var showError by remember { mutableStateOf(false) }

    val isEditMode = task != null

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Edit Tugas" else "Tambah Tugas Baru", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                tonalElevation = 8.dp
            ) {
                Button(
                    onClick = {
                        if (mataKuliah.isBlank() || judul.isBlank() || deskripsi.isBlank()) {
                            showError = true
                        } else {
                            onSaveTask(mataKuliah, judul, deskripsi, deadline, prioritas, status)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = if (isEditMode) "Simpan Perubahan" else "Simpan Tugas", fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (showError) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Text(
                        text = "Semua field wajib diisi (Mata Kuliah, Judul, dan Deskripsi).",
                        modifier = Modifier.padding(12.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Mata Kuliah Field
            OutlinedTextField(
                value = mataKuliah,
                onValueChange = { mataKuliah = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Mata Kuliah *") },
                placeholder = { Text("Contoh: Pemrograman Mobile") },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            // Judul Tugas Field
            OutlinedTextField(
                value = judul,
                onValueChange = { judul = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Judul Tugas *") },
                placeholder = { Text("Contoh: Laporan Praktikum 2") },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            // Deskripsi Field
            OutlinedTextField(
                value = deskripsi,
                onValueChange = { deskripsi = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Deskripsi Detail *") },
                placeholder = { Text("Tuliskan detail instruksi tugas...") },
                shape = RoundedCornerShape(12.dp),
                minLines = 3
            )

            // Deadline Field
            OutlinedTextField(
                value = deadline,
                onValueChange = { deadline = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Batas Waktu (Deadline)") },
                placeholder = { Text("YYYY-MM-DD HH:MM") },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            // Prioritas Selector
            Text(
                text = "Tingkat Prioritas",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TaskPriority.entries.forEach { p ->
                    FilterChip(
                        selected = prioritas == p,
                        onClick = { prioritas = p },
                        label = { Text(p.displayName) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Status Selector
            Text(
                text = "Status Pengerjaan",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TaskStatus.entries.forEach { s ->
                    FilterChip(
                        selected = status == s,
                        onClick = { status = s },
                        label = { Text(s.displayName) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
