package com.moonway.gohi.server.entity

import java.io.Serializable


/**
 * @program: UserNotificationEntity
 *
 * @description: 用户通知信息列表
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-03-17 08:20
 **/

class UserNotificationEntity():Serializable{

    var ntid:String? = null
    var flag_read :Int = 0
    var level:Int = 0
    var uid_from:Long = 0
    var uid_to:Long = 0
    var time:String? = null

}