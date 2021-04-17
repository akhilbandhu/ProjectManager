package com.example.projectmanagement

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat

//maybe this works!
class CalendarActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var compactCalendar: CompactCalendarView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        auth = FirebaseAuth.getInstance()

        var actionBar: ActionBar? = getSupportActionBar()
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false)
        }
        if (actionBar != null) {
            actionBar.setTitle(null)
        }
        var currentUserID = auth.uid.toString()
        var db = FirebaseFirestore.getInstance()

        compactCalendar = findViewById<CompactCalendarView>(R.id.compactcalendar_view)
        compactCalendar.setUseThreeLetterAbbreviation(true)
        var events = ArrayList<Event>()

        db.collection("Projects").get().addOnCompleteListener(){
            if(it.isSuccessful){
                for(document in it.result!!){
                    if(document.data["created by"] == currentUserID){
                        //get the name of the project
                        var projectName = document.data["name"].toString()
                        //completion date of project
                        var completionDate = document.data["projected completion"].toString()
                        //convert to epoch time
                        var timeStamp = convertDateToEpoch(completionDate)
                        //create event which will get added to the events list for every single document having the same userID
                        var event: Event = Event(Color.GREEN, timeStamp, "$projectName")
                        events.add(event)
                    }
                }
            }
        }
        //this will add the events to the calendar
        for(event in events){
            compactCalendar.addEvent(event)
        }
//        CompactCalendarView.setOnClickListener(object : CompactCalendarViewListener {
//            override fun onDayClick(dateClicked: Date) {
//                val events: List<Event> = CompactCalendarView.getEvents(dateClicked)
//                Log.d(this.toString(), "Day was clicked: $dateClicked with events $events")
//            }
//
//            override fun onMonthScroll(firstDayOfNewMonth: Date) {
//                Log.d(this.toString(), "Month was scrolled to: $firstDayOfNewMonth")
//            }
//        })
    }

    //this will convert the date into timestamp from the database
    fun convertDateToEpoch(date: String): Long{
        val date = SimpleDateFormat("MM-dd-yyyy").parse(date)
        return date.time
    }
}