package com.kiran.student.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.insert
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.kiran.student.R
import com.kiran.student.entity.Student
import com.kiran.student.repository.StudentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class AddStudent : AppCompatActivity() {
    private lateinit var etFullName: TextInputEditText
    private lateinit var etAge: TextInputEditText
    private lateinit var etAddress: TextInputEditText
    private lateinit var rdoMale: RadioButton
    private lateinit var rdoFemale: RadioButton
    private lateinit var rdoOthers: RadioButton
    private lateinit var btnSave: Button
    private lateinit var imgView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        etFullName = findViewById(R.id.etFullName)
        etAge = findViewById(R.id.etAge)
        etAddress = findViewById(R.id.etAddress)
        rdoMale = findViewById(R.id.rdoMale)
        rdoFemale = findViewById(R.id.rdoFemale)
        rdoOthers = findViewById(R.id.rdoOthers)
        btnSave = findViewById(R.id.btnSave)
        imgView = findViewById(R.id.imgview)

        btnSave.setOnClickListener {
            saveStudent()
        }
        imgView.setOnClickListener {

        }
    }

    private fun saveStudent() {
        val fullname = etFullName.text.toString()
        val age = etAge.text.toString()
        val address = etAddress.text.toString()
        var gender = " "
        when {
            rdoMale.isChecked -> {
                gender = "Male"
            }
            rdoOthers.isChecked -> {
                gender = "Others"
            }
        }
        val student =
            Student(
                fullname = fullname, age = age,
                gender = gender, address = address
            )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val studentRepository = StudentRepository()
                val response = studentRepository.insertStudent(student)

                if (response.success == true) {
                    withContext(Main) {
                        Toast.makeText(
                            this@AddStudent,
                            "Student Added",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@AddStudent,
                        ex.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}