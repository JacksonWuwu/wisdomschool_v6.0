package cn.wstom.student.core;

import java.util.UUID;

public class Uuid {
    public String UId() {

        //注意replaceAll前面的是正则表达式
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }
}
