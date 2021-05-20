package com.tp3.crowdingestg

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.tp3.crowdingestg.api.EndPoints
import com.tp3.crowdingestg.api.ServiceBuilder
import kotlinx.android.synthetic.main.activity_home.view.*

class Scoreboard : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getscoreaboard()
    }
    }