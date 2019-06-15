package com.moonway.gohi.server.control.thread

import com.moonway.gohi.server.base.BaseThread
import com.moonway.gohi.server.constant.Constant
import com.moonway.gohi.server.dao.UserDaoImpl
import com.moonway.gohi.server.entity.*
import com.moonway.gohi.server.map.NotificationSocketMap
import java.io.DataOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ServerSocket
import java.net.Socket
import java.util.*
import kotlin.collections.ArrayList


/**
 * @program: ServiceThread
 *
 * @description: 服务器线程
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-01-08 07:16
 **/

class ServiceThread(socket: Socket, serverSocket: ServerSocket) : BaseThread() {
    companion object {
        const val TAG = "ServiceThread"
    }

    var oos: ObjectOutputStream? = null
    var ois: ObjectInputStream? = null
    var msgEntity: MsgEntity? = null
    var loginEntity: LoginEntity? = null
    var ip: String? = null
    var port: Int = 0
    var flag_Socket: Boolean = true
    var socket: Socket? = null

    init {
        try {
            println("线程初始化开始")
            this.socket = socket
            oos = ObjectOutputStream(socket.getOutputStream())
            ois = ObjectInputStream(socket.getInputStream())!!
            ip = socket?.inetAddress?.hostAddress
            port = socket?.port
            println("ip${ip} port${port}")

        } catch (e: NullPointerException) {
            flag_Socket = false
            println("socket包含内容异常")
        } catch (e: Exception) {
            flag_Socket = false
            println("线程初始化错误")
            println(e.toString())
        }
    }


