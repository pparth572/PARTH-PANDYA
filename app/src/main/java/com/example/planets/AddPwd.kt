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
import android.widget.Button
import android.widget.EditText
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class AddPwd : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pwd)


        setTitle("Add New Credential..!")

        var ed_wname = findViewById<EditText>(R.id.ed_Wname)
        var ed_uname = findViewById<EditText>(R.id.ed_uname)
        var ed_pwd = findViewById<EditText>(R.id.ed_pwd)
        var btn_save = findViewById<Button>(R.id.btn_savepwd)

        var helper = MyHelper(applicationContext)
        var db = helper.writableDatabase

        var rs = db.rawQuery("SELECT * FROM PWDMNGR",null)
        val v = (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)

        btn_save.setOnClickListener {
            if(ed_wname.text.toString().isNotBlank() && ed_uname.text.toString().isNotBlank() && ed_pwd.text.toString().isNotBlank()){
                var cv = ContentValues()
                cv.put("WNAME",ed_wname.text.toString())
                cv.put("UNAME",ed_uname.text.toString())
                cv.put("PWD",ed_pwd.text.toString())
                db.insert("PWDMNGR",null,cv)
                rs.requery()

                ed_wname.setText("")
                ed_uname.setText("")
                ed_pwd.setText("")

                var ad = AlertDialog.Builder(this)
                ad.setTitle("Add")
                ad.setMessage("Record Saved Successfully..!")
                ad.setPositiveButton("Ok" , DialogInterface.OnClickListener { dialogInterface, i ->
                    ed_wname.setText("")
                    ed_uname.setText("")
                    ed_pwd.setText("")
                    ed_wname.requestFocus()
//                    var adapter = SimpleCursorAdapter(applicationContext,android.R.layout.simple_expandable_list_item_2,rs,
//                        arrayOf("WNAME","UNAME","PWD"),
//                        intArrayOf(android.R.id.text1,android.R.id.text2),0
//                    )
                    intent = Intent(applicationContext,
                        Category::class.java)
                    intent.putExtra("MyPwd","4")
                    startActivity(intent)
                    finish()


//                     fragmentManager?.beginTransaction()?.replace(R.id.frame, MyPictures())?.commit()
                })
                ad.show()
            }else {
                Toast.makeText(
                    applicationContext,
                    "All Text Field Are Mandatory..!",
                    Toast.LENGTH_LONG
                ).show()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(
                        VibrationEffect.createOneShot(
                            500,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                }
            }


        }


    }
    override fun onBackPressed() {
        intent = Intent(applicationContext,
            Category::class.java)
        intent.putExtra("MyPwd","4")
        startActivity(intent)
        finish()
    }
}