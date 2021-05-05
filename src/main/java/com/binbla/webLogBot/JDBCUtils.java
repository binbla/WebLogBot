package com.binbla.webLogBot;


import java.sql.*;

/**
 * >ClassName Database.java
 * >Description TODO
 * >Author binbla
 * >Version 1.0.0
 * >CreateTime 2021-05-02  08:18
 */

public final class JDBCUtils {
    public static final JDBCUtils INSTANCE = new JDBCUtils();
    String url,username,password;
    JDBCUtils(){
        url = "jdbc:mysql://"+
                Config.INSTANCE.getMysqlAddress()+
                ":"+Config.INSTANCE.getMysqlPort()+
                "/"+Config.INSTANCE.getDatabaseName()+
                "?useSSL=false&characterEncoding=utf8";
        username = Config.INSTANCE.getUserName();
        password = Config.INSTANCE.getUserPasswd();
    }
    // 获取连接对象
    public Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url,username,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
