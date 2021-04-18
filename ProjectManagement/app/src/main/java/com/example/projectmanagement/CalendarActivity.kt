package com.example.projectmanagement

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.CompactCalendarView.CompactCalendarViewListener
import com.github.sundeepk.compactcalendarview.domain.Event
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

//maybe this works!
class CalendarActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var compactCalendar: CompactCalendarView
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        auth = FirebaseAuth.getInstance()
        var showPreviousMonthBtn = findViewById<Button>(R.id.prev_button);
        var showNextMonthBtn = findViewById<Button>(R.id.next_button);
        var actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false)
        }
        if (actionBar != null) {
            actionBar.setTitle(null)
        }

        var currentUserID = auth.uid.toString()
        var db = FirebaseFirestore.getInstance()

        compactCalendar = findViewById<CompactCalendarView>(R.id.compactcalendar_view)
//        compactCalendar.setUseThreeLetterAbbreviation(true)
        var events = ArrayList<Event>()

//        var date = "04-22-2021"
//        var timeS = SimpleDateFormat("MM-dd-yyyy").parse(date)
//        var ev1 = Event(Color.GREEN, timeS.time, "Test")
//        compactCalendar.addEvent(ev1)

        db.collection("Projects").get().addOnCompleteListener(){
            if(it.isSuccessful){
                for(document in it.result!!){
                    if(document.data["created by"] == currentUserID){
                        //get the name of the project
                        var projectName = document.data["name"].toString()
                        //completion date of project
                        var completionDate = document.data["projected completion"].toString()
                        Log.d("Date", completionDate)
                        //convert to epoch time
                        val timeStamp = SimpleDateFormat("MM/dd/yyyy").parse(completionDate)
                        //create event which will get added to the events list for every single document having the same userID
                        var event = Event(Color.GREEN, timeStamp.time, "$projectName")
                        compactCalendar.addEvent(event)
                    }
                }
            }
        }
        //this will add the events to the calendar
//        for(event in events){
//            compactCalendar.addEvent(event)
//        }

        showNextMonthBtn.setOnClickListener(View.OnClickListener { compactCalendar.scrollRight()})
        showPreviousMonthBtn.setOnClickListener(View.OnClickListener { compactCalendar.scrollLeft()})

        compactCalendar.setListener(object : CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) {
                val events: List<Event> = compactCalendar.getEvents(dateClicked)
                var context = applicationContext
//                Log.d("TAG", "Day was clicked: $dateClicked with events ${events.get()}")
                if(events.isNotEmpty()){
                    Toast.makeText(context, "Day was clicked: $dateClicked with events $events", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(context, "Day was clicked: $dateClicked with no events", Toast.LENGTH_LONG).show()
                }
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {
                var context = applicationContext
                Log.d("TAG", "Month was scrolled to: $firstDayOfNewMonth")
                Toast.makeText(context, "Month was scrolled to: $firstDayOfNewMonth", Toast.LENGTH_SHORT).show()
            }
        })

    }

    //this will convert the date into timestamp from the database
    fun convertDateToEpoch(date: String): Long{
        val finalDate = SimpleDateFormat("MM/dd/yyyy").parse(date)
        return finalDate.time
    }
}