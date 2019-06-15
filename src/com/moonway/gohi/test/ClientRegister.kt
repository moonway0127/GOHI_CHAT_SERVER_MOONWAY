package com.moonway.gohi.test

import com.moonway.gohi.server.constant.Constant
import com.moonway.gohi.server.entity.LoginEntity
import com.moonway.gohi.server.entity.MsgEntity
import com.moonway.gohi.server.entity.RegisterEntity
import com.moonway.gohi.server.entity.UserEntity
import com.moonway.gohi.server.tools.CltToSvrConnection
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


/**
 * @program: ClientRegister
 *
 * @description: 注册测试客户端
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-02-26 13:25
 **/

fun main() {
    var socket = CltToSvrConnection.Connection()
    var bfw = ObjectOutputStream(socket.getOutputStream())
    bfw.writeObject(
        MsgEntity(
            Constant.MSG_FLAG_REGISTER,
            UserEntity(
            "123456",
            "嘤嘤嘤",
            1,
            "https://1241241212412.jpg",
            "学校",
            1997,
            1,
            1,
            "141",
            "abc@def.com",
            "yyyyyyyyyy",
            "沈阳"
        ) as Object)
    )
    bfw.flush()

    //TODO 超时设定
    var message = ObjectInputStream(socket.getInputStream()).readObject() as RegisterEntity
    println(message.flag)
    println(
        message.uid
    )
}