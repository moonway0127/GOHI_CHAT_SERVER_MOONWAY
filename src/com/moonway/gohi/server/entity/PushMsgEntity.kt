package com.moonway.gohi.server.entity

import com.moonway.gohi.server.constant.Constant
import java.io.Serializable
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType


/**
 * @program: SimpleMsgEntity
 *
 * @description: 推送消息实体类
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-01-09 13:18
 **/
class PushMsgEntity(msgFlag: Int) :Serializable{

    companion object {
        const val TAG = "PushMsgEntity"
    }

    var msgFlag: Int? = null
        set(value) {
            field = value
            println(msgFlag)
        }

    init {
        println("推送消息实体")
        this.msgFlag = msgFlag
    }


}