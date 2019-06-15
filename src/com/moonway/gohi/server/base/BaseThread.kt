package com.moonway.gohi.server.base

import com.moonway.gohi.server.constant.Constant
import com.moonway.gohi.server.control.thread.LifeThread
import com.moonway.gohi.server.dao.UserDaoImpl
import com.moonway.gohi.server.entity.*
import com.moonway.gohi.server.map.NotificationSocketMap
import com.moonway.gohi.server.tools.NotificationFormat

import java.io.DataOutputStream
import java.io.ObjectOutputStream
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType


/**
 * @program: BaseThread
 *
 * @description: 线程基类
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-01-09 13:13
 **/


open class BaseThread : Thread() {
    override fun run() {
        super.run()
    }


    fun pushMsg(oos: ObjectOutputStream, msgFlag: Int, entity: Object) {
        if (msgFlag == Constant.MSG_FLAG_REGISTER) {
            oos.writeObject(entity)
        }else if(msgFlag == Constant.MSG_FLAG_SELECTUSERBYID){

            oos.writeObject(entity)
            println("查询结果发出")
        }

        oos.flush()
        oos.close()
    }


//    fun pushMsg(oos: ObjectOutputStream, msgFlag: Int, msgContent: Object, entity: Object) {
//        if (msgFlag == Constant.MSG_FLAG_HEARTBEAT) {
//            var temp = NotificationFormat.SealString(msgFlag, msgContent)
//            println(temp)
//            oos.writeUTF(temp)
//
//            NotificationSocketMap.get().lifeMap[(entity as LifeEntity).uid!!] = true
//            (NotificationSocketMap.get().lifeThreadMap[(entity as LifeEntity).uid!!])!!.resetLifeTime()
//
//            //TODO 心跳若中断 在心跳复苏时重新通过登录创建推送信道
//        }
//
//        oos.flush()
//        oos.close()
//    }

    //心跳返回消息
    fun pushMsg(oos: ObjectOutputStream,msgFlag: Int, userNotificationList: MutableList<UserNotificationEntity>, userAllFriendList: MutableList<UserEntity>,uid:Long) {
        if (msgFlag == Constant.MSG_FLAG_HEARTBEAT) {
           var headRequestBackEntity = HeadRequestBackEntity()
            NotificationSocketMap.get().chatContentMap[uid]?.let { headRequestBackEntity.chatContentListAll = it }
            headRequestBackEntity.msgFlag = msgFlag
            headRequestBackEntity.userAllFriendList = userAllFriendList
            headRequestBackEntity.userNotificationList = userNotificationList


            oos.writeObject(headRequestBackEntity as Object)

            (NotificationSocketMap.get().lifeThreadMap[uid])!!.resetLifeTime()

            NotificationSocketMap.get().lifeMap[uid] = true

            //TODO 心跳若中断 在心跳复苏时重新通过登录创建推送信道
        }

        oos.flush()
        oos.close()
    }


    fun pushMsg(socket: Socket, entity: Object, msgContent: Object, msgFlag: Int, oos: ObjectOutputStream) {
        if (Constant.MSG_FLAG_LOGIN_AND_NOTIFICATION_LOGIN == msgFlag) {//登录消息成功或失败消息推送

            var loginEntity = entity as LoginEntity
            var temp: String = NotificationFormat.SealString(msgFlag, msgContent)
            println(temp)
            oos.writeUTF(temp)
            oos.flush()
            if (msgContent as Int == Constant.LOGIN_FLAG_SUCCESS) {//账号密码正确 登录成功
                NotificationSocketMap.get().chatContentMap[loginEntity!!.uid!!] = ArrayList()
//                var temp1 :ChatContentEntity = ChatContentEntity()
//                temp1.friendId = 10001
//                var temp11 = ChatContent()
//                var temp12 = ChatContent()
//                temp11.sender =10001
//                temp11.content="第一个用户的请求"
//                temp11.time = "112233"
//                temp12.sender =10000
//                temp12.content = "第一个用户的回复"
//                temp12.time = "11223344"
//                temp1.chatContentList.add(temp11)
//                temp1.chatContentList.add(temp12)
//
//
//                var temp2 :ChatContentEntity = ChatContentEntity()
//                temp1.friendId = 10001
//                var temp21 = ChatContent()
//                var temp22 = ChatContent()
//                temp21.sender =10000
//                temp21.content="第2个用户的请求"
//                temp21.time = "112233"
//                temp22.sender =10002
//                temp22.content = "第2个用户的回复"
//                temp22.time = "11223344"
//                temp2.chatContentList.add(temp21)
//                temp2.chatContentList.add(temp22)
//
//                NotificationSocketMap.get().chatContentMap[loginEntity!!.uid!!]!!.add(temp1)
//
//                NotificationSocketMap.get().chatContentMap[loginEntity!!.uid!!]!!.add(temp2)

                NotificationSocketMap.get().socketMap[loginEntity!!.uid!!] = socket
                NotificationSocketMap.get().notificationMap[loginEntity!!.uid!!] = false

//                NotificationSocketMap.get().messageMap[loginEntity.uid!!] = loginEntity

                var lifeThread  =  LifeThread(loginEntity.uid!!)
                try {
                    if(NotificationSocketMap.get().lifeThreadMap[lifeThread!!.uid]!=null){
                        NotificationSocketMap.get().lifeThreadMap[lifeThread!!.uid]!!.thread!!.stop()
                    }
                }catch (e:Exception){
                    println(e)
                }

                NotificationSocketMap.get().lifeThreadMap[loginEntity!!.uid!!] = lifeThread
                lifeThread.run()
                NotificationSocketMap.get().lifeMap[loginEntity!!.uid!!] = true
                while (true) {
                    if (NotificationSocketMap.get().notificationMap[loginEntity.uid!!]!!) {
                        var oos = ObjectOutputStream(NotificationSocketMap.get().socketMap[loginEntity!!.uid!!]!!.getOutputStream())
                        oos.writeUTF("getNewMeaasge")
                        oos.flush()
                        NotificationSocketMap.get().notificationMap[loginEntity!!.uid!!] = false

                    }
//                    println("用户${loginEntity.uid}:${NotificationSocketMap.get().notificationMap[loginEntity!!.uid!!]}")


                    if(!NotificationSocketMap.get().lifeMap[loginEntity!!.uid!!]!!){
                        println("已断线")
                        break
                    }
                }

            } else if (msgContent as Int == Constant.LOGIN_FLAG_ERROR) {
                //账号密码错误 登录失败
            } else if (msgContent as Int == Constant.LOGIN_FLAG_DEFAULT) {
                //默认 登录出现未知错误
            }


        }else if(Constant.MSG_FLAG_CHANGEINFO == msgFlag){
            var temp: String = NotificationFormat.SealString(msgFlag, msgContent)
            println(temp)
            oos.writeUTF(temp)
            oos.flush()
        }else if(Constant.MSG_FLAG_ADDUSER == msgFlag){
            var temp:String = NotificationFormat.SealString(msgFlag,msgContent);
            println(temp)
            oos.writeUTF(temp)
            oos.flush()
        }else if(Constant.MSG_FLAG_ACCEPTORREFUSE_FRIEND == msgFlag){
            var temp:String = NotificationFormat.SealString(msgFlag,msgContent);
            println(temp)
            oos.writeUTF(temp)
            oos.flush()
        }
        oos.close()
    }
}