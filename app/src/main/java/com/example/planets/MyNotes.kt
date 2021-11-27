package com.example.planets

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.MultiSelectListPreference
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.fragment_my_notes.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyNotes.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyNotes : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val rotateOpen: Animation by lazy{ AnimationUtils.loadAnimation(context,R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy{ AnimationUtils.loadAnimation(context,R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy{ AnimationUtils.loadAnimation(context,R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy{ AnimationUtils.loadAnimation(context,R.anim.to_bottom_anim)}
    private var clicked = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_my_notes, container, false)

        var add = v.findViewById<FloatingActionButton>(R.id.add)
        var edit = v.findViewById<FloatingActionButton>(R.id.edit)
        var delete = v.findViewById<FloatingActionButton>(R.id.delete)
        var addNote = v.findViewById<FloatingActionButton>(R.id.addNote)
        var My_Notes_lv = v.findViewById<ListView>(R.id.my_notes_lv)
        var search_note = v.findViewById<SearchView>(R.id.search_note)










        var helper = context?.let { MyHelper(it) }
        var db = helper?.writableDatabase

        var rs = db!!.rawQuery("SELECT * FROM MYNOTES ORDER BY _ID DESC",null)


        search_note.queryHint = "Search Among ${rs.count} Records"

        //var arr = mutableListOf<String>("")
        var adapter = SimpleCursorAdapter(context,android.R.layout.simple_expandable_list_item_2,rs,
            arrayOf("TITLE","BODY"),
            intArrayOf(android.R.id.text1,android.R.id.text2),0
        )


        search_note.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                rs = db.rawQuery("SELECT * FROM MYNOTES WHERE TITLE LIKE '%${p0}%' OR BODY LIKE '%${p0}%'",null)
                adapter.changeCursor(rs)
                return false
            }





        })

        My_Notes_lv.adapter = adapter

        My_Notes_lv.setOnItemClickListener { adapterView, view, i, l ->
            var intent = Intent(requireContext(),MyNote::class.java)
            intent.putExtra("note",rs.getString(0))
            startActivity(intent)

            activity?.finish()
        }


        add.setOnClickListener{
            onAddButtonClicked()
        }
        edit.setOnClickListener{
//            rs.moveToNext()
//            rs.moveToNext()
//
//            Toast.makeText(context,"${rs.getString(1)}",Toast.LENGTH_LONG).show()

        }
        addNote.setOnClickListener{
            startActivity(Intent(context,AddNote::class.java))
            activity?.finish()

            //Toast.makeText(context,"Add Note",Toast.LENGTH_LONG).show()
            //fragmentManager?.beginTransaction()?.replace(R.id.frame, MyPictures())?.commit()
        }
        delete.setOnClickListener{

        }





        return v
    }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked : Boolean) {
        if(!clicked) {
            edit.startAnimation(fromBottom)
            addNote.startAnimation(fromBottom)
            delete.startAnimation(fromBottom)
            add.startAnimation(rotateOpen)
        }else{
            edit.startAnimation(toBottom)
            addNote.startAnimation(toBottom)
            delete.startAnimation(toBottom)
            add.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked) {
            edit.visibility = View.VISIBLE
            addNote.visibility = View.VISIBLE
            delete.visibility = View.VISIBLE
        }else{
            edit.visibility = View.INVISIBLE
            addNote.visibility = View.INVISIBLE
            delete.visibility = View.INVISIBLE
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyNotes.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyNotes().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}