package com.moonway.gohi.test

import com.moonway.gohi.server.constant.Constant
import com.moonway.gohi.server.entity.LifeEntity
import com.moonway.gohi.server.entity.LoginEntity
import com.moonway.gohi.server.entity.MsgEntity
import com.moonway.gohi.server.tools.CltToSvrConnection
import com.moonway.gohi.server.tools.NotificationFormat
import java.io.DataInputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.*


/**
 * @program: LifeThread
 *
 * @description: 心跳线程
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-02-17 17:00
 *
 *
 * @memo: 心跳发送时间为：10s  服务器推送消息将会刷新心跳包发送时间
 **/

open class LifeThread: Thread(){


    companion object {

        private var lifeTime:Int = 0
        fun ResetLifeTime(){
            lifeTime = 0
        }
    }

    init {
        lifeTime = 0
    }

    override fun run() {
        super.run()
        while (true){

            println("向服务器发送心跳 bilibili！~")
            try {
                var socket = CltToSvrConnection.Connection()
                var bfw = ObjectOutputStream(socket.getOutputStream())
                bfw.writeObject(MsgEntity(Constant.MSG_FLAG_HEARTBEAT,LifeEntity(Constant.SUPERUSER_UID.toLong()) as Object ))
                bfw.flush()
                println("发送完成等待回应 dididididi!~")
                //设置5秒超时
                socket.soTimeout = 5000
                var message: String =  ObjectInputStream(socket.getInputStream()).readUTF()
                println("接收到消息 bibibibi！~")
                println(message)

                var content = NotificationFormat.ReleaseString(message)

                if(Constant.HEARTBEAT_FLAG_SUCCESS==content.toInt()){
                    println("心跳回复正确")
                }else{
                    println("心跳回复错误，预计停止线程")
                }
            }catch (e:ConnectException){
                println("连接网络失败，进入离线状态")
            }catch (e:SocketTimeoutException){
                println("连接网络超时，进入离线状态")
            }

            //10秒心跳
            while (lifeTime in 0..9){
                Thread.sleep(1000)
                lifeTime++
                println(lifeTime)

            }
            lifeTime =0
        }

    }
}