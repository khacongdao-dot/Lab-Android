package com.example.baitaplap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

// 1. Model dữ liệu Khóa học
data class Course(
    val id: Int,
    val title: String,
    val author: String
)

// 2. Sealed interface quản lý 3 trạng thái UI
sealed interface CourseUiState {
    data object Loading : CourseUiState
    data class Success(val list: List<Course>) : CourseUiState
    data class Error(val message: String) : CourseUiState
}

class Lab5UiStateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Lab5Screen(onBack = { finish() })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Lab5Screen(onBack: () -> Unit) {
    var uiState by remember { mutableStateOf<CourseUiState>(CourseUiState.Loading) }
    var reloadKey by remember { mutableIntStateOf(0) }

    // Giả lập call API / Tải dữ liệu bằng LaunchedEffect & delay
    LaunchedEffect(reloadKey) {
        uiState = CourseUiState.Loading
        delay(1200) // Đợi 1.2 giây
        uiState = if (reloadKey % 2 == 0) {
            CourseUiState.Success(
                listOf(
                    Course(1, "Lập trình Android Kotlin", "Ths. Nguyễn Quốc Dũng"),
                    Course(2, "Thiết kế giao diện Jetpack Compose", "Ths. Nguyễn Quốc Dũng")
                )
            )
        } else {
            CourseUiState.Error("Không thể tải danh sách khóa học. Vui lòng thử lại!")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("UI State — Khóa học") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Khi uiState thay đổi -> Chuyển giao diện tương ứng
            when (val state = uiState) {
                is CourseUiState.Loading -> LoadingContent()
                is CourseUiState.Success -> SuccessContent(
                    courses = state.list,
                    onReload = { reloadKey++ }
                )
                is CourseUiState.Error -> ErrorContent(
                    message = state.message,
                    onRetry = { reloadKey++ }
                )
            }
        }
    }
}

@Composable
fun LoadingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(16.dp))
        Text("Đang tải...")
    }
}

@Composable
fun SuccessContent(courses: List<Course>, onReload: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(courses, key = { it.id }) { course ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = course.title, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Giảng viên: ${course.author}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
        Button(
            onClick = onReload,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tải lại (Giả lập Lỗi)")
        }
    }
}

@Composable
fun ErrorContent(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Thử lại")
        }
    }
}