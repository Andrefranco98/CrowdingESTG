package com.tp3.crowdingestg





import android.Manifest
import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager


import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


private lateinit var context: Context

private lateinit var chatUtils: ChatUtils


private var connectedDevice: String? = null




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBluetooth()
    }


  /*  @Suppress("DEPRECATION")
    private val handler = Handler { message ->
        when (message.what) {
            MESSAGE_STATE_CHANGED -> when (message.arg1) {
                ChatUtils.STATE_NONE-> setState("Not Connected")
                ChatUtils.STATE_LISTEN -> setState("Not Connected")
                ChatUtils.STATE_CONNECTING -> setState("Connecting...")
                ChatUtils.STATE_CONNECTED -> setState("Connected: $connectedDevice")
            }
            MESSAGE_WRITE -> {
                val buffer1 = message.obj as ByteArray
                val outputBuffer = String(buffer1)
                adapterMainChat.add("Me: $outputBuffer")
            }
            MESSAGE_READ -> {
                val buffer = message.obj as ByteArray
                val inputBuffer = String(buffer, 0, message.arg1)
                adapterMainChat.add("$connectedDevice: $inputBuffer")
            }
            MESSAGE_DEVICE_NAME -> {
                connectedDevice = message.data.getString(DEVICE_NAME)
                Toast.makeText(context, connectedDevice, Toast.LENGTH_SHORT).show()
            }
            MESSAGE_TOAST -> Toast.makeText(context, message.data.getString(TOAST), Toast.LENGTH_SHORT).show()
        }
        false
    }
*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_givelocationactivity, menu)

        return true
    }

    fun initBluetooth(){
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            Toast.makeText(applicationContext, "No Bluetooth on this device", Toast.LENGTH_SHORT).show()
            // Device doesn't support Bluetooth
        }
    }


     override fun onOptionsItemSelected(item: MenuItem): Boolean {
         return when (item.itemId) {


             R.id.logout -> {
                 var token = getSharedPreferences("username", Context.MODE_PRIVATE)
                 var editor = token.edit()
                 editor.putString("username_login_atual"," ")        // Iguala valor a vazio, fica sem valor, credenciais soltas
                 editor.commit()                                     // Atualizar editor
                 val intent = Intent(this, Login::class.java)
                 startActivity(intent)
                 true
             }
             else -> super.onOptionsItemSelected(item)
         }
     }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                    this@MainActivity,
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