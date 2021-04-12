package com.example.projectmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.projectmanagementdeanna.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var signUpButton = findViewById(R.id.button2) as Button
        signUpButton.setOnClickListener(){
            startActivity(Intent(this, SignUpActivity::class.java
            ))
        }
    }
}