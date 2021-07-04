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
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.tp3.crowdingestg.R.id.textView5
import com.tp3.crowdingestg.api.EndPoints
import com.tp3.crowdingestg.api.OutputPost
import com.tp3.crowdingestg.api.ServiceBuilder
import kotlinx.android.synthetic.main.activity_give_location.*
import kotlinx.android.synthetic.main.activity_scoreboard.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Compiler.enable


lateinit var editTextName: EditText
lateinit var ButtonClickMe : Button
lateinit var textView: TextView
lateinit var textspinner: TextView


private lateinit var context: Context
val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()


const val LOCATION_PERMISSION_REQUEST = 101
const val SELECT_DEVICE = 102

const val DEVICE_NAME = "deviceName"
const val TOAST = "toast"
private var connectedDevice: String? = null

private var userid : Int = 0
class GiveLocationActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_give_location)
        var token = getSharedPreferences("id", Context.MODE_PRIVATE)
        userid = token.getInt("id_login_atual",0)


        textspinner = findViewById(R.id.textView)
        ButtonClickMe = findViewById(R.id.button)
        val spinner: Spinner = findViewById(R.id.spinnersala)



// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.salas_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
            spinner.onItemSelectedListener = this

        }

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


        val newDeviceName = textspinner.text

        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.enable()
        if(bluetoothAdapter.state == BluetoothAdapter.STATE_ON){
            bluetoothAdapter.name = newDeviceName.toString()
        }


       // Toast.makeText(applicationContext, "You are on $newDeviceName", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, DeviceListActivity::class.java)
        startActivity(intent)

        val request = ServiceBuilder.buildService(EndPoints::class.java)     // crio o request
        val call = request.rewarduser_location(userid)     // id para acrescentar os pontos

        call.enqueue(object : Callback<OutputPost> {

            override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {

                if (response.isSuccessful) {
                    if (response.body()?.status == false) {
                        val c: OutputPost = response.body()!!
                        Toast.makeText(this@GiveLocationActivity, "Erro...", Toast.LENGTH_SHORT).show()
                    }else{
                        val a: OutputPost = response.body()!!
                        recyclerview.adapter?.notifyDataSetChanged()
                        Toast.makeText(this@GiveLocationActivity, "Você ganhou 1 Ponto... Parabéns!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                Toast.makeText(this@GiveLocationActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {

            R.id.menu_search_devices -> {
                checkPermissions()
                Toast.makeText(applicationContext, "Searching devices", Toast.LENGTH_SHORT).show()

                true
            }

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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val textspinner: String = parent?.getItemAtPosition(position).toString()
        textView.text = textspinner
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
