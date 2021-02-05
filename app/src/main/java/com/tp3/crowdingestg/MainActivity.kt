package com.tp3.crowdingestg





import android.Manifest
import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle

import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
const val LOCATION_PERMISSION_REQUEST = 101;

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)

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

             R.id.menu_search_devices -> {
                 checkPermissions()
                 Toast.makeText(applicationContext, "Searching devices", Toast.LENGTH_SHORT).show()

                 true
             }
             R.id.menu_enable_bluetooth -> {
                 enableBluetooth()
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
            startActivity(intent)

        }

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