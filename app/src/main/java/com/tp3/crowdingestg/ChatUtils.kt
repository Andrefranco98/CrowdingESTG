package com.tp3.crowdingestg

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.os.Handler


class ChatUtils {
    private lateinit var context: Context
    private lateinit var handler: Handler
    private lateinit var bluetoothAdapter: BluetoothAdapter


    val STATE_NONE = 0
    val STATE_LISTEN = 1
    val STATE_CONNECTING = 2
    val STATE_CONNECTED = 3

    private var state = 0

    fun ChatUtils(context: Context, handler: Handler){
        this.context = context
        this.handler = handler

        state = STATE_NONE
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    fun getState(): Int {
        return state
    }


  @Synchronized
  fun setState(state: Int) {
      this.state = state
     // handler.obtainMessage(MainActivity.MESSAGE_STATE_CHANGED, state, -1).sendToTarget()
   }





}