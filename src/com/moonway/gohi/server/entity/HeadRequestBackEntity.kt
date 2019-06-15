package com.moonway.gohi.server.entity

import java.io.Serializable


/**
 * @program: HeadRequestBackEntity
 *
 * @description: 心跳线程请求返回
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-03-17 08:53
 **/

class HeadRequestBackEntity():Serializable{
    var userNotificationList :MutableList<UserNotificationEntity> = ArrayList()
    var userAllFriendList : MutableList<UserEntity> = ArrayList()
    var msgFlag :Int = 0
    var chatContentListAll:MutableList<ChatContentEntity> = ArrayList()
}