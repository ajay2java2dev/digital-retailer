package com.digital.retailer.services.impl.config;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static org.h2.tools.Server.createWebServer;

//THIS CLASS IS TO OVERRIDE H2 CONSOLE SINCE ITS NOT ENABLED IN DEFAULT WEBFLUX IMPLEMENTATION
//See: https://stackoverflow.com/questions/63646864/spring-boot-h2-console-returns-404

@Component
public class H2ConsoleConfig {
    Logger log = LoggerFactory.getLogger(H2ConsoleConfig.class);
    private Server webServer;

    @Value("${h2-server.port:9082}")
    Integer h2ConsolePort;

    @EventListener(ContextRefreshedEvent.class)
    public void start() throws java.sql.SQLException {
        log.info("starting h2 console at port "+ h2ConsolePort);
        this.webServer = createWebServer("-webPort", h2ConsolePort.toString(),
                "-tcpAllowOthers").start();
        System.out.println(webServer.getURL());
    }

    @EventListener(ContextClosedEvent.class)
    public void stop() {
        log.info("stopping h2 console at port "+h2ConsolePort);
        this.webServer.stop();
    }
}
