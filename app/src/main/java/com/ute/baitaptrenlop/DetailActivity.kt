package com.ute.baitaptrenlop

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ute.baitaptrenlop.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("EXTRA_NAME") ?: "N/A"
        val email = intent.getStringExtra("EXTRA_EMAIL") ?: "N/A"
        val phone = intent.getStringExtra("EXTRA_PHONE") ?: "N/A"
        val msv = intent.getStringExtra("EXTRA_MSV") ?: "N/A"
        val lop = intent.getStringExtra("EXTRA_LOP") ?: "N/A"

        val displayInfo = """
            Họ và tên: $name
            Email: $email
            Số điện thoại: $phone
            Mã sinh viên: $msv
            Lớp: $lop
        """.trimIndent()

        binding.tvDetailInfo.text = displayInfo

        binding.btnGoBack.setOnClickListener {
            val backIntent = Intent(this, MainActivity::class.java)
            backIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(backIntent)
        }
    }
}