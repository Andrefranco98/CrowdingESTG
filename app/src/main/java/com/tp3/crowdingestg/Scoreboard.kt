package com.tp3.crowdingestg

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.tp3.crowdingestg.adapters.LineAdapter
import com.tp3.crowdingestg.api.EndPoints
import com.tp3.crowdingestg.api.OutputPost
import com.tp3.crowdingestg.api.ServiceBuilder
import com.tp3.crowdingestg.api.scoreboard
import com.tp3.crowdingestg.dataclasses.Place
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_scoreboard.*
import kotlinx.android.synthetic.main.recyclerline.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Scoreboard : AppCompatActivity() {
    private lateinit var myList: ArrayList<Place>
    private lateinit var scoreboard2: List<scoreboard>
    private var userpontos : Int = 0
    private var posicao : Int = 1
    private lateinit var username : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)

        val request = ServiceBuilder.buildService(EndPoints::class.java)     // crio o request
        val call = request.getscoreaboard()

        call.enqueue(object : Callback<List<scoreboard>> {
            override fun onResponse(call: Call<List<scoreboard>>, response: Response<List<scoreboard>>) {
                if (response.isSuccessful) {
                    scoreboard2 = response.body()!!
                    myList = ArrayList<Place>()
                    for (tabela in scoreboard2){
                        myList.add(Place("$posicao º", tabela.name, tabela.pontos, "Contribuições"))
                        posicao += 1
                    }
                    recyclerview.adapter = LineAdapter(myList)
                    recyclerview.layoutManager = LinearLayoutManager(this@Scoreboard)
                }
            }
            override fun onFailure(call: Call<List<scoreboard>>, t: Throwable) {
                Toast.makeText(this@Scoreboard, "ERRO", Toast.LENGTH_SHORT).show()
            }
        })
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