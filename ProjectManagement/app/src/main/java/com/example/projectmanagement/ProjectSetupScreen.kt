package com.example.projectmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class ProjectSetupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_setup)

        var addTask = findViewById<Button>(R.id.addTask)

        addTask.setOnClickListener(){
            startActivity(Intent(this, AddingTaskScreen::class.java))
        }
    }
}