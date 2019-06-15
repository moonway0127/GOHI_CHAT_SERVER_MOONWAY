package com.moonway.gohi.server.dao

import com.moonway.gohi.server.constant.Constant
import com.moonway.gohi.server.entity.RegisterEntity
import com.moonway.gohi.server.entity.SearchDateEntity
import com.moonway.gohi.server.entity.UserEntity
import com.moonway.gohi.server.entity.UserNotificationEntity
import com.moonway.gohi.server.tools.DBConnection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.*
import kotlin.collections.ArrayList


/**
 * @program: userDaoImpl
 *
 * @description:
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-01-21 13:05
 **/


class UserDaoImpl : DBConnection() {
    companion object {
        const val TAG = "userDao"
        const val SQL_LOGIN: String = "select * from TB_USER where uid = ? and pwd = ?"
        const val SQL_USERINFO_BY_NAME: String = "select * from TB_USER where name = ?"
        const val SQL_USERINFO_DELETE: String = "delete from TB_USER where uid = ?"
        const val SQL_ALL_FRIEND_BY_ID: String =
            "select TB_USER.* FROM TB_USER WHERE TB_USER.uid IN (SELECT friend_user FROM TB_RELATION WHERE host_user=? AND host_accept = 1 AND friend_accept = 1) OR uid in (SELECT host_user FROM TB_RELATION WHERE friend_user=? AND host_accept = 1 AND friend_accept = 1)"
        const val E_NULL = "${TAG}:PreparedStatement,null"
        const val SQL_CHANGE_HEART: String = "update TB_USER set flag_online = ? where uid = ?"
        const val SQL_SET_NOTIFICATION =
            "INSERT into TB_NOTIFICATION set ntid = ? ,time = ?,flag_read =?,level = ? ,uid_from = ?,uid_to = ?"
        const val SQL_SELECT_NOTIFICATION =
            "select  * from TB_NOTIFICATION where uid_to = ? and flag_read = ${Constant.FLAG_NOTIFICATION_NOT_READ}"
        const val SQL_SET_RELATION: String =
            "insert into TB_RELATION SET host_user = ?,friend_user =?,host_accept =?,friend_accept = ?,location_chat_cos = ?"
        const val SQL_ACCEPT_FRIEND: String =
            "update TB_NOTIFICATION  set flag_read = ${Constant.FLAG_NOTIFICATION_READED} where ntid = ?"

        const val SQL_ACCEPT_FRIEND_RELATION =
            "update TB_RELATION SET host_accept = 1,friend_accept = 1 where (host_user = ? and friend_user =?)||(friend_user = ? and host_user =?)"
        const val SQL_DELETE_FRIEND_RELATION =
            "delete from TB_RELATION where (host_user = ? and friend_user =?)||(friend_user = ? and host_user =?)"




    }


    var pstmt: PreparedStatement? = null


    /**
     * @Author moonway
     * @Description
     * @Date 08:53 2019-01-23
     * @Param  用户登录
     * @return
     **/

    fun userLogin(uid: Long, password: String): Int {
        try {
            pstmt = conn!!.prepareStatement(SQL_LOGIN)
            pstmt!!.setLong(1, uid)
            pstmt!!.setString(2, password)
            var result: ResultSet = pstmt!!.executeQuery()
            while (result.next()) {
                println(result.getString("uid") + "登录成功")
                return Constant.LOGIN_FLAG_SUCCESS
            }
            println("登录失败")
        } catch (e: Exception) {
            println(e.toString())
            println("${TAG}:login error")
            return Constant.LOGIN_FLAG_ERROR
        } finally {
            closeConnect()
        }
        return Constant.LOGIN_FLAG_ERROR
    }


    /***
     * @Author moonway
     * @Description
     * @Date 15:54 2019-02-27
     * @Param 更改在状态
     * @return
     **/

    fun changeOnlineFlag(uid: Long, flag: Int): Int {
        var result = 0
        try {
            pstmt = conn!!.prepareStatement(SQL_CHANGE_HEART)
            pstmt!!.setInt(1, flag)
            pstmt!!.setLong(2, uid)
            result = pstmt!!.executeUpdate()

        } catch (e: Exception) {

        } finally {
            closeConnect()
        }

        return result
    }


    /**
     * @Author moonway
     * @Description //TODO moonway
     * @Date 07:42 2019-03-17
     * @Param 查询通知表
     * @return
     **/


