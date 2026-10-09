package com.example.baitaplap

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val LightColors = lightColorScheme(
    primary = Color(0xFF0D9488),
    onPrimary = Color.White,
    surface = Color(0xFFF8FAFC),
    onSurface = Color(0xFF0F172A)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF2DD4BF),
    surface = Color(0xFF0F172A),
    onSurface = Color(0xFFF1F5F9)
)

class Lab4ThemeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var darkMode by remember { mutableStateOf(false) }

            MaterialTheme(colorScheme = if (darkMode) DarkColors else LightColors) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ThemeDemoScreen(
                        darkMode = darkMode,
                        onDarkModeChange = { darkMode = it },
                        onBack = { finish() }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeDemoScreen(
    darkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Giao diện & Theme (Lab 4)") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                actions = {
                    TextButton(onClick = {
                        val intent = Intent(context, Lab5UiStateActivity::class.java)
                        context.startActivity(intent)
                    }) {
                        Text("Sang Lab 5 >")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Chế độ tối (Dark Mode)", style = MaterialTheme.typography.titleMedium)
                    Text("Sử dụng giao diện tối để tiết kiệm pin", fontSize = 12.sp)
                }
                Switch(
                    checked = darkMode,
                    onCheckedChange = onDarkModeChange
                )
            }

            Text("MÀU SẮC (ROLES)", style = MaterialTheme.typography.labelLarge)

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Info, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Tiêu đề thẻ", style = MaterialTheme.typography.titleMedium)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Đây là nội dung mẫu của thẻ. Nó tự động cập nhật theo Theme.")
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = {}) {
                        Text("HÀNH ĐỘNG")
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Nút mở Lab 5
            Button(
                onClick = {
                    val intent = Intent(context, Lab5UiStateActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mở Lab 5 (UI State)")
            }
        }
    }
}