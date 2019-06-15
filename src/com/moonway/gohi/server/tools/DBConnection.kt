package com.moonway.gohi.server.tools

import com.moonway.gohi.server.constant.Constant
import java.lang.NullPointerException
import java.sql.Connection
import java.sql.DriverManager


/**
 * @program: DBConnection
 *
 * @description: 数据库连接
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-01-18 10:13
 **/

open class DBConnection {

    init {
        createConnect()
        println("DB初始化")
    }

    open var conn: Connection? = null

       fun createConnect() {
            Class.forName("com.mysql.cj.jdbc.Driver")
            try {
                conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/DB_GOHI_CHAT_MOONWAY?useUnicode=true&characterEncoding=utf-8&useSSL=false", Constant
                        .MYSQL_USER_NAME, Constant.MYSQL_USER_PASSWPRD
                )
                conn!!.autoCommit = false
            } catch (e: Exception) {
                conn = null
                println(e.toString())
            }
        }

    fun closeConnect() {
        conn!!.commit()
        conn!!.close()
        println("DB连接关闭")
    }


    fun throwNull(about:String = " "){
        throw NullPointerException(about)
    }

}