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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity

class MyPwdmng : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_pwdmng)

        setTitle("Your Credentials..!")

        var helper = MyHelper(applicationContext)
        var db = helper.writableDatabase

        var MyPwdmngr = intent.getStringExtra("MyPwdmngr")

        var rs = db.rawQuery("SELECT * FROM PWDMNGR WHERE _id = ?", arrayOf(MyPwdmngr.toString()))

        var ed_wname = findViewById<EditText>(R.id.ed_Wname)
        var ed_uname = findViewById<EditText>(R.id.ed_uname)
        var ed_pwd = findViewById<EditText>(R.id.ed_pwd)
        var btn_delete = findViewById<Button>(R.id.delete)

        rs.moveToFirst()
        ed_wname.setText(rs.getString(1).toString())
        ed_uname.setText(rs.getString(2).toString())
        ed_pwd.setText(rs.getString(3).toString())

        var btn_update = findViewById<Button>(R.id.btn_savepwd)
        btn_update.setOnClickListener {

            if(ed_wname.text.toString().isNotBlank() && ed_uname.text.toString().isNotBlank() && ed_pwd.text.toString().isNotBlank()) {
                var cv = ContentValues()
                cv.put("WNAME", ed_wname.text.toString())
                cv.put("UNAME", ed_uname.text.toString())
                cv.put("PWD", ed_pwd.text.toString())
                db.update("PWDMNGR", cv, "_id = ?", arrayOf(rs.getString(0)))
                rs.requery()

                var ad = AlertDialog.Builder(this)
                ad.setTitle("Update")
                ad.setMessage("Update Successfully..!");
                ad.setPositiveButton(
                    "Ok",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        //startActivity(Intent(applicationContext, Category::class.java))
                        //finishActivity(1)
                        // finish()
                    })
                ad.show()

            }else{
                Toast.makeText(
                    applicationContext,
                    "All Fields Are Mandatory..!",
                    Toast.LENGTH_LONG
                ).show()
                val v = (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)
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

        btn_delete.setOnClickListener {
            db.delete("PWDMNGR","_id = ?", arrayOf(rs.getString(0)))
            rs.requery()
            intent = Intent(applicationContext,
                Category::class.java)
            intent.putExtra("Password Manager",0)
            startActivity(intent)
            Toast.makeText(applicationContext,"Credentials Deleted Successfully...!", Toast.LENGTH_LONG).show()
            finish()

        }

    }
    override fun onBackPressed() {
        var nm = "4"
        var fragment = MyPwdmngr()
        intent = Intent(applicationContext,
            Category::class.java)
        intent.putExtra("MyPwd",nm)
        startActivity(intent)
        finish()
    }
}