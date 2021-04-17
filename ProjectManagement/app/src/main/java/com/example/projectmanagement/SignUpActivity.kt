package com.example.projectmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        var emailTF = findViewById(R.id.signUpUserName) as EditText
        var pwdTF = findViewById(R.id.signUpPwd) as EditText
        var confirmPwd = findViewById(R.id.confirmPwd) as EditText
        var signUpButton = findViewById(R.id.signupBtn) as Button

        signUpButton.setOnClickListener(){
            //to get the correct format for the email address
            var emailMatch = android.util.Patterns.EMAIL_ADDRESS.matcher(emailTF.text).matches()
            //to get the passwords to match in the password and confirm password boxes
            var pwdMatch = pwdTF.text.toString().equals(confirmPwd.text.toString())
            var hasUpperCase = checkUpperCase(pwdTF.text.toString())
            var hasLowerCase = checkLowerCase(pwdTF.text.toString())
            var hasDigit = checkDigit(pwdTF.text.toString())
            //creating user in firebase
            if(emailMatch && pwdMatch && hasUpperCase && hasLowerCase && hasDigit){
                auth.createUserWithEmailAndPassword(emailTF.text.toString(), pwdTF.text.toString())
                    .addOnCompleteListener(){
                        if(it.isSuccessful){
                            Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show()
                            startActivity(
                                Intent(this, MainActivity::class.java
                                )
                            )
                        }
                    }
            }
            else if(!emailMatch){
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
            }
            else if(!pwdMatch){
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
            else if(!hasUpperCase){
                Toast.makeText(this, "Password does not have an upper case letter", Toast.LENGTH_SHORT).show()
            }
            else if(!hasLowerCase){
                Toast.makeText(this, "Password does not have a lower case letter", Toast.LENGTH_SHORT).show()
            }
            else if(!hasDigit){
                Toast.makeText(this, "Password does not have a digit", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun checkUpperCase(password: String):Boolean{
        for(letter in password){
            if(letter.isUpperCase()){
                return true
            }
        }
        return false
    }
    fun checkLowerCase(password: String):Boolean{
        for(letter in password){
            if(letter.isLowerCase()){
                return true
            }
        }
        return false
    }
    fun checkDigit(password: String):Boolean{
        for(letter in password){
            if(letter.isDigit()){
                return true
            }
        }
        return false
    }
}