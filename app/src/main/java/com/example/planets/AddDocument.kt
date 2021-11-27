package com.example.planets

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import com.github.barteksc.pdfviewer.PDFView
import kotlinx.android.synthetic.main.activity_add_document.*

class AddDocument : AppCompatActivity() {

    lateinit var filepath : Uri
    var pdfviewer = findViewById<PDFView>(R.id.pdfviewer)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_document)

        var btn_choose_doc = findViewById<Button>(R.id.btn_choose_doc)
        var btn_savedoc = findViewById<Button>(R.id.btn_savedoc)

        btn_choose_doc.setOnClickListener {
            startFileChooser()
        }
    }

    private fun startFileChooser() {
        var i = Intent()
        i.setType("pdf/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i,"Choose Document"),112)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 112 && resultCode == Activity.RESULT_OK && data != null){
            filepath = data.data!!
           pdfviewer.fromAsset(filepath.toString()).load()
        }
    }
}