    fun selectNotification(uid: Long): MutableList<UserNotificationEntity> {
        var userNotificationList: MutableList<UserNotificationEntity> = ArrayList()
        try {
            pstmt = conn!!.prepareStatement(SQL_SELECT_NOTIFICATION)
            pstmt!!.setLong(1, uid)
            var result: ResultSet = pstmt!!.executeQuery()
            while (result.next()) {
                var userNotification = UserNotificationEntity()
                userNotification.ntid = result.getString("ntid")
                userNotification.flag_read = result.getInt("flag_read")
                userNotification.level = result.getInt("level")
                userNotification.time = result.getString("time")
                userNotification.uid_from = result.getLong("uid_from")
                userNotification.uid_to = result.getLong("uid_to")
                userNotificationList.add(userNotification)
            }

        } catch (e: Exception) {
            println(e.toString())
            println("${TAG}:login error")

        } finally {
            closeConnect()
        }
        return userNotificationList
    }


    /***
     * @Author moonway
     * @Description //TODO moonway
     * @Date 07:46 2019-03-17
     * @Param 插入通知
     * @return
     **/

    fun setNotification(time: String, flag_Read: Int, user_From: Long, user_To: Long, level: Int): Int {
        var result = 0
        try {
            pstmt = conn!!.prepareStatement(SQL_SET_NOTIFICATION)
            pstmt!!.setString(1, "$user_From-$user_To-$time")
            pstmt!!.setString(2, time)
            pstmt!!.setInt(3, Constant.FLAG_NOTIFICATION_NOT_READ)
            pstmt!!.setInt(4, level)
            pstmt!!.setLong(5, user_From)
            pstmt!!.setLong(6, user_To)

            result = pstmt!!.executeUpdate()

        } catch (e: Exception) {
            println(e)
        } finally {
            closeConnect()
        }

        return result
    }


    /**
     * @Author moonway
     * @Description //TODO moonway
     * @Date 18:30 2019-03-17
     * @Param 添加好友
     * @return
     **/

    fun setRelation(host_uid: Long, friend_uid: Long, host_accept: Int, friend_accpet: Int): Int {
        var result = 0
        try {
            pstmt = conn!!.prepareStatement(SQL_SET_RELATION)
            pstmt!!.setLong(1, host_uid)
            pstmt!!.setLong(2, friend_uid)
            pstmt!!.setInt(3, host_accept)
            pstmt!!.setInt(4, friend_accpet)
            pstmt!!.setString(5, "$host_uid$friend_uid")
            result = pstmt!!.executeUpdate()

        } catch (e: Exception) {
            println(e)
        } finally {
            closeConnect()
        }

        return result
    }


    /**
     * @Author moonway
     * @Description
     * @Date 07:44 2019-03-18
     * @Param  好友接受或拒绝
     * @return
     **/

    fun AcceptOrRefuce(flag: Int, uid: Long, uid_Friend: Long, notificationId: String): Int {
        var result = 0
        try {
            if (flag == 1) {
                pstmt = conn!!.prepareStatement(SQL_ACCEPT_FRIEND_RELATION)
                pstmt!!.setLong(1, uid)
                pstmt!!.setLong(2, uid_Friend)
                pstmt!!.setLong(3, uid)
                pstmt!!.setLong(4, uid_Friend)
                result = pstmt!!.executeUpdate()
            } else {
                pstmt = conn!!.prepareStatement(SQL_DELETE_FRIEND_RELATION)
                pstmt!!.setLong(1, uid)
                pstmt!!.setLong(2, uid_Friend)
                pstmt!!.setLong(3, uid)
                pstmt!!.setLong(4, uid_Friend)
                result = pstmt!!.executeUpdate()
            }


            if (result != 0) {
                pstmt = conn!!.prepareStatement(SQL_ACCEPT_FRIEND)
                pstmt!!.setString(1, notificationId)
                result = pstmt!!.executeUpdate()
            }
        } catch (e: Exception) {
            println(e)
        } finally {
            closeConnect()
        }
        return result
    }


    /**
     * @Author moonway
     * @Description
     * @Date 07:44 2019-03-18
     * @Param  好友删除
     * @return
     **/

    fun DeleteFriend(uid: Long, uid_Friend: Long): Int {
        var result = 0
        try {


            pstmt = conn!!.prepareStatement(SQL_DELETE_FRIEND_RELATION)
            pstmt!!.setLong(1, uid)
            pstmt!!.setLong(2, uid_Friend)
            pstmt!!.setLong(3, uid)
            pstmt!!.setLong(4, uid_Friend)
            result = pstmt!!.executeUpdate()
        } catch (e: Exception) {
            println(e)
        } finally {
            closeConnect()
        }
        return result
    }



