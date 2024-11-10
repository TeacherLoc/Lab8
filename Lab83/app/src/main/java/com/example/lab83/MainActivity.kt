package com.example.lab83

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit


class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private var openCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)

        // Lấy số lần mở từ SharedPreferences
        openCount = sharedPreferences.getInt("open_count", 0)

        // Tăng số lần mở lên 1
        openCount++

        // Lưu lại số lần mở vào SharedPreferences
        sharedPreferences.edit {
            putInt("open_count", openCount)
        }

        // Hiển thị số lần mở trên TextView
        findViewById<TextView>(R.id.text_open_count).text = "Số lần mở ứng dụng: $openCount"
    }
}
