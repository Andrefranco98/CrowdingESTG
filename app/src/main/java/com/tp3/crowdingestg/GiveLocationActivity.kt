package com.tp3.crowdingestg

import android.Manifest
import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.lang.Compiler.enable


lateinit var editTextName: EditText
lateinit var ButtonClickMe : Button


private lateinit var context: Context
val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
private lateinit var chatUtils: ChatUtils

const val LOCATION_PERMISSION_REQUEST = 101
const val SELECT_DEVICE = 102

const val MESSAGE_STATE_CHANGED = 0
const val MESSAGE_READ = 1
const val MESSAGE_WRITE = 2
const val MESSAGE_DEVICE_NAME = 3
const val MESSAGE_TOAST = 4

const val DEVICE_NAME = "deviceName"
const val TOAST = "toast"
private var connectedDevice: String? = null


class GiveLocationActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_give_location)

        editTextName = findViewById(R.id.textroom)
        ButtonClickMe = findViewById(R.id.button)

        ButtonClickMe.setOnClickListener(this)
        initBluetooth()
    }

    fun GotoMain(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    fun initBluetooth(){
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            Toast.makeText(applicationContext, "No Bluetooth on this device", Toast.LENGTH_SHORT).show()
            // Device doesn't support Bluetooth
        }
    }

    override fun onClick(view: View) {
        val newDeviceName = editTextName.text

        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.enable()
        if(bluetoothAdapter.state == BluetoothAdapter.STATE_ON){
            bluetoothAdapter.name = newDeviceName.toString()
        }


        Toast.makeText(applicationContext, "You are on $newDeviceName", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, DeviceListActivity::class.java)
        startActivity(intent)

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
                val intent = Intent(this@GiveLocationActivity, Login::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }

    fun enable_bluetooth(view: View) {
        enableBluetooth()
        true
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                    this@GiveLocationActivity,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    LOCATION_PERMISSION_REQUEST
            )
        } else {
            val intent = Intent(this, DeviceListActivity::class.java).apply { }
            startActivityForResult(intent, SELECT_DEVICE)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SELECT_DEVICE && resultCode == RESULT_OK) {
            val address = data?.getStringExtra("deviceAddress")
            Toast.makeText(this, "Address:$address", Toast.LENGTH_SHORT).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String?>,
            grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(applicationContext, DeviceListActivity::class.java)

            } else {
                AlertDialog.Builder(applicationContext)
                        .setCancelable(false)
                        .setMessage("Location permission is required.\n Please grant")
                        .setPositiveButton("Grant",
                                DialogInterface.OnClickListener { dialogInterface, i -> checkPermissions() })
                        .setNegativeButton("Deny",
                                DialogInterface.OnClickListener { dialogInterface, i -> finish() }).show()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    fun enableBluetooth(){
        if (bluetoothAdapter != null) {
            if (bluetoothAdapter.isEnabled){
                bluetoothAdapter.enable()
            }
            if(bluetoothAdapter.scanMode !=  BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE){
                val discoveryIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
                discoveryIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
                startActivity(discoveryIntent)
            }

        }


    }


}