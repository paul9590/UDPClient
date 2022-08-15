package com.pingmo.udpclient

import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pingmo.udpclient.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val mList = ArrayList<String>()
        val mAdapter = ChatAdapter(mList)
        mBinding.listChat.adapter = mAdapter
        mBinding.listChat.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val readHandler = Handler {msg : Message ->
            mList.add("Host : ${msg.obj}")
            mAdapter.notifyDataSetChanged()
            true
        }
        val udpClient = UDPClient(6020, readHandler)

        mBinding.btnSend.setOnClickListener {
            val msg = Message()
            msg.what = 100
            msg.obj = mBinding.editSend.text.toString()
            mList.add("Client : ${msg.obj}")
            mAdapter.notifyDataSetChanged()
            udpClient.write(msg)
        }
    }
}