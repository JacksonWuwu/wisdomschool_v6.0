package cn.wstom.storage;

import cn.wstom.storage.boot.ConsoleRunner;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <h2>入口类（启动类）</h2>
 * <p>该类为程序主类，内部的main方法为程序的唯一入口。</p>
 *
 * @author dws
 * @version 1.0
 */

public class StorageMain {
    /**
     * <h2>主方法</h2>
     * <p>该方法为入口位置，负责接收命令传参并以不同模式启动。</p>
     *
     * @param args String[] 控制台传入参数
     * @return void
     */
    public static void main(final String[] args) {
        //以控制台模式启动。
        ConsoleRunner.build();
    }
}
