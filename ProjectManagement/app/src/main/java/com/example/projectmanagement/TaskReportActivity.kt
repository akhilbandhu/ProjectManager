package com.example.projectmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TaskReportActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_report)

        //there will be a quite a few things that come in here

        var showReportBtn = findViewById<Button>(R.id.showReport)
        var submitReport = findViewById<Button>(R.id.submitBtn)
        var report = findViewById<EditText>(R.id.report)
        var db = FirebaseFirestore.getInstance()
        var projectDocumentID = intent.getStringExtra("Project Document")
        auth = FirebaseAuth.getInstance()
        //we will need the current user id to store and access the notes
        //this can be done with the auth object
        var currentUserID = auth.uid.toString()
        showReportBtn.setOnClickListener() {
            val notes: MutableMap<String, Any?> = HashMap()
            //now we have to get the notes corresponding to that user id
            //this should add all the user notes to the field
            db.collection("Projects").document(projectDocumentID.toString()).collection("Tasks").document(projectDocumentID.toString()).collection("Task Report").get().addOnCompleteListener() {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        if (document.data["UserID"].toString() == currentUserID) {
                            report.setText(document.data["Notes"].toString())
                        }
                    }
                }
            }

        }

        submitReport.setOnClickListener() {
            val reportNotes: MutableMap<String, Any?> = HashMap()
            reportNotes["Notes"] = report.text.toString()
            reportNotes["UserID"] = auth.uid.toString()
            db.collection("Projects").document(projectDocumentID.toString()).collection("Tasks").document(projectDocumentID.toString()).collection("Task Report").add(reportNotes).addOnCompleteListener() {
                Toast.makeText(this, "Task Report Added", Toast.LENGTH_SHORT).show()
            }
        }
    }
}