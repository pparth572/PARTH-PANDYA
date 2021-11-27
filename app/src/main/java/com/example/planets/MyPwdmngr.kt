package com.example.planets

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ListView
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_my_notes.*
import kotlinx.android.synthetic.main.fragment_my_notes.add
import kotlinx.android.synthetic.main.fragment_my_pwdmngr.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyPwdmngr.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyPwdmngr : Fragment() {
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
        var v =  inflater.inflate(R.layout.fragment_my_pwdmngr, container, false)

        var add = v.findViewById<FloatingActionButton>(R.id.add)
        //var edit = v.findViewById<FloatingActionButton>(R.id.edit)
       // var delete = v.findViewById<FloatingActionButton>(R.id.delete)
        var add_pwd = v.findViewById<FloatingActionButton>(R.id.add_pwd)
        var My_pwdmngr_lv = v.findViewById<ListView>(R.id.my_pwdmngr_lv)
        var search_uname = v.findViewById<SearchView>(R.id.search_uname)

        var helper = context?.let { MyHelper(it) }
        var db = helper?.writableDatabase

        var rs = db!!.rawQuery("SELECT * FROM PWDMNGR ORDER BY _ID DESC",null)


        search_uname.queryHint = "Search Among ${rs.count} Records"

        //var arr = mutableListOf<String>("")
        var adapter = SimpleCursorAdapter(context,R.layout.pwdmngr_layout,rs,
            arrayOf("WNAME"),
            intArrayOf(R.id.tv_url_name),0
        )


        search_uname.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                rs = db.rawQuery("SELECT * FROM PWDMNGR WHERE UNAME LIKE '%${p0}%' OR PWD LIKE '%${p0}%' OR WNAME LIKE '%${p0}%'",null)
                adapter.changeCursor(rs)
                return false
            }





        })

        My_pwdmngr_lv.adapter = adapter

        My_pwdmngr_lv.setOnItemClickListener { adapterView, view, i, l ->
            var intent = Intent(requireContext(),MyPwdmng::class.java)
            intent.putExtra("MyPwdmngr",rs.getString(0))
            startActivity(intent)

            activity?.finish()
        }


        add.setOnClickListener{
            onAddButtonClicked()
        }
//        edit.setOnClickListener{
////            rs.moveToNext()
////            rs.moveToNext()
////
////            Toast.makeText(context,"${rs.getString(1)}",Toast.LENGTH_LONG).show()
//
//        }
        add_pwd.setOnClickListener{
            startActivity(Intent(context,AddPwd::class.java))

            activity?.finish()

            //Toast.makeText(context,"Add Note",Toast.LENGTH_LONG).show()
            //fragmentManager?.beginTransaction()?.replace(R.id.frame, MyPictures())?.commit()
        }
//        delete.setOnClickListener{
//
//        }

        return v
    }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked : Boolean) {
        if(!clicked) {
           // edit.startAnimation(fromBottom)
            add_pwd.startAnimation(fromBottom)
            //delete.startAnimation(fromBottom)
            add.startAnimation(rotateOpen)
        }else{
           // edit.startAnimation(toBottom)
            add_pwd.startAnimation(toBottom)
            //delete.startAnimation(toBottom)
            add.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked) {
           // edit.visibility = View.VISIBLE
            add_pwd.visibility = View.VISIBLE
            //delete.visibility = View.VISIBLE
        }else{
            //edit.visibility = View.INVISIBLE
            add_pwd.visibility = View.INVISIBLE
            //delete.visibility = View.INVISIBLE
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyPwdmngr.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyPwdmngr().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}