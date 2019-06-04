package com.mochu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Slf4j
@Component
public class Systems {

    @Value("${spring.application.name}")
    private static String appName;

    private static int port = 0;
    private static boolean isOnline = false;

    public static int port() {

        return port;
    }

    public static void init(ConfigurableEnvironment env) {

        Systems.currentHost();

        String portString = env.getProperty("server.port");
        if (portString != null) {
            port = Integer.valueOf(portString);

        }

        isOnline = Boolean.parseBoolean(env.getProperty("isOnline"));

        log.info("isOnline: " + isOnline);
        log.info("host: " + host);
        log.info("port: " + port);
    }

    public static boolean isOnline() {

        return isOnline;
    }

    private static String host = null;

    public static String currentHost() {
        if (host == null) {
            try {
                host = InetAddress.getLocalHost().getHostAddress();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return host;
    }

}
