package com.example.baitaplap

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

// Data model dùng chung cho các Lab
data class Student(
    val id: Int,
    val name: String,
    val major: String,
    val email: String = "",
    val phone: String = ""
)

val sampleStudents = listOf(
    Student(1, "Nguyễn Văn An", "Công nghệ thông tin", "an.nguyen@example.com", "0123 456 789"),
    Student(2, "Trần Thị Bích", "Kỹ thuật phần mềm", "bich.tran@example.com", "0987 654 321"),
    Student(3, "Lê Văn Cường", "Hệ thống thông tin", "cuong.le@example.com", "0911 223 344"),
    Student(4, "Phạm Thị Dung", "An toàn thông tin", "dung.pham@example.com", "0933 445 566")
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
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Danh sách sinh viên (Lab 2)") },
                actions = {
                    // Nút chuyển nhanh sang Lab 4 trên thanh tiêu đề
                    TextButton(onClick = {
                        val intent = Intent(context, Lab4ThemeActivity::class.java)
                        context.startActivity(intent)
                    }) {
                        Text("Sang Lab 4 >")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(sampleStudents, key = { it.id }) { student ->
                    StudentItem(student = student, onClick = {
                        // Mở sang Lab 3 khi bấm vào từng item
                        val intent = Intent(context, Lab3DetailActivity::class.java).apply {
                            putExtra(Lab3DetailActivity.EXTRA_STUDENT_ID, student.id)
                        }
                        context.startActivity(intent)
                    })
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Nút to bên dưới mở Lab 4
            Button(
                onClick = {
                    val intent = Intent(context, Lab4ThemeActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mở Lab 4 (Theme & Dark Mode)")
            }
        }
    }
}

@Composable
fun StudentItem(student: Student, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Họ tên: ${student.name}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Ngành: ${student.major}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}