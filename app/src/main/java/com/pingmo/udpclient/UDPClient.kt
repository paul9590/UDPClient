package com.pingmo.udpclient

import android.os.Handler
import android.os.Looper
import android.os.Message
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress


class UDPClient(port: Int, readHandler: Handler){
    lateinit var socket : DatagramSocket
    lateinit var readThread : ReadThread
    lateinit var writeThread : WriteThread

    init {
        try {
            socket = DatagramSocket(port)
            readThread = ReadThread(socket, readHandler)
            writeThread = WriteThread(socket)
            readThread.start()
            writeThread.start()

        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    fun write(msg : Message) {
        writeThread.write(msg)
    }
}

class ReadThread(socket: DatagramSocket, readHandler: Handler) : Thread(){
    val socket = socket
    val readHandler = readHandler

    override fun run() {
        super.run()
        while (true) {
            try {
                val data = ByteArray(100)
                val recv = DatagramPacket(data, data.size)
                socket.receive(recv)
                val msg = Message()
                msg.what = 101
                msg.obj = String(recv.data)
                readHandler.sendMessage(msg)
            }catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }
}

class WriteThread(socket: DatagramSocket) : Thread() {
    val socket = socket
    lateinit var writeHandler : Handler
    override fun run() {
        super.run()
        Looper.prepare()
        writeHandler = Handler { msg: Message ->
            try {
                val data: ByteArray = msg.obj.toString().toByteArray()
                val send = DatagramPacket(data, data.size, InetAddress.getByName("192.168.200.122"), 25400)
                socket.send(send)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            true
        }
        Looper.loop()
    }

    fun write(msg : Message) {
        writeHandler.sendMessage(msg)
    }
}

