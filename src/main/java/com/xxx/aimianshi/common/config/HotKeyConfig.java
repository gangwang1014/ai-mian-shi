package com.xxx.aimianshi.common.config;

import com.jd.platform.hotkey.client.ClientStarter;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "hotkey")
@Data
public class HotKeyConfig {

    /**
     * Etcd 服务器完整地址
     */
    private String etcdServer;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 本地缓存最大数量
     */
    private int caffeineSize;

    /**
     * 批量推送 key 的间隔时间
     */
    private long pushPeriod;

    /**
     * 初始化 hotkey
     */
    @PostConstruct
    public void initHotkey() {
        ClientStarter.Builder builder = new ClientStarter.Builder();
        ClientStarter starter = builder.setAppName(appName)
                .setCaffeineSize(caffeineSize)
                .setPushPeriod(pushPeriod)
                .setEtcdServer(etcdServer)
                .build();
        starter.startPipeline();
    }
}
