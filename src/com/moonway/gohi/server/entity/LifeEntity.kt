package com.moonway.gohi.server.entity

import java.io.Serializable
import java.util.*
import javax.xml.crypto.Data


/**
 * @program: LifeEntity
 *
 * @description: 心跳包实体
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-02-18 18:15
 **/

class LifeEntity(uid:Long):Serializable{
    companion object {
        const val TAG = "lifeEntity"
    }

    var uid:Long? = null

    var date:Long? = null

    init {
        this.uid = uid
        this.date = Date().time
    }
}