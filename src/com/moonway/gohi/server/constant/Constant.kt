package com.moonway.gohi.server.constant

import com.moonway.gohi.server.entity.LoginEntity


/**
 * @program: Constant
 *
 * @description: 静态内容
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-01-07 16:37
 **/

class Constant {
    companion object {
        const val LOCALHOST_IP: String = "127.0.0.1"
        const val LOCALHOST_PORT = 6767

        //消息类型flag
        const val MSG_FLAG_DEFAULT = -1000001
        const val MSG_FLAG_LOGIN_AND_NOTIFICATION = 10000001
        const val MSG_FLAG_HEARTBEAT = 10000002
        const val MSG_FLAG_REGISTER = 10000003
        const val MSG_FLAG_SELECTUSERBYID = 10000004
        const val MSG_FLAG_SELECTUSERBYNAME = 10000005
        const val MSG_FLAG_SELECTALLFRIEND = 10000006
        const val MSG_FLAG_CHANGEINFO = 10000007
        const val MSG_FLAG_USERDELETE = 10000008
        const val MSG_FLAG_ADDUSER = 10000009
        const val MSG_FLAG_ACCEPTORREFUSE_FRIEND = 11000001
        const val MSG_FLAG_SEND_CHAT = 11000002
        const val MSG_FLAG_CLEAR_CHAT = 11000003
        const val MSG_FLAG_DELETE_FRIEND = 11000004
        //登录与通知
        const val MSG_FLAG_LOGIN_AND_NOTIFICATION_LOGIN = 10000011
        const val MSG_FLAG_LOGIN_AND_NOFIFICATION_NOTIFICATION = 10000021

        //登录判断的flag
        const val LOGIN_FLAG_DEFAULT = -2000001
        const val LOGIN_FLAG_SUCCESS = 20000011
        const val LOGIN_FLAG_ERROR = 20000001


        //心跳
        const val HEARTBEAT_FLAG_SUCCESS = 2000012
        const val HEARTBEAT_FLAG_ERROR = 20000002


        //注册
        const val REGISTER_FLAG_SUCCESS = 20000013
        const val REGISTER_FLAG_ERROR = 20000003


        //查询插入通用
        const val SEARCH_FLAG_HAVE = 90000001
        const val SEARCH_FLAG_NO_HAVE=90000000

        const val NOTIFICATION_FLAG=30000001


        const val USERINFO_SEARCH_ERROR = -1

        const val MYSQL_USER_NAME = "root"
        const val MYSQL_USER_PASSWPRD = "84228698liu"

        //测试用账号密码
        const val TEST_UID = 123124124124
        const val TEST_PWD = "fqijoqofiqje"


        //superuser
        const val SUPERUSER_UID = 10000
        const val SUPERUSER_PWD = "123456"


        //online flag
        const val FLAG_ONLINE_ON = 1
        const val FLAG_ONLINE_OFF = 0

        //notification level
        const val FLAG_NOTIFICATION_ADDUSER = 1

        //notification read
        const val FLAG_NOTIFICATION_READED = 1
        const val FLAG_NOTIFICATION_NOT_READ = 0

    }


}