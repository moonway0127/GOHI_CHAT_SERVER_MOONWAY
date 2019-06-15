package com.moonway.gohi.test

import com.moonway.gohi.server.constant.Constant
import com.moonway.gohi.server.entity.MsgEntity
import com.moonway.gohi.server.entity.UserEntity
import com.moonway.gohi.server.tools.CltToSvrConnection
import java.io.ObjectOutputStream


/**
 * @program: ClientChange
 *
 * @description: 个人信息修改
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-02-27 14:58
 **/

fun main() {
    var socket = CltToSvrConnection.Connection()
    var bfw = ObjectOutputStream(socket.getOutputStream())
    bfw.writeObject(
        MsgEntity(
            Constant.MSG_FLAG_CHANGEINFO,
            UserEntity(
                "123456",
                "嘤嘤嘤2123124",
                1,
                "https://1241241212412.jpg",
                "学校",
                1997,
                1,
                1,
                "141",
                "abc@def.com",
                "yyyyyyyyyy",
                "沈阳",10001
            ) as Object)
    )
    bfw.flush()

}