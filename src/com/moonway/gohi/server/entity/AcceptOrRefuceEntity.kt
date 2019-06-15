package com.moonway.gohi.server.entity

import java.io.Serializable


/**
 * @program: AcceptOrRefuceEntity
 *
 * @description: 用户同意或拒绝好友申请
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-03-18 07:30
 **/

class AcceptOrRefuceEntity():Serializable{
    var uid :Long= 0
    var uid_friend :Long= 0
    var flag = 0
    var time:String ? = null
    var notificationId:String? = null

}