package com.moonway.gohi.test

import com.moonway.gohi.server.constant.Constant
import com.moonway.gohi.server.entity.MsgEntity
import com.moonway.gohi.server.entity.UserEntity
import com.moonway.gohi.server.tools.CltToSvrConnection
import java.io.ObjectOutputStream


/**
 * @program: ClientSearch
 *
 * @description: 客户端查询测试
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-02-27 11:53
 **/

fun main() {
    var socket = CltToSvrConnection.Connection()
    var bfw = ObjectOutputStream(socket.getOutputStream())



    bfw.writeObject(
        MsgEntity(
            Constant.MSG_FLAG_SELECTALLFRIEND,
            10000.toLong() as Object
            )
    )

    bfw.flush()
}