    override fun run() {
        super.run()
        println("线程开始")
        if (flag_Socket) {
            try {
                msgEntity = ois!!.readObject() as MsgEntity
                var msgFlag = msgEntity?.let { it.msgFlag }!!
                println(msgEntity)
                if (Constant.MSG_FLAG_LOGIN_AND_NOTIFICATION == msgFlag) { //登录验证
                    loginEntity = msgEntity!!.entityObject as LoginEntity
                    loginEntity?.let {
                        it.ip = ip
                        it.port = port
                    }!!

                    var login_flag = UserDaoImpl().userLogin(loginEntity!!.uid!!, loginEntity!!.password!!)
                    println("登录结果：${login_flag}")
                    pushMsg(
                        socket!!,
                        loginEntity as Object,
                        login_flag as Object,
                        Constant.MSG_FLAG_LOGIN_AND_NOTIFICATION_LOGIN,
                        oos!!
                    )

                } else if (Constant.MSG_FLAG_HEARTBEAT == msgFlag) { //心跳
                    var lifeEntity = msgEntity?.let { it.entityObject } as LifeEntity
                    if (Date().time - lifeEntity.date as Long <= 1000) {//检查是否心跳超时
                        if (true) {//TODO 未来加入安全码
                            var changeOnlineFlagResult = UserDaoImpl().changeOnlineFlag(lifeEntity.uid!!,Constant.FLAG_ONLINE_ON)
                            if(changeOnlineFlagResult!=0){
                                //查询通知表
                              var userNotificationEntityList = UserDaoImpl().selectNotification(lifeEntity.uid!!)
                                var userAllFriendList = UserDaoImpl().selectAllFriend(lifeEntity!!.uid!!)

                                pushMsg(oos!!,Constant.MSG_FLAG_HEARTBEAT,userNotificationEntityList,userAllFriendList,lifeEntity!!.uid!!)
                            }
//                            pushMsg(oos!!, Constant.MSG_FLAG_HEARTBEAT, Constant.HEARTBEAT_FLAG_SUCCESS as Object,lifeEntity as Object)
                        } else {
//                            pushMsg(oos!!, Constant.MSG_FLAG_HEARTBEAT, Constant.HEARTBEAT_FLAG_ERROR as Object,lifeEntity as Object)
                        }
                    }
                } else if (Constant.MSG_FLAG_REGISTER == msgFlag) {//注册

                    var entity = UserDaoImpl().userRegist(
                        msgEntity?.let { it.entityObject as UserEntity }!!
                    )
                    pushMsg(oos!!, Constant.MSG_FLAG_REGISTER, entity as Object)


                } else if (Constant.MSG_FLAG_SELECTUSERBYID == msgFlag) {//TODO 通过id查询用户/获取自己的个人信息
                    var select_id = msgEntity?.let { it.entityObject as Long }
                    var result = UserDaoImpl().selectUserInfoById(arrayOf(select_id!!))
                    println("查询结果：${((result.entity)as ArrayList<UserEntity>)[0].uid}")
//                    result.entity as MutableList<UserEntity>
                    if( (result.entity as MutableList<UserEntity>).size>0)
                    pushMsg(oos!!,Constant.MSG_FLAG_SELECTUSERBYID,((result.entity)as ArrayList<UserEntity>)[0] as Object)
                    else{
                        var userEntity = UserEntity()
                        userEntity.uid = -1
                        pushMsg(oos!!,Constant.MSG_FLAG_SELECTUSERBYID,userEntity as Object)
                    }


                } else if(Constant.MSG_FLAG_SELECTUSERBYNAME == msgFlag){//TODO 通过名字查询用户
                    var select_name = msgEntity?.let { it.entityObject as String }
                    var result = UserDaoImpl().selectUserInfoByName(select_name!!)
                    println("查询结果：${result.arrayFlag}")
                    //TODO push
                }else if(Constant.MSG_FLAG_SELECTALLFRIEND == msgFlag){//TODO 查询所有的好友(暂时废弃）
//                    var my_id = msgEntity?.let { it.entityObject as Long }
//                    var result = UserDaoImpl().selectAllFriend(my_id!!)
//                    println("查询结果：${result.arrayFlag}")
                    //TODO push
                }else if(Constant.MSG_FLAG_CHANGEINFO == msgFlag){//TODO 变更用户信息
                    var entity = msgEntity?.let { it.entityObject as UserEntity }
                    var result = UserDaoImpl().userInfoChange(entity!!)
                    println("修改结果:${result}")
                    pushMsg(
                        socket!!,
                        entity as Object,
                        result as Object,
                        Constant.MSG_FLAG_CHANGEINFO,
                        oos!!
                    )
                    //TODO push
                }else if(Constant.MSG_FLAG_USERDELETE == msgFlag){//TODO  删除用户（非生产）
                    var id = msgEntity?.let { it.entityObject as Long}
                    var result = UserDaoImpl().deleteUserById(id!!)
                    println("删除结果:${result}")
                    //TODO push
                }else if(Constant.MSG_FLAG_ADDUSER == msgFlag){
                    var notificationEntity = msgEntity!!.entityObject as UserNotificationEntity
                    var relationResult = UserDaoImpl().setRelation(notificationEntity.uid_from,notificationEntity.uid_to,1,0)
                    var notificaionResult =  UserDaoImpl().setNotification(notificationEntity.time!!,Constant.FLAG_NOTIFICATION_NOT_READ,notificationEntity.uid_from,notificationEntity.uid_to,Constant.FLAG_NOTIFICATION_ADDUSER)
                    if(notificaionResult!=0&&relationResult!=0){
                        NotificationSocketMap.get().notificationMap[notificationEntity.uid_to] = true
                        println("用户${notificationEntity.uid_to}:${NotificationSocketMap.get().notificationMap[notificationEntity.uid_to]}")
                        pushMsg(socket!!,msgEntity as Object,Constant.SEARCH_FLAG_HAVE as Object,Constant.MSG_FLAG_ADDUSER,oos!!)

                    }else{
                        pushMsg(socket!!,msgEntity as Object,Constant.SEARCH_FLAG_NO_HAVE as Object,Constant.MSG_FLAG_ADDUSER,oos!!)

                    }
                }else if(Constant.MSG_FLAG_ACCEPTORREFUSE_FRIEND == msgFlag){
                    var acceptOrRefuceEntity = msgEntity!!.entityObject as AcceptOrRefuceEntity
                   var result =  UserDaoImpl().AcceptOrRefuce(acceptOrRefuceEntity.flag,acceptOrRefuceEntity.uid,acceptOrRefuceEntity.uid_friend,acceptOrRefuceEntity.notificationId!!)

                    if(result==1){
                       NotificationSocketMap.get().notificationMap[acceptOrRefuceEntity.uid] = true
                       NotificationSocketMap.get().notificationMap[acceptOrRefuceEntity.uid_friend] = true
                   }

                    pushMsg(socket!!,msgEntity as Object,Constant.SEARCH_FLAG_HAVE as Object,Constant.MSG_FLAG_ACCEPTORREFUSE_FRIEND,oos!!)

                }else if(Constant.MSG_FLAG_DELETE_FRIEND==msgFlag){
                    var chatContentEntity = msgEntity!!.entityObject as ChatContentEntity
                    var result =  UserDaoImpl().DeleteFriend(chatContentEntity.friendId,chatContentEntity.chatContentList[0].sender)

                    if(result==1){
                        NotificationSocketMap.get().notificationMap[chatContentEntity.friendId] = true
                        NotificationSocketMap.get().notificationMap[chatContentEntity.chatContentList[0].sender] = true
                    }

                    oos!!.writeUTF("OK")
                    oos!!.flush()
                    oos!!.close()

                }else if(Constant.MSG_FLAG_SEND_CHAT == msgFlag){

                    var chatContentEntity = msgEntity!!.entityObject as ChatContentEntity

                    var haveFriend = false
                    for(item in (NotificationSocketMap.get().chatContentMap[chatContentEntity.friendId])as MutableList<ChatContentEntity>){
                        if(item.friendId == chatContentEntity.chatContentList[0].sender){
                            item.chatContentList.add(chatContentEntity.chatContentList[0])
                            haveFriend = true
                            break
                        }
                    }
                    if(!haveFriend){
                        var friend = ChatContentEntity()
                        friend.friendId = chatContentEntity.chatContentList[0].sender
                        friend.chatContentList.add(chatContentEntity.chatContentList[0])
                        (NotificationSocketMap.get().chatContentMap[chatContentEntity.friendId]as MutableList<ChatContentEntity>).add(friend)
                    }


                    var myHave = false
                    for(item in (NotificationSocketMap.get().chatContentMap[chatContentEntity.chatContentList[0].sender])as MutableList<ChatContentEntity>){
                        if(item.friendId == chatContentEntity.friendId){
                            item.chatContentList.add(chatContentEntity.chatContentList[0])
                            myHave = true
                            break
                        }
                    }
                    if(!myHave){
                        (NotificationSocketMap.get().chatContentMap[chatContentEntity.chatContentList[0].sender]as MutableList<ChatContentEntity>).add(chatContentEntity)
                    }

                    NotificationSocketMap.get().notificationMap[chatContentEntity.chatContentList[0].sender] = true
                    NotificationSocketMap.get().notificationMap[chatContentEntity.friendId] = true
                    oos!!.writeUTF("OK")
                    oos!!.flush()
                    oos!!.close()

                }else if(Constant.MSG_FLAG_CLEAR_CHAT == msgFlag){
                    var chatContentEntity = msgEntity!!.entityObject as ChatContentEntity

                    var haveFriend = false
                    for(item in (NotificationSocketMap.get().chatContentMap[chatContentEntity.friendId])as MutableList<ChatContentEntity>){
                        if(item.friendId == chatContentEntity.chatContentList[0].sender){
                            item.chatContentList.clear()
                            haveFriend = true
                            break
                        }
                    }
                    if(!haveFriend){
                        var friend = ChatContentEntity()
                        friend.friendId = chatContentEntity.chatContentList[0].sender
                        friend.chatContentList.clear()
                        (NotificationSocketMap.get().chatContentMap[chatContentEntity.friendId]as MutableList<ChatContentEntity>).add(friend)
                    }


                    var myHave = false
                    for(item in (NotificationSocketMap.get().chatContentMap[chatContentEntity.chatContentList[0].sender])as MutableList<ChatContentEntity>){
                        if(item.friendId == chatContentEntity.friendId){
                            item.chatContentList.clear()
                            myHave = true
                            break
                        }
                    }
                    if(!myHave){
                        (NotificationSocketMap.get().chatContentMap[chatContentEntity.chatContentList[0].sender]as MutableList<ChatContentEntity>).clear()
                    }

                    NotificationSocketMap.get().notificationMap[chatContentEntity.chatContentList[0].sender] = true
                    NotificationSocketMap.get().notificationMap[chatContentEntity.friendId] = true
                    oos!!.writeUTF("OK")
                    oos!!.flush()
                    oos!!.close()

                }
            } catch (e: NullPointerException) {
                println("序列化对象内容出错")
            }
        } else {
            println("socket通信异常，放弃线程")
        }


    }


}