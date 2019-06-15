package com.moonway.gohi.server.entity

import com.moonway.gohi.server.constant.Constant
import java.io.Serializable
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType


/**
 * @program: MsgEntity
 *
 * @description: 接收消息实体类
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-01-08 16:43
 **/

class MsgEntity(msgFlag: Int):Serializable{
    companion object {
        const val TAG = "MsgEntity"
    }

    var msgFlag:Int = Constant.MSG_FLAG_DEFAULT
    set(value) {
        field = value
        println(TAG+msgFlag)
    }
    var entityObject:Object? = null
    set(value) {
        field = value
        println(TAG+entityObject!!)
    }
        init {
            println("创建消息标识实体类成功")
            this.msgFlag = msgFlag
        }

    constructor(msgFlag:Int , entityObject:Object):this(msgFlag){
        this.entityObject = entityObject
    }
}