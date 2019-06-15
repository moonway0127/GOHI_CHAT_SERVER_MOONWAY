package com.moonway.gohi.server.control.thread

import com.moonway.gohi.server.constant.Constant
import com.moonway.gohi.server.entity.LifeEntity
import com.moonway.gohi.server.entity.LoginEntity
import com.moonway.gohi.server.entity.MsgEntity
import com.moonway.gohi.server.map.NotificationSocketMap
import com.moonway.gohi.server.tools.CltToSvrConnection
import com.moonway.gohi.server.tools.NotificationFormat
import java.io.DataInputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.rmi.server.UID
import java.util.*


/**
 * @program: LifeThread
 *
 * @description: 心跳监听
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-02-17 17:00
 *
 *
 * @memo: 超过30秒未接受心跳设置为离线
 **/

open class LifeThread(uid:Long) {


    var uid: Long? = null

    init {
        this.uid = uid
    }



    var lifeTime: Int = 0

    fun resetLifeTime() {
        lifeTime = 0
    }

    init {
        lifeTime = 0
    }
        var thread:Thread? = null

    fun run(){

         thread = object :Thread(){
            override fun run() {
                println("线程启动")
                super.run()
                NotificationSocketMap.get().lifeMap[uid!!] = true

                //30秒心跳
                while (lifeTime in 0..29) {
                    Thread.sleep(1000)
                    lifeTime++
//                    println("心跳监听：" + lifeTime)
//                    try{
//                       var oos = ObjectOutputStream(NotificationSocketMap.get().socketMap[uid!!]!!.getOutputStream())
//                        oos.writeUTF("lalala")
//                        oos.flush()
//                    }catch (e:Exception){
//                        println(e)
//                        break
//                    }
                }

                NotificationSocketMap.get().lifeMap[uid!!] = false

            }
        }
        thread!!.start()
        println("--------------")
    }

}