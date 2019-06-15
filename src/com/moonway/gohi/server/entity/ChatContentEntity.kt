package com.moonway.gohi.server.entity

import java.io.Serializable
import java.util.ArrayList


/**
 * @program: ChatContentEntity
 *
 * @description: 用户聊天内容
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-03-17 09:01
 **/

class ChatContentEntity():Serializable{
    var chatContentList :MutableList<ChatContent> = ArrayList()
    var friendId:Long = 0
}


class ChatContent():Serializable{
    var sender:Long = 0
    var time:String? = null
    var content :String? = null

}