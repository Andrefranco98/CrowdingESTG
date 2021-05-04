package com.tp3.crowdingestg

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.EXTRA_DEVICE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity


class DeviceListActivity : AppCompatActivity() {
    private lateinit var listPairedDevices: ListView
    private lateinit var listAvailableDevices: ListView
    private lateinit var adapterPairedDevices: ArrayAdapter<String>
    private lateinit var adapterAvailableDevices: ArrayAdapter<String>
    private lateinit var progressScanDevice: ProgressBar

    private var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_list_)

        init()

    }

   fun init(){


       //listPairedDevices = findViewById(R.id.list_paired_devices)
       listAvailableDevices = findViewById(R.id.list_available_devices)
       progressScanDevice = findViewById(R.id.progress_scan_devices)

      adapterPairedDevices =  ArrayAdapter<String>(this, R.layout.device_list_item)
      adapterAvailableDevices =  ArrayAdapter<String>(this, R.layout.device_list_item)

      listPairedDevices.adapter = adapterPairedDevices
      listAvailableDevices.adapter = adapterAvailableDevices

       listAvailableDevices.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
           val info = (view as TextView).text.toString()
           val address = info.substring(info.length - 17)
           val intent = Intent()
           intent.putExtra("deviceAddress", address)
           setResult(RESULT_OK, intent)
           finish()
       }

      bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
      val pairedDevices = bluetoothAdapter.bondedDevices

      if (pairedDevices != null && pairedDevices.size > 0) {
          for (device in pairedDevices) {
              adapterPairedDevices.add(
                      """
                ${device.name}
                ${device.address}
                """.trimIndent()
              )
          }
      }

      val intentFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
      registerReceiver(bluetoothDeviceListener, intentFilter)
      val intentFilter1 = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
      registerReceiver(bluetoothDeviceListener, intentFilter1)

          }

    private val bluetoothDeviceListener = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
           var action  = intent.action

            if(BluetoothDevice.ACTION_FOUND == action){
                val rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE)
             val device = intent.getParcelableExtra<BluetoothDevice>(EXTRA_DEVICE)
                if (device != null) {
                    if(device.bondState != BluetoothDevice.BOND_BONDED){
                        if (device != null) {
                            adapterAvailableDevices.add(device.name + "\n" + device.address + "\n" + rssi)
                        }
                    }else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action){
                        progressScanDevice.visibility= View.GONE
                        if (adapterAvailableDevices.count == 0){
                            Toast.makeText(context, "No new devices found", Toast.LENGTH_SHORT).show()
                        } else{
                            Toast.makeText(
                                    context,
                                    "Click on the device to start the chat",
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_device_list, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_scan_devices -> {
                scanDevices()
                Toast.makeText(this, "Scan started", Toast.LENGTH_SHORT).show()
                return true
            }

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
        return super.onOptionsItemSelected(item)
    }


    fun scanDevices(){
        progressScanDevice.visibility = View.VISIBLE
        adapterAvailableDevices.clear()
        Toast.makeText(this, "Scan started", Toast.LENGTH_SHORT).show()

        if(bluetoothAdapter.isDiscovering){
            bluetoothAdapter.cancelDiscovery()
        }

        bluetoothAdapter.startDiscovery()


    }

    fun back(view: View) {

        val intent = Intent(this@DeviceListActivity, Home::class.java)       // ENTRA NA ATIVIDADE
        startActivity(intent)
    }


}







