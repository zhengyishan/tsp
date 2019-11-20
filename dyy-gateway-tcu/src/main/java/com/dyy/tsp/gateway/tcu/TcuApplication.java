package com.dyy.tsp.gateway.tcu;

import com.dyy.tsp.autoconfig.kafka.KafkaProperties;
import com.dyy.tsp.gateway.tcu.config.TcuProperties;
import com.dyy.tsp.gateway.tcu.netty.TcuClient;
import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * TCU模拟仿真终端
 * created by dyy
 */
@SuppressWarnings("all")
@EnableConfigurationProperties(value = {TcuProperties.class, KafkaProperties.class})
@SpringBootApplication
@EnableSwagger2
public class TcuApplication {

    @Autowired
    private TcuProperties tcuProperties;

    @Bean
    public TcuClient client() {
        TcuClient client = new TcuClient(tcuProperties.getGatewayHost(),tcuProperties.getGatewayPort());
        client.setTimeout(tcuProperties.getTimeout());
        client.setConnectMaxNum(tcuProperties.getReconnectMaxNum());
        return client;
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(TcuApplication.class).web(WebApplicationType.SERVLET).run(args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .paths(Predicates.not(PathSelectors.regex("/actuator.*")))
                .paths(PathSelectors.regex("/.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("TCU仿真模拟平台")
                .contact(new Contact("NB", "", "15000814726@163.com"))
                .description("TCU仿真模拟平台")
                .termsOfServiceUrl("http://www.baidu.com/")
                .license("TCU仿真模拟平台")
                .licenseUrl("http://www.baidu.com/")
                .version("v1.0")
                .build();
    }
}

