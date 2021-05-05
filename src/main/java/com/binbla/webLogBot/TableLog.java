package com.binbla.webLogBot;

import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.Image;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Date;
import javax.imageio.ImageIO;
/**
 * >ClassName TableLog.java
 * >Description TODO
 * >Author binbla
 * >Version 1.0.0
 * >CreateTime 2021-05-04  14:58
 */
public class TableLog {
    Function function;
    Long cid=null;
    Long userID;
    Long time;
    String subject;
    String content;
    BufferedImage image;
    TableLog(MessageEvent msg){
        String[] target = msg.getMessage().contentToString().split("[\\s]", 3);
        this.function = choseFunction(target[0]);
        this.userID = msg.getSender().getId();
        this.time = new Date().getTime()/1000;
        this.subject = target[1];
        this.content = target[2];
        saveImage(msg);
    }
    private Boolean saveImage(MessageEvent msg){
        Image miraiImage = (Image) msg.getMessage().stream().filter(Image.class::isInstance).findFirst().orElse(null);
        //获取第一张图片或是null
        if(miraiImage == null)return false;
        String imageUrl = Image.queryUrl(miraiImage);
        try {
            URL url = new URL(imageUrl);
            image = ImageIO.read(url);
            String path = ""+Config.INSTANCE.getImagePath()+this.time+".jpg";
            WebLogBotMain.INSTANCE.getLogger().info("图片已保存在："+path);
            File out = new File(path);
            OutputStream imageFile = new FileOutputStream(out);
            ImageIO.write(image, "jpg", imageFile);
            imageFile.close();
        } catch (Exception e) {
            return false;
        }return true;
    }
    private Function choseFunction(String command) {
        if (Config.INSTANCE.getCommand_ADD().contains(command)) return Function.ADD;
        if (Config.INSTANCE.getCommand_CHECK().contains(command)) return Function.CHECK;
        return Function.UNKNOWN;
    }
}
