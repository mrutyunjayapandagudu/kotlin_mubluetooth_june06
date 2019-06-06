package com.example.iteradmin.kotlin_mubluetooth_june06

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
val ba:BluetoothAdapter= BluetoothAdapter.getDefaultAdapter()
    val REQUEST_CODE:Int=100
    var list:ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val active=findViewById<Button>(R.id.button)
         list=findViewById<ListView>(R.id.list)

        active.setOnClickListener{
            if(ba==null){
                Toast.makeText(this,"Bluetooth is not supported",Toast.LENGTH_LONG).show()
            }
            else{
                if(ba.isEnabled==false){
                    active.text="off"
                    val i=Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    startActivityForResult(i,REQUEST_CODE)
                }
                else{
                    ba.disable()
                    active.text="on"
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQUEST_CODE&&resultCode==Activity.RESULT_OK){
            Toast.makeText(this,"bluetooth is on",Toast.LENGTH_LONG).show()
            var data:Array<String> = arrayOf()
            val devices:Set<BluetoothDevice> = ba.bondedDevices
            for(device in devices){
                val name:String=device.name
                val address:String=device.address
                data += name
            }
            val adp=ArrayAdapter(this,android.R.layout.simple_list_item_1,data)
            list?.adapter =adp
        }
            else{
            Toast.makeText(this,"bluetooth is off",Toast.LENGTH_LONG).show()
        }
    }
}
