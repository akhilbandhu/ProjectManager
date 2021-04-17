package com.example.projectmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddingPeopleScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_people_screen)
        var member = findViewById<EditText>(R.id.name)
        var addBtn = findViewById<Button>(R.id.addPeople)
        var membersList = ArrayList<String?>()
        var backBtn = findViewById<Button>(R.id.back)

        membersList.add("ME")
        addBtn.setOnClickListener {
        // adding owner first to members
                membersList.add(member.text.toString())
            if (member.text.isNotEmpty()){
                Toast.makeText(this, member.text.toString()+" added to project", Toast.LENGTH_SHORT).show()
            }
        }
        backBtn.setOnClickListener {
            var intent = Intent(this, ProjectSetupScreen::class.java)
            intent.putExtra("Members", membersList)
            startActivity(intent)
        }
    }
}