package com.moonway.gohi.server.map

import com.moonway.gohi.server.control.thread.LifeThread
import com.moonway.gohi.server.entity.ChatContentEntity
import com.moonway.gohi.server.entity.LoginEntity
import com.moonway.gohi.server.entity.MsgEntity
import java.net.Socket


/**
 * @program: NotificationSocketMap
 *
 * @description: 存放所有在线用户通知线程的集合
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-01-09 14:27
 **/

class NotificationSocketMap private constructor() {


    val socketMap = mapOf<Long,Socket>().toMutableMap()
    val notificationMap = mapOf<Long,Boolean>().toMutableMap()
    val lifeMap = mapOf<Long,Boolean>().toMutableMap()
//    val messageMap = mapOf<Long,LoginEntity>().toMutableMap()
    val lifeThreadMap = mapOf<Long,LifeThread>().toMutableMap()
    val chatContentMap = mapOf<Long,MutableList<ChatContentEntity>>().toMutableMap()
    companion object {

        const val TAG = "NotificationSocketMap"
        private var instance: NotificationSocketMap? = null
            get() {
                if (field == null) {
                    field = NotificationSocketMap()
                }
                return field
            }

        fun get(): NotificationSocketMap {
            return instance!!
        }
    }

}