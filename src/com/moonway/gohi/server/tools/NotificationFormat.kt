package com.moonway.gohi.server.tools

import com.moonway.gohi.server.constant.Constant
import com.moonway.gohi.server.entity.MsgEntity
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * @program: NotificationFormat
 *
 * @description: 消息格式化
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-02-08 19:55
 *
 *
 *
 *
 * @format:
 *
 * shell:<moonway-message-shell>***</moonway-message-shell>
 *
 * flag:<moonway-message-flag>...</moonway-message-flag>
 *
 * content:<moonway-message-content>***|...</moonway-message-content>
 *
 * list:<moonway-message-list>***</moonway-message-list>
 *
 * listSubHead:<moonway-list-sub-head>...</moonway-list-sub-head>
 *
 * listSubContent:<moonway-list-sub-content>***|...</moonway-list-sub-content>
 *
 * other:<moonway-list-sub-content-(Object...)>***|...</moonway-list-sub-content-(Object...)>
 **/


class NotificationFormat{

    companion object {
        //封印
        val SHELL_BEGIN:String = "<moonway-message-shell>"
        val SHELL_END:String = "</moonway-message-shell>\n"
        val MESSAGE_FLAG_BEGIN = "<moonway-message-flag>"
        val MESSAGE_FLAG_END = "</moonway-message-flag>"
        val MESSAGE_CONTENT_BEGIN = "<moonway-message-content>"
        val MESSAGE_CONTENT_END = "</moonway-message-content>"



        //封印解除

        val REG_RELEASE_SHELL = "<moonway-message-shell>(.*)</moonway-message-shell>"
        val REG_RELEASE_MESSAGE_FLAG = "<moonway-message-flag>(.*)</moonway-message-flag>"
        val REG_RELEASE_MESSAGE_CONTENT = "<moonway-message-content>(.*)</moonway-message-content>"



        /**
         * @Author moonway
         * @Description 封印成字符串
         * @Date 19:58 2019-02-08
         * @Param 
         * @return 
         **/
        
        fun SealString(msgFlag:Int,msgContent:Object):String{

            if(Constant.MSG_FLAG_LOGIN_AND_NOTIFICATION_LOGIN == msgFlag||Constant.MSG_FLAG_HEARTBEAT==msgFlag||Constant.MSG_FLAG_CHANGEINFO == msgFlag||Constant.MSG_FLAG_ADDUSER==msgFlag||Constant.MSG_FLAG_ACCEPTORREFUSE_FRIEND == msgFlag){//登录flag
                return "${SHELL_BEGIN}${MESSAGE_FLAG_BEGIN}${msgFlag}${MESSAGE_FLAG_END}${MESSAGE_CONTENT_BEGIN}${msgContent as Int}${MESSAGE_CONTENT_END}${SHELL_END}"
            }

            return ""
        }
        
        
        /**
         * @Author moonway
         * @Description 封印解除
         * @Date 20:01 2019-02-08
         * @Param 
         * @return 
         **/

        @Throws(Exception::class)
        fun ReleaseString(message:String):String{
            var p_shell:Pattern = Pattern.compile(REG_RELEASE_SHELL)
            var m_shell:Matcher = p_shell.matcher(message)
            var release_shell:String? = null

            while (m_shell.find())
                release_shell = m_shell.group(1)

            var p_message_flag = Pattern.compile(REG_RELEASE_MESSAGE_FLAG)
            var m_message_flag = p_message_flag.matcher(release_shell)
            var release_message_flag:String? = null
            while (m_message_flag.find()) {
                release_message_flag = m_message_flag.group(1)
                println(release_message_flag)
            }

            release_message_flag?.let {
                if(it.toInt()==Constant.MSG_FLAG_LOGIN_AND_NOTIFICATION_LOGIN){//登录验证
                    var p_login_content = Pattern.compile(REG_RELEASE_MESSAGE_CONTENT)
                    var m_login_content = p_login_content.matcher(release_shell)
                    var release_message_content:String? = null
                    while (m_login_content.find()){
                        release_message_content = m_login_content.group(1)
                        println(release_message_content)
                    }

                    release_message_content?.let {
                        return it
                    }!!

                }else if(it.toInt()==Constant.MSG_FLAG_HEARTBEAT){//心跳验证
                    var p_heart_content = Pattern.compile(REG_RELEASE_MESSAGE_CONTENT)
                    var m_heart_content = p_heart_content.matcher(release_shell)
                    var release_message_content:String? = null
                    while (m_heart_content.find()){
                        release_message_content = m_heart_content.group(1)
                        println(release_message_content)
                    }
                    release_message_content?.let {
                        return it
                    }
                }

            }!!


            return ""
        }
    }
}