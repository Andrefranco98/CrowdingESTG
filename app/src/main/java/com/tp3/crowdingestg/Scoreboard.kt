package com.tp3.crowdingestg

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class Scoreboard : AppCompatActivity() {
    private lateinit var listPairedDevices: ListView
    private lateinit var listAvailableDevices: ListView
    private lateinit var adapterPairedDevices: ArrayAdapter<String>
    private lateinit var adapterAvailableDevices: ArrayAdapter<String>
    private lateinit var progressScanDevice: ProgressBar

    private var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)
    }
    }