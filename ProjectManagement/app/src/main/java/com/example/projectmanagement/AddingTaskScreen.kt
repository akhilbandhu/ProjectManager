package com.example.projectmanagement

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList
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
        var documentID = intent.getStringExtra("Project Document")


        var addTask = findViewById<Button>(R.id.addTask)
        var tskName = findViewById<EditText>(R.id.taskName)
        var startDate = findViewById<EditText>(R.id.startDate)
        var dueDate = findViewById<EditText>(R.id.dueDate)
        var taskNotes = findViewById<EditText>(R.id.taskNotes)

        // adding members from firestore project to local array variable
        var membersList = ArrayList<String?>()
        membersList.add("TrialRun")
        documentID?.let {
            db.collection("Projects").document(documentID).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var projectDocument = task.result
                    val map: Map<String, Any> =
                        projectDocument?.getData() as Map<String, Any>
                    for ((key, value) in map) {
                        if (key == "members") {
                            Log.d("TAG", value.toString())
                            // LOOK AT THIS PART - I need to add individual members to membersList not the whole list
                            membersList.add(value.toString()) // gives me "[word, word]"
                        }
                    }
                }
            }
        }
        // member drop down menu - assign to task
        var assignMenu = findViewById<Spinner>(R.id.memberMenu)
        // putting project members in drop down menu
        var listAdapter: ArrayAdapter<String?> = ArrayAdapter(
            this,
            android.R.layout.simple_expandable_list_item_1,
            membersList
        )
        listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        assignMenu.adapter = listAdapter


        addTask.setOnClickListener(){
            val task: MutableMap<String, Any?> = HashMap()
            task["Project Name"] = projectTitle.text.toString()
            task["Task Name"] = tskName.text.toString()
            task["Date Created"] = Date().toString()
            task["Start Date"] = startDate.text.toString()
            task["Due Date"] = dueDate.text.toString()
            task["Task Notes"] = taskNotes.text.toString()
            if (documentID != null) {
                db.collection("Projects").document(documentID).collection("Tasks").add(task)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Task was added", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MyProjectsScreen::class.java))
                    }
            }
        }
    }
}