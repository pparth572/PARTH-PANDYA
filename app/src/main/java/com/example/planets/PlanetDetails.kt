package com.example.planets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_planet_details.*
import kotlinx.android.synthetic.main.view.*
import kotlinx.android.synthetic.main.view.distance
import kotlinx.android.synthetic.main.view.gravity

class PlanetDetails : AppCompatActivity() {
    private lateinit var obj: PlanetData
    private var planetImg: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planet_details)

        obj = intent.getParcelableExtra("data")!!
        planetImg = intent.getIntExtra("planetImage", -1)
        setData(obj,planetImg!!)
    }

    private fun setData(obj: PlanetData, planetImg: Int) {
        var title_info = findViewById<TextView>(R.id.title)
        var distance = findViewById<TextView>(R.id.distance)
        var gravity = findViewById<TextView>(R.id.gravity)
        var overview_info = findViewById<TextView>(R.id.overview_info)
        var galaxy_info = findViewById<TextView>(R.id.galaxy_info)
        var planet_img = findViewById<ImageView>(R.id.planet_img_info)

        var helper = MyHelper(applicationContext)
        var db = helper.readableDatabase
        var rs = db.rawQuery("SELECT COUNT(_id) FROM USERS",null)
        title_info.text = obj.title
        distance.text = obj.distance + "m km"
        gravity.text = obj.gravity + " m/ss"
        overview_info.text = obj.overview
        galaxy_info.text = obj.galaxy
        planet_img.setImageResource(planetImg)
       // Toast.makeText(applicationContext,"$obj.title Hiii",Toast.LENGTH_LONG).show()
        rs.moveToNext()


        if( obj.id == 10) {
            planet_img.setOnLongClickListener{
//                var r = db.rawQuery("SELECT * FROM USERS",null)
//                r.requery()
                rs.requery()
                rs.moveToNext()
                Toast.makeText(applicationContext,"Hidden Vault..!",Toast.LENGTH_LONG).show()
                if(rs.getString(0).equals("1"))
                   startActivity(Intent(applicationContext,Login_with_sign_up::class.java))
                   // startActivityForResult(Intent(applicationContext,Login_with_sign_up::class.java),1)
                else
                    startActivity(Intent(applicationContext,Login::class.java))
                true
            }
        }


    }
}