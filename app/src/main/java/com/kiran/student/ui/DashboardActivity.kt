package com.kiran.student.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.kiran.student.R
import com.kiran.student.api.ServiceBuilder

class DashboardActivity : AppCompatActivity() {
    private lateinit var viewSTd:Button
    private lateinit var AddStd:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        viewSTd=findViewById(R.id.viewStd)
        AddStd=findViewById(R.id.addStd)

        Toast.makeText(this, ServiceBuilder.token, Toast.LENGTH_SHORT).show()
    }
}