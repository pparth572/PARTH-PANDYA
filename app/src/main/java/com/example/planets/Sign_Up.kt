package com.example.planets

import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_sign_up.*

class Sign_Up : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        var helper = MyHelper(applicationContext)
        var db = helper.writableDatabase
        var rs = db.rawQuery("SELECT * FROM USERS",null)

        var ed_userid = findViewById<EditText>(R.id.login)
        var ed_password = findViewById<EditText>(R.id.password)
        var ed_cpassword = findViewById<EditText>(R.id.cpassword)
        var email_id = findViewById<EditText>(R.id.email)
        var btn_signup = findViewById<Button>(R.id.btn_SignUp)
        var btn_login = findViewById<Button>(R.id.btn_login)
        var flag = false
        val v = (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)




        //Email Validation
        email_id.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(email_id.text.toString()).matches())
                    flag = true
                else {
                    flag = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }


        })







        btn_signup.setOnClickListener {
            if(ed_userid.text.toString().isNotBlank() &&
                ed_password.text.toString().isNotBlank() &&
                    ed_cpassword.text.toString().isNotBlank() &&
                    email_id.text.toString().isNotBlank()) {

                        if(flag == false) {
                            Toast.makeText(
                                applicationContext,
                                "Invalid Email..!",
                                Toast.LENGTH_LONG
                            ).show()

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(
                                    VibrationEffect.createOneShot(500,
                                        VibrationEffect.DEFAULT_AMPLITUDE))
                            }
                        }
                        else {
                            if (ed_password.text.toString().equals(ed_cpassword.text.toString())) {


                                var cv = ContentValues()
                                cv.put("UNAME", ed_userid.text.toString())
                                cv.put("PASSWORD", ed_password.text.toString())
                                cv.put("EMAIL", email_id.text.toString())
                                db.insert("USERS", null, cv)
                                rs.requery()

                                var ad = AlertDialog.Builder(this)
                                ad.setTitle("Sign Up")
                                ad.setMessage("Sign Up Successfully..!");
                                ad.setPositiveButton(
                                    "Ok",
                                    DialogInterface.OnClickListener { dialogInterface, i ->
                                        ed_userid.setText("")
                                        ed_password.setText("")
                                        ed_cpassword.setText("")
                                        email_id.setText("")
                                        ed_userid.requestFocus()
                                        startActivity(Intent(applicationContext, Login::class.java))
                                        //finishActivity(1)
                                        finish()
                                    })
                                ad.show()

                                //startActivity(Intent(applicationContext,Login::class.java))
                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "Confirm Password Not Match..!",
                                    Toast.LENGTH_LONG
                                ).show()

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    v.vibrate(
                                        VibrationEffect.createOneShot(500,
                                            VibrationEffect.DEFAULT_AMPLITUDE))
                                }
                            }
                        }

            }else{
                Toast.makeText(applicationContext,"All Text Field Are Mandatory..!",Toast.LENGTH_LONG).show()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(
                        VibrationEffect.createOneShot(500,
                            VibrationEffect.DEFAULT_AMPLITUDE))
                }
            }
        }

//        btn_login.setOnClickListener {
//            startActivity(Intent(applicationContext,Login::class.java))
//            var helper = MyHelper(applicationContext)
//            var db = helper.readableDatabase
//            var r = db.rawQuery("SELECT * FROM USERS",null)
//            r.requery()
//            var rs = db.rawQuery("SELECT COUNT(_id) FROM USERS",null)
//            rs.moveToNext()
//            if(rs.getString(0).equals("1"))
//                 startActivity(Intent(applicationContext,Login_with_sign_up::class.java))
//            else {
//                startActivity(Intent(applicationContext,Login::class.java))
//                finish()
//            }
//            true
//        }
    }
}