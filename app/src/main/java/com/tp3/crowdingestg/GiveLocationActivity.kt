package com.tp3.crowdingestg

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.Compiler.enable


lateinit var editTextName: EditText
lateinit var ButtonClickMe : Button



class GiveLocationActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_give_location)

        editTextName = findViewById(R.id.textroom)
        ButtonClickMe = findViewById(R.id.button)

        ButtonClickMe.setOnClickListener(this)
    }

    fun GotoMain(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    override fun onClick(view: View) {
        val newDeviceName = editTextName.text

        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.enable()
        if(bluetoothAdapter.state == BluetoothAdapter.STATE_ON){
            bluetoothAdapter.name = newDeviceName.toString()
        }


        Toast.makeText(applicationContext, "You are on $newDeviceName", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }



}