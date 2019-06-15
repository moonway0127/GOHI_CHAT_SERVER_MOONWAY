package com.moonway.gohi.server.entity

import java.io.Serializable
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType


/**
 * @program: SearchDateEntity
 *
 * @description: 查询数据集合实体类
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-02-27 11:13
 **/

class SearchDateEntity(arrayFlag: Int):Serializable{
    var arrayFlag:Int? = null
    var entity:Object? =null

    init {
        this.arrayFlag = arrayFlag
    }

    constructor(arrayFlag: Int,entity:Object):this(arrayFlag){
        this.entity = entity
    }

}