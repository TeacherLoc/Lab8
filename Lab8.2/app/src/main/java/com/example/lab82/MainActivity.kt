package com.example.lab82

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var switchDarkMode: Switch
    private lateinit var radioGroupTextSize: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("app_settings", MODE_PRIVATE)
        switchDarkMode = findViewById(R.id.switch_dark_mode)
        radioGroupTextSize = findViewById(R.id.radio_group_text_size)

        // Khôi phục cài đặt
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        val textSize = sharedPreferences.getInt("text_size", 1) // 0: Nhỏ, 1: Vừa, 2: Lớn

        switchDarkMode.isChecked = isDarkMode
        setDarkMode(isDarkMode)

        when (textSize) {
            0 -> radioGroupTextSize.check(R.id.radio_small)
            1 -> radioGroupTextSize.check(R.id.radio_medium)
            2 -> radioGroupTextSize.check(R.id.radio_large)
        }

        // Lắng nghe sự thay đổi của Switch
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            setDarkMode(isChecked)
            sharedPreferences.edit {
                putBoolean("dark_mode", isChecked)
            }
        }

        // Lắng nghe sự thay đổi của RadioGroup
        radioGroupTextSize.setOnCheckedChangeListener { _, checkedId ->
            val size = when (checkedId) {
                R.id.radio_small -> 0
                R.id.radio_large -> 2
                else -> 1 // Vừa
            }
            sharedPreferences.edit {
                putInt("text_size", size)
            }
            adjustTextSize(size)
        }

        adjustTextSize(textSize)
    }

    private fun setDarkMode(isEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isEnabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    private fun adjustTextSize(size: Int) {
        val textSize = when (size) {
            0 -> 12f // Nhỏ
            1 -> 16f // Vừa
            2 -> 20f // Lớn
            else -> 16f // Mặc định
        }
        findViewById<TextView>(R.id.text_size_label).textSize = textSize
    }
}
