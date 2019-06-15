package com.moonway.gohi.test

import com.moonway.gohi.server.constant.Constant
import com.moonway.gohi.server.entity.MsgEntity
import com.moonway.gohi.server.entity.LoginEntity
import com.moonway.gohi.server.tools.CltToSvrConnection
import com.moonway.gohi.server.tools.NotificationFormat
import java.io.*
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*


/**
 * @program: Client
 *
 * @description: 单线程测试客户端
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-01-07 07:27
 **/


fun main(args: Array<String>) {

//
    var socket = CltToSvrConnection.Connection()
    var bfw = ObjectOutputStream(socket.getOutputStream())

    //TODO 登录失败
//    bfw.writeObject(MsgEntity(Constant.MSG_FLAG_LOGIN_AND_NOTIFICATION,LoginEntity(Constant.TEST_UID ,Constant.TEST_PWD) as Object))

    //TODO 登录成功
    bfw.writeObject(MsgEntity(Constant.MSG_FLAG_LOGIN_AND_NOTIFICATION,LoginEntity(Constant.SUPERUSER_UID.toLong() ,Constant.SUPERUSER_PWD) as Object))

    bfw.flush()
//
//    var bfw2 = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
//    var message2: String = "花Q2\n"
//    bfw2.write(message2)
//    bfw2.flush()
//

    //TODO 推送与心跳线程  (推送最终以客户端服务线程的形式展示)
    while (true){
//        var temp1 = socket.getInputStream()

        //登录
        var message: String =  ObjectInputStream(socket.getInputStream()).readUTF()
        println("接收轮询监听开始:${SimpleDateFormat("HH:mm:ss").format(Date())}")
        println(message)
        var message_content = NotificationFormat.ReleaseString(message)
        if(message_content.equals(Constant.LOGIN_FLAG_SUCCESS.toString())){
            //返回的结果是登录成功的flag 启动心跳线程 并提示登录成功
            println("login success")
            LifeThread().start()
        }else if(message_content.equals(Constant.LOGIN_FLAG_ERROR)){
            //返回的结果是登录失败的flag 无法登陆成功
            println("login error")
        }
    }

}




//        socket.close()



