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
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MyNote : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_note)
        setTitle("Your Note")

        var helper = MyHelper(applicationContext)
        var db = helper.writableDatabase

        var note = intent.getStringExtra("note")

        var rs = db.rawQuery("SELECT * FROM MYNOTES WHERE _id = ?", arrayOf(note.toString()))

        var title = findViewById<EditText>(R.id.noteTitle)
        var ed_note = findViewById<EditText>(R.id.Note)
        var btn_delete = findViewById<Button>(R.id.delete)

        rs.moveToFirst()
        title.setText(rs.getString(1).toString())
        ed_note.setText(rs.getString(2).toString())

        var btn_update = findViewById<Button>(R.id.btn_updateNote)
        btn_update.setOnClickListener {

            if(title.text.toString().isNotBlank() && ed_note.text.toString().isNotBlank()) {
                var cv = ContentValues()
                cv.put("TITLE", title.text.toString())
                cv.put("BODY", ed_note.text.toString())
                db.update("MYNOTES", cv, "_id = ?", arrayOf(rs.getString(0)))
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
            db.delete("MYNOTES","_id = ?", arrayOf(rs.getString(0)))
            rs.requery()
            intent = Intent(applicationContext,
                Category::class.java)
            intent.putExtra("MyNotes",0)
            startActivity(intent)
            Toast.makeText(applicationContext,"Note Deleted Successfully...!",Toast.LENGTH_LONG).show()
            finish()

        }

    }
    override fun onBackPressed() {
        intent = Intent(applicationContext,
            Category::class.java)
        intent.putExtra("MyNotes",0)
        startActivity(intent)
        finish()
    }
}