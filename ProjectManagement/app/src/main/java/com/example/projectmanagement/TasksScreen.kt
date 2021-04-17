package com.example.projectmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

//should have a list of all the tasks that you have to complete
//every task should lead to a page that would have all the information of your task
//like the due date
//what you are trying to accomplish

class TasksScreen : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks_screen)
        auth = FirebaseAuth.getInstance()
        var db = FirebaseFirestore.getInstance()
        var projectName = findViewById<TextView>(R.id.projectTitleTask)
        var taskName = findViewById<TextView>(R.id.taskTitle)
        var dueDate = findViewById<TextView>(R.id.dueDateTask)
        var addNotes = findViewById<TextView>(R.id.taskNotes)


    }
}