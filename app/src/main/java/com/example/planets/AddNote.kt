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
import androidx.fragment.app.Fragment

class AddNote : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        setTitle("Create Note")

        var note_title = findViewById<EditText>(R.id.noteTitle)
        var note_body = findViewById<EditText>(R.id.Note)
        var btn_save = findViewById<Button>(R.id.btn_saveNote)

        var helper = MyHelper(applicationContext)
        var db = helper.writableDatabase

        var rs = db.rawQuery("SELECT * FROM MYNOTES",null)
        val v = (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)

        btn_save.setOnClickListener {
             if(note_title.text.toString().isNotBlank() && note_body.text.toString().isNotBlank()){
                 var cv = ContentValues()
                 cv.put("TITLE",note_title.text.toString())
                 cv.put("BODY",note_body.text.toString())
                 db.insert("MYNOTES",null,cv)
                 rs.requery()

                 note_title.setText("")
                 note_body.setText("")

                 var ad = AlertDialog.Builder(this)
                 ad.setTitle("Add Note")
                 ad.setMessage("Note Saved Successfully..!")
                 ad.setPositiveButton("Ok" ,DialogInterface.OnClickListener { dialogInterface, i ->
                     note_title.setText("")
                     note_body.setText("")
                     note_title.requestFocus()
                     var adapter = SimpleCursorAdapter(applicationContext,android.R.layout.simple_expandable_list_item_2,rs,
                         arrayOf("TITLE","BODY"),
                         intArrayOf(android.R.id.text1,android.R.id.text2),0
                     )
                   intent = Intent(applicationContext,
                       Category::class.java)
                           intent.putExtra("MyNotes",0)
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
        intent.putExtra("MyNotes",0)
        startActivity(intent)
        finish()
    }

}