    /**
     * @Author moonway
     * @Description
     * @Date 08:54 2019-01-23
     * @Param 用户注册
     * @return
     **/

    fun userRegist(userInfo: UserEntity): RegisterEntity {
        try {
            var sql =
                "INSERT into TB_USER set name = \"${userInfo.name}\" " +
                        ", pwd = \"${userInfo.pwd}\" , sex = \"${userInfo.sex}\" " +
                        ", avatar_img = \"${if (userInfo.avatar_img == null) "default \"" else userInfo.avatar_img + "\""}" +
                        "${userInfo.school?.let { " , school = \"" + userInfo.school + "\"" }}" +
                        "${userInfo.born_year?.let { " , born_year = \"" + userInfo.born_year + "\"" }}" +
                        "${userInfo.born_month?.let { " , born_month = \"" + userInfo.born_month + "\"" }}" +
                        "${userInfo.born_day?.let { " , born_day = \"" + userInfo.born_day + "\"" }}" +
                        "${userInfo.phone?.let { " , phone = \"" + userInfo.phone + "\"" }}" +
                        "${userInfo.mail?.let { " , mail = \"" + userInfo.mail + "\"" }}" +
                        "${userInfo.description?.let { " , description = \"" + userInfo.description + "\"" }}" +
                        "${userInfo.address?.let { " , address = \"" + userInfo.address + "\"" }}"

            pstmt = conn!!.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            var tempFlag: Int = pstmt!!.executeUpdate()
            if (tempFlag != null && tempFlag > 0) {
                var rs: ResultSet = pstmt!!.generatedKeys
                if (rs.next()) {
//                    println("注册成功，您的id为 ${rs.getInt(1)},请牢记")
                    return RegisterEntity(Constant.REGISTER_FLAG_SUCCESS, rs.getLong(1))
                }
            }

        } catch (e: Exception) {
            println(e.toString())
        } finally {
            closeConnect()
        }
        return RegisterEntity(Constant.REGISTER_FLAG_ERROR)
    }


    /**
     * @Author moonway
     * @Description
     * @Date 08:54 2019-01-23
     * @Param 通过id查询用户信息
     * @return
     **/

    fun selectUserInfoById(uids: Array<Long>): SearchDateEntity {
        var searchDate = SearchDateEntity(Constant.SEARCH_FLAG_NO_HAVE)

        try {
            var temp: String? = null
            for (a in uids) {
                if (null == temp) {
                    temp = a.toString()
                } else {
                    temp = "${temp},${a}"
                }
            }
            println(temp)
            pstmt = conn!!.prepareStatement("select * from TB_USER where uid in (${temp})")

            var rs: ResultSet = pstmt!!.executeQuery()
            searchDate = searchDateManager(rs)
        } catch (e: Exception) {
            println(e)
            println("${TAG}:userInfo get Error")
        } finally {
            closeConnect()
        }
        return searchDate
    }

    /***
     * @Author moonway
     * @Description
     * @Date 12:39 2019-01-23
     * @Param 通过name查询用户信息
     * @return
     **/

    fun selectUserInfoByName(name: String): SearchDateEntity {
        var searchDate = SearchDateEntity(Constant.SEARCH_FLAG_NO_HAVE)
        try {
            pstmt = conn!!.prepareStatement(SQL_USERINFO_BY_NAME)
            pstmt!!.setString(1, name)
            var rs: ResultSet = pstmt!!.executeQuery()
            searchDate = searchDateManager(rs)
        } catch (e: Exception) {
            println(e)
            println("${TAG}:userInfo get Error")
        } finally {
            closeConnect()
        }
        return searchDate
    }


    /**
     * @Author moonway
     * @Description //TODO moonway
     * @Date 11:16 2019-01-23
     * @Param 个人信息修改
     * @return
     **/

    fun userInfoChange(userInfo: UserEntity): Int {
        try {
            var sql =
                "update TB_USER set name = \"${userInfo.name}\"  " +
                        ", sex = \"${userInfo.sex}\" " +
                        ", avatar_img = \"${if (userInfo.avatar_img == null) "default \"" else userInfo.avatar_img + "\""}" +
                        "${userInfo.school?.let { " , school = \"" + userInfo.school + "\"" }}" +
                        "${userInfo.born_year?.let { " , born_year = \"" + userInfo.born_year + "\"" }}" +
                        "${userInfo.born_month?.let { " , born_month = \"" + userInfo.born_month + "\"" }}" +
                        "${userInfo.born_day?.let { " , born_day = \"" + userInfo.born_day + "\"" }}" +
                        "${userInfo.phone?.let { " , phone = \"" + userInfo.phone + "\"" }}" +
                        "${userInfo.mail?.let { " , mail = \"" + userInfo.mail + "\"" }}" +
                        "${userInfo.description?.let { " , description = \"" + userInfo.description + "\"" }}" +
                        "${userInfo.address?.let { " , address = \"" + userInfo.address + "\"" }} " +
                        "where uid = ${userInfo.uid}"
            println(sql)
            pstmt = conn!!.prepareStatement(sql)
            if (pstmt!!.executeUpdate() >= 1) return Constant.SEARCH_FLAG_HAVE else println("change error")

        } catch (e: Exception) {
            println(e.toString())
        } finally {
            closeConnect()
        }
        return Constant.SEARCH_FLAG_NO_HAVE
    }


    /**
     * @Author moonway
     * @Description
     * @Date 13:44 2019-01-23
     * @Param 删除指定id用户(生产/测试版 此方法废弃，只更改可使用flag)
     * @return
     **/

    fun deleteUserById(uid: Long): Int {
        try {
            pstmt = conn!!.prepareStatement(SQL_USERINFO_DELETE)
            pstmt!!.setLong(1, uid)
            var tempFlag: Int = pstmt!!.executeUpdate()
            if (tempFlag > 0) {
                return Constant.SEARCH_FLAG_HAVE
            } else {
                println("未找到用户，删除失败")
            }
        } catch (e: Exception) {
            println(e.toString())
            println("删除失败")
        } finally {
            closeConnect()
        }

        return Constant.SEARCH_FLAG_NO_HAVE
    }


    /***
     * @Author moonway
     * @Description
     * @Date 12:39 2019-02-27
     * @Param 查询所有好友
     * @return
     **/
    fun selectAllFriend(uid: Long): MutableList<UserEntity> {
        var searchDate = SearchDateEntity(Constant.SEARCH_FLAG_NO_HAVE)
        try {
            pstmt = conn!!.prepareStatement(SQL_ALL_FRIEND_BY_ID)
            pstmt!!.setLong(1, uid)
            pstmt!!.setLong(2,uid)
            var rs: ResultSet = pstmt!!.executeQuery()
            searchDate = searchDateManager(rs)
        } catch (e: Exception) {
            println(e)
            println("${TAG}:userInfo get Error")
        } finally {
            closeConnect()
        }


        var freindList: MutableList<UserEntity> = ArrayList()
        if (searchDate.arrayFlag == Constant.SEARCH_FLAG_HAVE) {
            freindList = searchDate.entity as MutableList<UserEntity>
        }

        return freindList

    }


    /***
     * @Author moonway
     * @Description
     * @Date 11:47 2019-01-23
     * @Param  查询到的数据整理
     * @return
     **/

    @Throws(Exception::class)
    fun searchDateManager(rs: ResultSet): SearchDateEntity {

        var userInfoList: MutableList<UserEntity> = ArrayList()
        while (rs.next()) {
            var userInfo = UserEntity()
            try {
                rs.getString("pwd")?.let { userInfo.pwd = it }
                rs.getString("name")?.let { userInfo.name = it }
                rs.getInt("sex")?.let { userInfo.sex = it }
                rs.getString("avatar_img")?.let { userInfo.avatar_img = it }
                rs.getString("school")?.let { userInfo.school = it }
                rs.getInt("born_year")?.let { userInfo.born_year = it }
                rs.getInt("born_month")?.let { userInfo.born_month = it }
                rs.getInt("born_day")?.let { userInfo.born_day = it }
                rs.getString("phone")?.let { userInfo.phone = it }
                rs.getString("mail")?.let { userInfo.mail = it }
                rs.getString("description")?.let { userInfo.description = it }
                rs.getString("address")?.let { userInfo.address = it }
                rs.getInt("vip")?.let { userInfo.vip = it }
                rs.getInt("flag_online")?.let { userInfo.flag_online = it }
            } catch (e: SQLException) {
                println(e)
            }
            try {
                rs.getLong("uid")?.let { userInfo.uid = it }
            } catch (e: SQLException) {
            }
            try {
                rs.getLong("friend_user")?.let { userInfo.friend = it }
            } catch (e: SQLException) {
            }
            userInfoList.add(userInfo)
        }
        if (null != userInfoList && userInfoList.size != 0) {
            return SearchDateEntity(Constant.SEARCH_FLAG_HAVE, userInfoList as Object)
        } else {
        }
        return SearchDateEntity(Constant.SEARCH_FLAG_NO_HAVE)
    }
}





