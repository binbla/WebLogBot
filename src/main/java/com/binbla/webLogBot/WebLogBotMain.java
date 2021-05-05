package com.binbla.webLogBot;

import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

public final class WebLogBotMain extends JavaPlugin {
    public static final WebLogBotMain INSTANCE = new WebLogBotMain();

    private WebLogBotMain() {
        super(new JvmPluginDescriptionBuilder("com.binbla.webLogBot.webLogBot", "1.0-SNAPSHOT")
                .name("WebLogBot")
                .author("binbla")
                .build());
    }

    @Override
    public void onEnable() {
        DBConnection check = new DBConnection();
        if(check.checkTableExist())getLogger().info("已确认表！");
        if(check.close())getLogger().info("已断开");
        reloadPluginConfig(Config.INSTANCE);
        GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessageEvent.class, this::handleFMsg);
    }
    void handleFMsg(MessageEvent fMsg){
        getLogger().info(fMsg.getMessage().contentToString());
        if(checkUser(fMsg)) new HandleThread(fMsg);
    }
    Boolean checkUser(MessageEvent msg){
        return Config.INSTANCE.getUserList().contains(msg.getSender().getId());
    }
}
