package cn.wstom.exam.utils;

import java.util.UUID;

/**
 * UUID 工具
 */
public class Uuid {
    public String UId() {

        //注意replaceAll前面的是正则表达式
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }
}
