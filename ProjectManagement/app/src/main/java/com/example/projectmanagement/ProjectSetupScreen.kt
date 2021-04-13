package com.example.projectmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.util.*
import kotlin.collections.HashMap

class ProjectSetupActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_setup_screen)

        var projectNameET = findViewById<EditText>(R.id.projectName)
        var projectedCompletionDateET = findViewById<EditText>(R.id.projectedDate)
        var addProjectB = findViewById<Button>(R.id.addProject)

        var auth = FirebaseAuth.getInstance()
        var db = FirebaseFirestore.getInstance()




        addProjectB.setOnClickListener(){
            val project: MutableMap<String, Any?> = HashMap()
            project["name"] = projectNameET.text.toString()
            project["projected completion"] = projectedCompletionDateET.text.toString()
            project["date created"] = Date().toString()
            project["created by"] = auth.uid.toString()
            db.collection("Projects").add(project)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Project was added", Toast.LENGTH_SHORT).show()
                        finish()
                    }
        }
    }
}