package com.example.planets

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import java.util.jar.Manifest

class Category : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)


        setTitle("Hidden Vault..!")
        var i = intent.getStringExtra("MyPwd")

        if(i.toString().isNullOrEmpty() || i.toString().equals("null"))
            supportFragmentManager.beginTransaction().replace(R.id.frame, MyNotes()).commit()
        else
            sf(i?.toInt())







        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var drawer = findViewById<DrawerLayout>(R.id.drawer)
        var lv = findViewById<ListView>(R.id.listview1)

        toggle = ActionBarDrawerToggle(this,drawer,R.string.open, R.string.close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        var options = arrayOf("Notes","Pictures","Videos","Documents","Password\nManager" )
        var adapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,options)
        lv.adapter = adapter

        lv.setOnItemClickListener { adapterView, view, i, l ->
            drawer.closeDrawers()
            when(i) {
                0 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame, MyNotes()).commit()
                }
                1 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame, MyPictures()).commit()
                }
                3 -> {
                    if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),111)
                    else{
                        supportFragmentManager.beginTransaction().replace(R.id.frame, MyDocuments()).commit()
                    }
                }

                4 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame, MyPwdmngr()).commit()
                }
            }
        }






    }

private fun sf(i: Int?) {
        when(i?.toInt()) {
            0 -> {
                supportFragmentManager.beginTransaction().replace(R.id.frame, MyNotes()).commit()
            }
            1 -> {
                supportFragmentManager.beginTransaction().replace(R.id.frame, MyPictures()).commit()
            }
            3 -> {
                supportFragmentManager.beginTransaction().replace(R.id.frame, MyDocuments()).commit()
            }
            4 -> {
                supportFragmentManager.beginTransaction().replace(R.id.frame, MyPwdmngr()).commit()
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            supportFragmentManager.beginTransaction().replace(R.id.frame, MyDocuments()).commit()
        }
        else{
            Toast.makeText(applicationContext,"Permission Not Granted..!",Toast.LENGTH_LONG).show()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(1,101,1,"Admin")

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item))
            return true


        when(item.itemId){
            101 -> startActivity(Intent(applicationContext,Admin::class.java))
        }





        return super.onOptionsItemSelected(item)
    }


}