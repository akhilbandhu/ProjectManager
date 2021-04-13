package com.example.projectmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

//this should open the home screen page which has two buttons

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var signUpButton = findViewById(R.id.signUpButtonLoginPage) as Button
        var loginButton = findViewById(R.id.logInButton) as Button
        var emailID = findViewById(R.id.emailID) as EditText
        var password = findViewById(R.id.loginpwd) as EditText
        signUpButton.setOnClickListener(){
            //this takes you to the other page
            startActivity(Intent(this, SignUpActivity::class.java
            ))
        }
        loginButton.setOnClickListener(){
            //to be able to sign in
            if(emailID.text.toString() != "" && password.text.toString() != "") {
                auth.signInWithEmailAndPassword(emailID.text.toString(), password.text.toString())
                        .addOnCompleteListener() {
                            //taking us to the dashboard page
                            if (it.isSuccessful) {
                                startActivity(Intent(this, HomeScreen::class.java))
                                emailID.setText("")
                                password.setText("")
                            } else {
                                Toast.makeText(this, "Invalid Email/Password", Toast.LENGTH_SHORT).show()
                            }

                        }
            }
            else{
                Toast.makeText(this, "Please enter information", Toast.LENGTH_SHORT).show()
            }
        }
    }
}