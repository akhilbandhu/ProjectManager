package com.example.projectmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
//akhil@test.com
//Test1234
class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        var myProjects = findViewById<Button>(R.id.myProjectsBtn)
        var setUpBtn = findViewById<Button>(R.id.setupBtn)
        var notes = findViewById<Button>(R.id.notesUser)
        var calendar = findViewById<Button>(R.id.myCalendar)

        myProjects.setOnClickListener(){
            startActivity(Intent(this, MyProjectsScreen::class.java))
        }

        setUpBtn.setOnClickListener(){
            startActivity(Intent(this, ProjectSetupScreen::class.java))
        }
        notes.setOnClickListener(){
            startActivity(Intent(this, UserNotesActivity::class.java))
        }
        calendar.setOnClickListener(){
            startActivity(Intent(this, CalendarActivity::class.java))
        }

    }


}