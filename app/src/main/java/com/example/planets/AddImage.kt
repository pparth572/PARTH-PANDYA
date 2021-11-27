package com.example.planets

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.MediaColumns.BITRATE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_add_image.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class AddImage : AppCompatActivity() {
    lateinit var filepath : Uri
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_image)

        var btn_choose_image = findViewById<Button>(R.id.btn_choose_image)
        var btn_saveimage = findViewById<Button>(R.id.btn_saveimage)
        var tv_filepath = findViewById<TextView>(R.id.tv_filepath)
       // var load_image = findViewById<ImageView>(R.id.load_image)

        btn_choose_image.setOnClickListener {
            startFileChooser()
        }
        btn_saveimage.setOnClickListener {

           // tv_filepath.text = "From : $filepath"
            tv_filepath.text = "Saved : $filepath"
            Toast.makeText(applicationContext,"Hiii...$filepath",Toast.LENGTH_LONG).show()
        }
    }

    private fun saveImageToInternalStorage(drawableId: Int): Uri? {
        val drawable = ContextCompat.getDrawable(applicationContext,drawableId)
        val bitmap = (drawable as BitmapDrawable).bitmap
        val wrapper = ContextWrapper(applicationContext)
        var file = wrapper.getDir("images",Context.MODE_PRIVATE)

        file = File(file, "${UUID.randomUUID()}.jpg")

        try{
            val stream : OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        }catch (e: IOException){
            e.printStackTrace()
        }
        return Uri.parse(file.absolutePath)

    }

    private fun startFileChooser() {
        var i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i,"Choose Picture"),112)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 112 && resultCode == Activity.RESULT_OK && data != null){
            filepath = data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filepath)
            load_image.setImageBitmap(bitmap)


        }
    }

}