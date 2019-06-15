package com.moonway.gohi.server.entity

import java.io.Serializable


/**
 * @program: testEntity
 *
 * @description: 测试实体类
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-01-08 15:32
 **/

class testEntity():Serializable{

    companion object {
        const val TAG = "testEntity"
    }
    var user:String? = null
    set(value) {
        field = value
        println(user)
    }
    var password:String ? = null
    set(value) {
        field = value
        println(password)
    }

    init {
        println("初始化实体类")
    }
    constructor(user:String,password:String):this(){
        this.user = user
        this.password = password
        println(this.password)
    }


}