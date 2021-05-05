package com.binbla.webLogBot

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

/**
 * >ClassName TypechoBotConfig.java
 * >Description TODO
 * >Author binbla
 * >Version 1.0.0
 * >CreateTime 2021-05-02  08:25
 */
object Config : AutoSavePluginConfig("Config") {
    @ValueDescription("机器人所有者")
    var owner: Long by value(905908099L)

    @ValueDescription("机器人ID")
    var botID: MutableList<Long> by value(mutableListOf(1449427875L, 725541084L))

    @ValueDescription("用户群体")
    var userList:MutableList<Long> by value(mutableListOf(905908099L))

    @ValueDescription("数据库地址")
    var mysqlAddress:String by value("localhost")

    @ValueDescription("数据库端口")
    var mysqlPort:Int by value(3306)

    var databaseName:String by value("test")

    @ValueDescription("表单前缀")
    var tablePre:String by value("typecho_")

    @ValueDescription("数据库用户")
    var userName:String by value("test")

    @ValueDescription("数据库用户密码")
    var userPasswd:String by value("test")

    @ValueDescription("图片保存地址")
    var imagePath:String by value("/www/admin/log.binbla.com_80/wwwroot/logimages/")
    //val imagePath:String by value("/home/bla/")

    @ValueDescription("Command")
    var command_ADD:MutableList<String> by value(mutableListOf("发表","add"))
    var command_CHECK:MutableList<String> by value(mutableListOf("check","查看"))
}
