package com.example.projectmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserNotesActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_notes)
        var showNotesBtn = findViewById<Button>(R.id.showNotesBtn)
        var addNotesBtn = findViewById<Button>(R.id.addNotesBtn)
        var userNotes = findViewById<EditText>(R.id.notes)
        var db = FirebaseFirestore.getInstance()
        //we will need the current user id to store and access the notes
        //this can be done with the auth object
        var currentUserID = auth.uid.toString()
        showNotesBtn.setOnClickListener(){
            val notes: MutableMap<String, Any?> = HashMap()
            //now we have to get the notes corresponding to that user id
            //this should add all the user notes to the field
            db.collection("User Notes").get().addOnCompleteListener(){
                if(it.isSuccessful){
                    for(document in it.result!!){
                        if(document.data["UserID"].toString() == currentUserID){
                            userNotes.setText(document.data["Notes"].toString())
                        }
                    }
                }
            }

        }

        addNotesBtn.setOnClickListener(){
            val notes: MutableMap<String, Any?> = HashMap()
            notes["Notes"] = userNotes.text.toString()
            notes["UserID"] = auth.uid.toString()
            db.collection("User Notes").add(notes).addOnCompleteListener(){
                Toast.makeText(this, "User Notes Added", Toast.LENGTH_SHORT).show()
            }

        }

    }


}