package com.example.baitaplap

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.baitaplap.databinding.ActivityLab1Binding

class Lab1Activity : AppCompatActivity() {

    private lateinit var binding: ActivityLab1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate view binding
        binding = ActivityLab1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bắt sự kiện Click nút đăng ký
        binding.btnRegister.setOnClickListener {
            val name = binding.edtName.text?.toString()?.trim().orEmpty()
            val email = binding.edtEmail.text?.toString()?.trim().orEmpty()
            val password = binding.edtPassword.text?.toString()?.trim().orEmpty()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}