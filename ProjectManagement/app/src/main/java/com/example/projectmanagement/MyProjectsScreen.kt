package com.example.projectmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// List of all projects connected to your account - should be clickable => show tasks from that project
//there should also be a add project button which allows you to add a project to your database
// send project name to ActualProjectScreen class after click

class MyProjectsScreen : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_projects_screen)

        var auth = FirebaseAuth.getInstance()
        var db = FirebaseFirestore.getInstance()

        var projectsList = findViewById<ListView>(R.id.projectList)
        var listOfElements = ArrayList<String?>()
        var listOfDocuments = ArrayList<String?>()
        var myID = auth.uid.toString()
        var membersList = intent.getStringArrayListExtra("Members")

        db.collection("Projects").get().addOnCompleteListener { task ->
            if (task.isSuccessful){
                for (document in task.result!!){
                    if (document.data["created by"].toString() == myID) {
                        var projectName = document.data["name"].toString()
                        listOfElements.add(projectName)
                        listOfDocuments.add(document.id.toString())
                        // adapter
                        var listAdapter: ArrayAdapter<String?> = ArrayAdapter(
                            this,
                            android.R.layout.simple_expandable_list_item_1,
                            listOfElements
                        )
                        projectsList.adapter = listAdapter
                        projectsList.setOnItemClickListener { parent, view, position, id ->
                            Toast.makeText(
                                this,
                                listOfElements[position] + " Project Tasks",
                                Toast.LENGTH_LONG
                            ).show()
                            var intent = Intent(this, ActualProjectScreen::class.java)
                            intent.putExtra("Project Name", listOfElements[position])
                            intent.putExtra("Created By", myID)
                            intent.putExtra("Document ID", listOfDocuments[position])
                            intent.putExtra("Members", membersList)
                            startActivity(intent)
                        }
                    }
                }
            }

        }

    }
}