package com.example.planets

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyHelper(context: Context) : SQLiteOpenHelper(context,"USERDB",null,1){
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE USERS(_id INTEGER PRIMARY KEY AUTOINCREMENT, UNAME TEXT, PASSWORD TEXT, EMAIL TEXT)")
        p0?.execSQL("INSERT INTO USERS(UNAME, PASSWORD, EMAIL) VALUES('ADMIN','ADMIN','admin@gmail.com')")

        p0?.execSQL("CREATE TABLE MYNOTES(_id INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, BODY TEXT)")
        p0?.execSQL("INSERT INTO MYNOTES(TITLE, BODY) VALUES('AWS','AMAZONE WEB SERVICES')")

        p0?.execSQL("CREATE TABLE PWDMNGR(_id INTEGER PRIMARY KEY AUTOINCREMENT, WNAME TEXT, UNAME TEXT, PWD TEXT)")
        p0?.execSQL("INSERT INTO PWDMNGR(WNAME, UNAME, PWD) VALUES('www.abc.com','john@gmail.com','j0hn@543')")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}