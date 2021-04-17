package com.example.projectmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

//should have the ability to make a team
//send project name and/or document id to this class from list in MyProjectsScreen class
//add tasks to project
//list of tasks related to project

class ActualProjectScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actual_project_screen)

        var auth = FirebaseAuth.getInstance()
        var db = FirebaseFirestore.getInstance()
        var membersList = intent.getStringArrayListExtra("Members")

        var projectTitle = findViewById<TextView>(R.id.projectTitle)
        var text = intent.getStringExtra("Document ID")
        projectTitle.text = text// project name clicked from list in MyProjectsScreen class
        var creatorID = intent.getStringExtra("Created By")

        var addTsk = findViewById<Button>(R.id.addingTask)
        addTsk.setOnClickListener {
            var newIntent = Intent(this, AddingTaskScreen::class.java)
            newIntent.putExtra("Project Title", projectTitle.text.toString())
            startActivity(newIntent)
        }

        var taskList = findViewById<ListView>(R.id.taskList)
        var listOfElements = ArrayList<String?>()
        var listOfDocuments = ArrayList<String?>()


        db.collection("Projects").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    //if (document.data["created by"].toString() == myID) {
                    var taskName = document.data["Task Name"].toString()
                    listOfElements.add(taskName)
                    listOfDocuments.add(document.id.toString())
                    // adapter
                    var listAdapter: ArrayAdapter<String?> = ArrayAdapter(
                            this,
                            android.R.layout.simple_expandable_list_item_1,
                            listOfElements
                    )
                    taskList.adapter = listAdapter
                    taskList.setOnItemClickListener { parent, view, position, id ->
                        Toast.makeText(
                                this,
                                listOfElements[position],
                                Toast.LENGTH_LONG
                        ).show()
                        var intent = Intent(this, TasksScreen::class.java)
                        intent.putExtra("Task Name", listOfElements[position])
                        intent.putExtra("Project Title", text)
                        intent.putExtra("Document ID", listOfDocuments[position])
                        intent.putExtra("Members", membersList)
                        startActivity(intent)
                    }
                }
            }
        }


    }
}