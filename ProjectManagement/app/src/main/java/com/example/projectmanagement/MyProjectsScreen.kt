package com.example.projectmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

//should have a list of all the tasks that you have to complete
//every task should lead to a page that would have all the information of your task
//like the due date
//what you are trying to accomplish
//there should also be a add project button which allows you to add a project to your database

class MyProjectsScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_projects_screen)

    }

}