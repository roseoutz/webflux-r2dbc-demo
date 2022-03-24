package com.example.config;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Slf4j
@Component
public class H2Config {

    @Value("${server.port:8080}")
    private int serverPort;

    private Server h2Server;

    @EventListener(ContextRefreshedEvent.class)
    public void start() throws SQLException {
        log.info("H2 Server Starting...");
        this.h2Server = Server.createWebServer("-webPort",serverPort + 1 + "", "-tcpAllowOthers");
        this.h2Server.start();
        log.info("H2 Server Ready!");
    }

    @EventListener(ContextClosedEvent.class)
    public void stop() {
        if (h2Server != null) {
            h2Server.stop();
            log.info("H2 Server Stopped...");
        }
    }


}
