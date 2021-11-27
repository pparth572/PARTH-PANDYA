package com.example.planets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContentView(R.layout.activity_main)

        var my_recycler = findViewById<RecyclerView>(R.id.my_recycler)

        my_recycler.adapter=Adapter(setData.SetPlanets())
        my_recycler.layoutManager=LinearLayoutManager(this)

    }
    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setTitle("Please confirm.")
            setMessage("Are you want to exit the app?")

            setPositiveButton("Yes") { _, _ ->
                // if user press yes, then finish the current activity
                super.onBackPressed()
            }

            setNegativeButton("No"){_, _ ->
                // if user press no, then return the activity
                Toast.makeText(this@MainActivity, "Thank you",
                    Toast.LENGTH_LONG).show()
            }

            setCancelable(true)
        }.create().show()
    }
}