package com.example.baitaplap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Student(
    val id: String,
    val name: String,
    val className: String
)

class Lab2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StudentListScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentListScreen() {
    val studentList = listOf(
        Student("SV01", "Nguyễn Văn A", "KTPM01"),
        Student("SV02", "Trần Thị B", "KTPM01"),
        Student("SV03", "Lê Văn C", "KTPM02"),
        Student("SV04", "Phạm Thị D", "KTPM02"),
        Student("SV05", "Hoàng Văn E", "KTPM03")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Danh sách sinh viên (Lab 2)") }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(studentList) { student ->
                StudentItem(student)
            }
        }
    }
}

@Composable
fun StudentItem(student: Student) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Mã SV: ${student.id}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Họ tên: ${student.name}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Lớp: ${student.className}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}