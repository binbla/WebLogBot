package com.binbla.webLogBot;

import net.mamoe.mirai.utils.MiraiLogger;

import java.sql.*;

/**
 * >ClassName DBConnection.java
 * >Description 管理连接部分
 * >Author binbla
 * >Version 1.0.0
 * >CreateTime 2021-05-02  09:37
 */
public class DBConnection {
    //在new出这个类的对象的时候就必须与数据库建立
    static final MiraiLogger logger = WebLogBotMain.INSTANCE.getLogger();
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    Boolean alive;//连接存活状态
    DBConnection(){
        alive = tryConnect();
    }
    private boolean tryConnect() {
        // 验证和连接数据库
        if (connection == null) {
            logger.info("验证数据库账户和密码...");
            try {
                connection = JDBCUtils.INSTANCE.getConnection();
                logger.info("验证成功!");
            } catch (Exception e) {
                logger.info("验证失败");
                return false;
            }
        }
        if (statement == null) {
            logger.info("建立与数据库的连接...");
            try {
                statement = connection.createStatement();
                logger.info("连接成功");
            } catch (Exception e) {
                logger.info("连接失败");
                return false;
            }
        }
        return true;
    }
    public Boolean close(){
        try {
            if(resultSet!=null)
                resultSet.close();
            statement.close();
            connection.close();
            alive = false;
            return true;
        } catch (SQLException e) {
            logger.info("关闭数据库连接的时候 有什么出错了(ง •̀_•́)ง\n\n"+ e);
            return false;
        }
    }
    public Boolean addProcess(TableLog in){
        if(!alive)return false;
        try (PreparedStatement addStatement = connection.prepareStatement(
                new StringBuilder().append("INSERT INTO `")
                        .append(Config.INSTANCE.getTablePre())
                        .append("daily_log`")
                        .append("(`cid`,`uid`,`time`,`subject`,`content`)")
                        .append("VALUES")
                        .append("(?,?,?,?,?)")
                        .toString())) {
            addStatement.setObject(1,in.cid);
            addStatement.setObject(2,in.userID);
            addStatement.setObject(3,in.time);
            addStatement.setObject(4,in.subject);
            addStatement.setObject(5,in.content);
            return addStatement.executeUpdate()==1;
        }catch (Exception e){
            logger.info("添加进数据库的时候 有什么出错了(ง •̀_•́)ง\n\n"+ e);
            return false;
        }
    }
    public Boolean checkTableExist(){
        try(PreparedStatement checkStatusStatement = connection.prepareStatement(
                new StringBuilder().append("CREATE TABLE IF NOT EXISTS `")
                .append(Config.INSTANCE.getTablePre())
                .append("daily_log`")
                .append("(cid INT(10) AUTO_INCREMENT PRIMARY KEY NOT NULL,uid INT(10) NOT NULL,time INT(10) NOT NULL,subject nvarchar(20) NULL,content TEXT NULL)")
                .toString())) {
            return checkStatusStatement.executeUpdate()==0;
        }catch(Exception e){
            logger.info("检查表是否存在的时候 有什么出错了(ง •̀_•́)ง\n\n"+ e);
            return false;
        }
    }
}
