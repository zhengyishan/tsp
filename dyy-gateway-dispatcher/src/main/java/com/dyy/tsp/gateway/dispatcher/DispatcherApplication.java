package com.dyy.tsp.gateway.dispatcher;

import com.dyy.tsp.autoconfig.kafka.KafkaProperties;
import com.dyy.tsp.gateway.dispatcher.config.DispatcherProperties;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * created by dyy
 */
@SuppressWarnings("all")
@EnableConfigurationProperties(value = {DispatcherProperties.class, KafkaProperties.class})
@SpringBootApplication
public class DispatcherApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DispatcherApplication.class).web(WebApplicationType.NONE).run(args);
    }
}

