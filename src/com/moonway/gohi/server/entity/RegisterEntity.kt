package com.moonway.gohi.server.entity

import java.io.Serializable


/**
 * @program: RegisterEntity
 *
 * @description: 注册返回
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-02-26 13:11
 **/

class RegisterEntity(flag:Int):Serializable{
    var flag:Int? = null
    var uid:Long? = null


    init {
        this.flag = flag
    }

    constructor(flag:Int,uid:Long):this(flag){
        this.uid = uid
    }


}