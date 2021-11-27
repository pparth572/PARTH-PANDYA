package com.example.planets

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class Login_with_sign_up : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_with_sign_up)

        var helper = MyHelper(applicationContext)
        var db = helper.readableDatabase

        var user_id = findViewById<EditText>(R.id.login)
        var password = findViewById<EditText>(R.id.password)
        var btn_login = findViewById<Button>(R.id.btn_login)
        var btn_signup = findViewById<Button>(R.id.btn_SignUp)

        btn_login.setOnClickListener {
            var args = listOf<String>(user_id.text.toString(),password.text.toString()).toTypedArray()
            var rs = db.rawQuery("SELECT * FROM USERS WHERE UNAME = ? AND PASSWORD = ?",args)
            if(rs.moveToNext()){
                startActivity(Intent(applicationContext,Category::class.java))
                Toast.makeText(applicationContext,"Welcome..! ${user_id.text.toString()}", Toast.LENGTH_LONG).show()
                login.setText("")
                password.setText("")
                user_id.requestFocus()
            }
            else{
                Toast.makeText(applicationContext,"Invalid Credentials...!", Toast.LENGTH_LONG).show()
                val v = (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(500,
                    VibrationEffect.DEFAULT_AMPLITUDE))
                }
            }
        }

        btn_signup.setOnClickListener {
            startActivity(Intent(applicationContext,Sign_Up::class.java))
            finish()
        }
    }


}