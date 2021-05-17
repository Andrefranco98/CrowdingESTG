package com.tp3.crowdingestg

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView

class Home : AppCompatActivity() {
    private lateinit var nomesharedpreference : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var token = getSharedPreferences("username", Context.MODE_PRIVATE)
        nomesharedpreference = token.getString("username_login_atual"," ").toString()

        findViewById<TextView>(R.id.username).setText(nomesharedpreference)
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
                val intent = Intent(this@Home, Login::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }

    fun give_my_location(view: View) {
        val intent = Intent(this@Home, GiveLocationActivity::class.java)
        startActivity(intent)
        true
    }

    fun nearby_locations(view: View) {
        val intent = Intent(this@Home, DeviceListActivity::class.java)
        startActivity(intent)
        true
    }

    fun help(view: View) {
        val intent = Intent(this@Home, MainActivity::class.java)
        startActivity(intent)
        true
    }
}