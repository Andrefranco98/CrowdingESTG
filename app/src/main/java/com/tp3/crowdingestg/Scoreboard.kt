package com.tp3.crowdingestg

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.tp3.crowdingestg.adapters.LineAdapter
import com.tp3.crowdingestg.dataclasses.Place
import kotlinx.android.synthetic.main.activity_scoreboard.*

class Scoreboard : AppCompatActivity() {

    private lateinit var myList: ArrayList<Place>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)

    //    val request = ServiceBuilder.buildService(EndPoints::class.java)
      //  val call = request.getscoreaboard()

        myList = ArrayList<Place>()

        for (i in 1 until 500) {
            myList.add(Place("$i º", "Marco", 500-(i*20), "Contribuições"))
        }
        recyclerview.adapter = LineAdapter(myList)
        recyclerview.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_givelocationactivity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {

            R.id.logout -> {
                var token = getSharedPreferences("username", Context.MODE_PRIVATE)
                var editor = token.edit()
                editor.putString("username_login_atual"," ")        // Iguala valor a vazio, fica sem valor, credenciais soltas
                editor.commit()                                     // Atualizar editor
                val intent = Intent(this@Scoreboard, Login::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }
    }