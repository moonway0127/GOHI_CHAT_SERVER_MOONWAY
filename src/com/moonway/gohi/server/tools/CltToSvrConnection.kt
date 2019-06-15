package com.moonway.gohi.server.tools

import java.net.Socket


/**
 * @program: CltToSvrConnection
 *
 * @description: 客户端连接服务器
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-02-17 17:30
 **/



class CltToSvrConnection(){
    companion object {
       fun Connection():Socket{
            return Socket("127.0.0.1", 6767)
       }
    }
}