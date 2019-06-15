package com.moonway.gohi.test

import com.moonway.gohi.server.constant.Constant
import com.moonway.gohi.server.entity.MsgEntity
import com.moonway.gohi.server.entity.UserEntity
import com.moonway.gohi.server.tools.CltToSvrConnection
import java.io.ObjectOutputStream


/**
 * @program: ClientDelete
 *
 * @description: 删除
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-02-27 15:01
 **/
fun main() {

    //TODO 生产版本只更改账号可用状态 不进行删除
    var socket = CltToSvrConnection.Connection()
    var bfw = ObjectOutputStream(socket.getOutputStream())
    bfw.writeObject(
        MsgEntity(
            Constant.MSG_FLAG_USERDELETE,
            10015.toLong() as Object)
    )
    bfw.flush()

}