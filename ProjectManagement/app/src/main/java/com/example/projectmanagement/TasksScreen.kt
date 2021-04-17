package com.example.projectmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//should have a list of all the tasks that you have to complete
//every task should lead to a page that would have all the information of your task
//like the due date
//what you are trying to accomplish

class TasksScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks_screen)
    }
}