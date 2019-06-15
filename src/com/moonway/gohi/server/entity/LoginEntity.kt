package com.moonway.gohi.server.entity

import java.io.Serializable


/**
 * @program: LoginEntity
 *
 * @description: 登录信息实体类
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-01-09 10:04
 **/

class LoginEntity() :Serializable{

    companion object {
        const val TAG = "loginEntity"
    }

    var uid: Long? = null
    set(value) {
        field = value
        println("用户id:${uid}")
    }
    var password: String? = null
        set(value) {
            field = value
            println("用户pwd:${password}")
        }
    var ip: String? = null
        set(value) {
            field = value
            println("用户ip:${ip}")
        }
    var port: Int = 0
        set(value) {
            field = value
            println("用户port:${port}")
        }

    init {
        println("初始化登录实体类")
    }

    constructor(uid:Long,password:String):this(){
        this.uid = uid
        this.password = password
    }

    constructor(uid: Long, password: String, ip: String, port: Int) : this() {
        this.uid = uid
        this.password = password
        this.ip = ip
        this.port = port
    }


}