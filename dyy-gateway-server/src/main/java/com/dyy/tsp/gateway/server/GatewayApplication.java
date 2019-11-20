package com.dyy.tsp.gateway.server;
import com.dyy.tsp.autoconfig.kafka.KafkaProperties;
import com.dyy.tsp.gateway.server.config.EvGBProperties;
import com.dyy.tsp.gateway.server.netty.EvGBServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * created by dyy
 */
@SuppressWarnings("all")
@EnableConfigurationProperties(value = {EvGBProperties.class, KafkaProperties.class})
@SpringBootApplication
public class GatewayApplication {

    @Autowired
    private EvGBProperties evGBProperties;

    @Bean
    public EvGBServer server() {
        EvGBServer server = new EvGBServer();
        server.setPort(evGBProperties.getPort());
        server.setTimeout(evGBProperties.getTimeout());
        server.setSoBackLog(evGBProperties.getSoBackLog());
        return server;
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(GatewayApplication.class).web(WebApplicationType.NONE).run(args);
    }
}

