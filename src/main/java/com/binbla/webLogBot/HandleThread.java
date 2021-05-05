package com.binbla.webLogBot;

import net.mamoe.mirai.event.events.MessageEvent;

/**
 * >ClassName HandleThread.java
 * >Description TODO
 * >Author binbla
 * >Version 1.0.0
 * >CreateTime 2021-05-02  08:23
 */
public class HandleThread extends Thread {
    MessageEvent fMsg;

    HandleThread(MessageEvent fMsg) {
        this.fMsg = fMsg;
        this.start();
    }

    @Override
    public void run() {
        TableLog target = new TableLog(fMsg);
        switch (target.function) {
            case ADD:
                DBConnection dbConnection = new DBConnection();
                if(dbConnection.addProcess(target))
                    fMsg.getSender().sendMessage("添加记录成功！继续加油！");
                dbConnection.close();
                break;
            case CHECK:
                //TODO
                break;
            case UNKNOWN:
                //TODO
                fMsg.getSender().sendMessage("未知命令或命令不完整！");
                break;
        }
    }
}
