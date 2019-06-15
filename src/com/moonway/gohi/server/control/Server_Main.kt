package com.moonway.gohi.server.control

import com.moonway.gohi.server.constant.Constant
import com.moonway.gohi.server.control.thread.ServiceThread

import java.net.ServerSocket


/**
 * @program: Server_Main
 *
 * @description: 服务器main
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-01-07 16:31
 **/


fun main() {
    var serverSocket = ServerSocket(Constant.LOCALHOST_PORT)
    monitor(serverSocket)



    //TODO 用户注册Demo
//    UserDaoImpl().userRegistvar handler = object : Handler() {
//    override fun handleMessage(msg: Message) {
//        super.handleMessage(msg)
//        when(msg.what){
//         MESSAGE->main_text!!.text = msg.obj.toString()
//        }
//    }
//}
//---------
//        UserEntity(
//            "123456",
//            "嘤嘤嘤",
//            1,
//            "https://1241241212412.jpg",
//            "学校",
//            1997,
//            1,
//            1,
//            "141",
//            "abc@def.com",
//            "yyyyyyyyyy",
//            "沈阳"
//        )
//    )

    //TODO 用户登录Demo
//    UserDaoImpl().userLogin(10000, "123456")


    //TODO 用户通过id查询信息Demo
//    UserDaoImpl().selectUserInfoById(10000)//查询到
//    UserDaoImpl().selectUserInfoById(20001)//未查询到

    //TODO 用户信息修改Demo
//
//    UserDaoImpl().userInfoChange(
//        UserEntity(
//            "123456",
//            "刘奇",
//            1,
//            "https://1241241212412.jpg",
//            "学校",
//            1997,
//            1,
//            1,
//            "141",
//            "abc@def.com",
//            "yyyyyyyyyy",
//            "沈阳",
//            10010
//        )
//    )


    //TODO 查询所有此名字用户Demo
//    UserDaoImpl().selectUserInfoByName("嘤嘤嘤")



    //TODO 删除用户Demo （此方法只为文档要求，生产/测试版只更改是否可用flag）
//    UserDaoImpl().deleteUserById(10008)



}


fun monitor(serverSocket: ServerSocket) {
    while (true) {
        var socket = serverSocket.accept()
        println("接收到新请求")
        ServiceThread(socket, serverSocket).start()


    }
}