package com.kiran.student.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.kiran.student.R
import com.kiran.student.entity.User
import com.kiran.student.repository.UserRespository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {
    private lateinit var etFname: EditText
    private lateinit var etLname: EditText
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnAddStudent: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etFname = findViewById(R.id.etFname)
        etLname = findViewById(R.id.etLname)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnAddStudent = findViewById(R.id.btnAddStudent)
        btnAddStudent.setOnClickListener {
            val fname = etFname.text.toString()
            val lname = etLname.text.toString()
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (password != confirmPassword) {
                etPassword.error = "Password does not match"
                etPassword.requestFocus()
                return@setOnClickListener
            } else {
                val user =
                    User(fname = fname, lname = lname, username = username, password = password)

                // Api code goes here
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val repository = UserRespository()
                        val response = repository.registerUser(user)
                        //success registeration bhaye toast ma dis
                        if (response.success == true) {
                            withContext(Main) {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Registeration Sucessfull",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    } catch (ex: Exception) {
                        withContext(Main) {
                            Toast.makeText(this@RegisterActivity, ex.toString(), Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }
}