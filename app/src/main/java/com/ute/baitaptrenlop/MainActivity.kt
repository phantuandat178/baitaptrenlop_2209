package com.ute.baitaptrenlop

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.ute.baitaptrenlop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Khởi tạo kiểm tra dữ liệu trực tiếp khi gõ
        setupRealtimeValidation()

        // 2. Xử lý sự kiện khi nhấn nút ĐĂNG KÝ
        binding.btnRegister.setOnClickListener {
            if (validateAllFields()) {
                // Lấy dữ liệu từ các ô nhập
                val name = binding.etFullName.text.toString().trim()
                val email = binding.etEmail.text.toString().trim()
                val phone = binding.etPhone.text.toString().trim()
                val msv = binding.etMsv.text.toString().trim()
                val lop = binding.etLop.text.toString().trim()

                // Dùng Intent chuyển qua trang Chi tiết (DetailActivity)
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("EXTRA_NAME", name)
                intent.putExtra("EXTRA_EMAIL", email)
                intent.putExtra("EXTRA_PHONE", phone)
                intent.putExtra("EXTRA_MSV", msv)
                intent.putExtra("EXTRA_LOP", lop)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Vui lòng kiểm tra lại thông tin bị lỗi!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRealtimeValidation() {
        // Kiểm tra Họ và tên (Chỉ chữ cái và khoảng trắng)
        binding.etFullName.doOnTextChanged { text, _, _, _ ->
            val name = text.toString().trim()
            val nameRegex = Regex("^[a-zA-ZÀ-ỹ\\s]+$")
            binding.tilFullName.error = when {
                name.isEmpty() -> "Họ tên không được để trống"
                !nameRegex.matches(name) -> "Họ tên chỉ được chứa chữ cái"
                else -> null
            }
        }

        // Kiểm tra Email (Đúng định dạng @gmail.com...)
        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            val email = text.toString().trim()
            binding.tilEmail.error = when {
                email.isEmpty() -> "Email không được để trống"
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Email không đúng định dạng"
                else -> null
            }
        }

        // Kiểm tra Số điện thoại (10 chữ số, bắt đầu bằng 0)
        binding.etPhone.doOnTextChanged { text, _, _, _ ->
            val phone = text.toString().trim()
            binding.tilPhone.error = when {
                phone.isEmpty() -> "Số điện thoại không được để trống"
                !Regex("^0[0-9]{9}$").matches(phone) -> "Số điện thoại gồm 10 số (bắt đầu bằng 0)"
                else -> null
            }
        }

        // Kiểm tra Mã sinh viên (Chỉ số, tối thiểu 6 ký tự)
        binding.etMsv.doOnTextChanged { text, _, _, _ ->
            val msv = text.toString().trim()
            val msvRegex = Regex("^[0-9]+$")
            binding.tilMsv.error = when {
                msv.isEmpty() -> "Mã sinh viên không được để trống"
                msv.length < 6 -> "Mã sinh viên phải từ 6 chữ số"
                !msvRegex.matches(msv) -> "Mã sinh viên chỉ được chứa số"
                else -> null
            }
        }

        // Kiểm tra Lớp (Chữ, số, khoảng trắng, gạch ngang)
        binding.etLop.doOnTextChanged { text, _, _, _ ->
            val lop = text.toString().trim()
            val lopRegex = Regex("^[a-zA-Z0-9\\s\\-]+$")
            binding.tilLop.error = when {
                lop.isEmpty() -> "Lớp không được để trống"
                !lopRegex.matches(lop) -> "Tên lớp chứa ký tự không hợp lệ"
                else -> null
            }
        }
    }

    private fun validateAllFields(): Boolean {
        var isValid = true

        val name = binding.etFullName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val msv = binding.etMsv.text.toString().trim()
        val lop = binding.etLop.text.toString().trim()

        if (name.isEmpty() || !Regex("^[a-zA-ZÀ-ỹ\\s]+$").matches(name)) {
            binding.tilFullName.error = "Họ tên không hợp lệ"
            isValid = false
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Lỗi định dạng Email"
            isValid = false
        }

        if (!Regex("^0[0-9]{9}$").matches(phone)) {
            binding.tilPhone.error = "Số điện thoại không hợp lệ"
            isValid = false
        }

        if (msv.isEmpty() || msv.length < 6 || !Regex("^[0-9]+$").matches(msv)) {
            binding.tilMsv.error = "Mã sinh viên phải gồm số (tối thiểu 6)"
            isValid = false
        }

        if (lop.isEmpty() || !Regex("^[a-zA-Z0-9\\s\\-]+$").matches(lop)) {
            binding.tilLop.error = "Lớp không hợp lệ"
            isValid = false
        }

        return isValid
    }
}