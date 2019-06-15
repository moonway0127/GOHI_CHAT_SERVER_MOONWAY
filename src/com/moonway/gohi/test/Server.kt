package com.moonway.gohi.test

import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*


/**
 * @program: Server
 *
 * @description: 单线程测试服务器
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-01-07 06:56
 **/


fun main(args: Array<String>) {

    var serverSocket = ServerSocket(6767)
    println("服务器启动")
    var socket:Socket

    while (true) {
        socket = serverSocket.accept()


        println("连接成功 time"+SimpleDateFormat("HH:mm:ss").format(Date()))

        var socket2 = socket

        var bfr = BufferedReader(InputStreamReader(socket.getInputStream()))
        var message: String = bfr.readLine()

//        Thread.sleep(5000)
        println(message)
        var bfw = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
        var reMessage: String = "服务器接收到的消息：".plus(message)+"（1）\n"
        bfw.write(reMessage)
        bfw.flush()

        Thread.sleep(5000)
        println("----------------")
        var bfw2 = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
        var reMessage2: String = "服务器接收到的消息：".plus(message)+"（2）\n"
        bfw2.write(reMessage2)
        bfw2.flush()
    }


    serverSocket.close()
    socket.close()
}
