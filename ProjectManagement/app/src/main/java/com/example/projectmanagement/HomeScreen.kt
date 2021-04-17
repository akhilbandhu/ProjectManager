package com.example.projectmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        var myProjects = findViewById<Button>(R.id.myProjectsBtn)
        var setUpBtn = findViewById<Button>(R.id.setupBtn)
        var userNotes = findViewById<Button>(R.id.notesBtn)
        var calendarBtn = findViewById<Button>(R.id.myCalendarBtn)

        myProjects.setOnClickListener(){
            startActivity(Intent(this, MyProjectsScreen::class.java))
        }

        setUpBtn.setOnClickListener(){
            startActivity(Intent(this, ProjectSetupScreen::class.java))
        }

        userNotes.setOnClickListener(){
            startActivity(Intent(this, UserNotesActivity::class.java))
        }
        
        calendarBtn.setOnClickListener(){
            startActivity(Intent(this, CalendarActivity::class.java))
        }

    }


}























