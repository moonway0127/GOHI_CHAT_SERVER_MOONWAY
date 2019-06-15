package com.moonway.gohi.server.entity

import java.io.Serializable


/**
 * @program: UserEntity
 *
 * @description: TB_USER表实体类
 *
 * @author: moonway
 *
 * @school: University of Science and Technology Liaoning
 *
 * @create: 2019-01-22 11:08
 **/
class UserEntity :Serializable {

    constructor()

    constructor(uid:Long, flag_online:Int){
        this.uid = uid
        this.flag_online = flag_online
    }

    constructor(
        pwd: String, name: String, sex: Int, avatar_img: String,
        school: String, born_year: Int?, born_month: Int, born_day: Int, phone: String,
        mail: String, description: String, address: String
    ){
        this.pwd = pwd
        this.name = name
        this.sex = sex
        this.avatar_img = avatar_img
        this.school = school
        this.born_month = born_month
        this.born_year = born_year
        this.born_day = born_day
        this.phone = phone
        this.mail = mail
        this.description = description
        this.address = address
    }



    constructor(
        pwd: String, name: String, sex: Int, avatar_img: String,
        school: String, born_year: Int?, born_month: Int, born_day: Int, phone: String,
        mail: String, description: String, address: String,uid:Long
    ){
        this.pwd = pwd
        this.name = name
        this.sex = sex
        this.avatar_img = avatar_img
        this.school = school
        this.born_month = born_month
        this.born_year = born_year
        this.born_day = born_day
        this.phone = phone
        this.mail = mail
        this.description = description
        this.address = address
        this.uid = uid
    }

    constructor(
        name: String, sex: Int, avatar_img: String,
        school: String, born_year: Int?, born_month: Int, born_day: Int, phone: String,
        mail: String, description: String, address: String,uid:Long
    ){
        this.name = name
        this.sex = sex
        this.avatar_img = avatar_img
        this.school = school
        this.born_month = born_month
        this.born_year = born_year
        this.born_day = born_day
        this.phone = phone
        this.mail = mail
        this.description = description
        this.address = address
        this.uid = uid
    }

    var uid: Long = -1
    var pwd: String? = null
    var name: String? = null
    var sex: Int? = null
    var avatar_img: String? = null
    var school: String? = null
    var born_year: Int? = null
    var born_month: Int? = null
    var born_day: Int? = null
    var phone: String? = null
    var mail: String? = null
    var description: String? = null
    var address: String? = null
    var vip: Int? = null
    var flag_online: Int? = null
    var friend:Long? = null

    override fun toString(): String {
        return  "${uid} ${name}  ${sex}  ${school}  ${born_year}  ${phone}  "
    }

}