package com.example.projectmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.HashMap

//should allow the head of the group to assign tasks as well
//should lead to an assign tasks page
// add a collection to the projects document

class AddingTaskScreen : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_task_screen)

        var auth = FirebaseAuth.getInstance()
        var db = FirebaseFirestore.getInstance()
        var projectTitle = findViewById<TextView>(R.id.projectName)
        projectTitle.text = intent.getStringExtra("Project Title")
        var documentID = intent.getStringExtra("Document ID")

        var addTask = findViewById<Button>(R.id.addTask)
        var tskName = findViewById<EditText>(R.id.taskName)
        var startDate = findViewById<EditText>(R.id.startDate)
        var dueDate = findViewById<EditText>(R.id.dueDate)



        addTask.setOnClickListener(){
            val task: MutableMap<String, Any?> = HashMap()
            task["Project Name"] = projectTitle.text.toString()
            task["Task Name"] = tskName.text.toString()
            task["Date Created"] = Date().toString()
            task["Start Date"] = startDate.text.toString()
            task["Due Date"] = dueDate.text.toString()
            if (documentID != null) {
                db.collection("Projects").document(documentID).collection("Tasks").add(task)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Task was added", Toast.LENGTH_SHORT).show()
                        finish()
                    }
            }
        }
    }
}