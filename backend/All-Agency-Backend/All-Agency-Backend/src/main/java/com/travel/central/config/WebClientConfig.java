package com.travel.central.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfig {

    @Value("${agency.dashboard.url}")
    private String dashboardUrl;

    @Value("${agency.dashboard.connect-timeout-ms}")
    private int connectTimeout;

    @Bean
    public WebClient agencyDashboardClient(WebClient.Builder builder) {
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(10))
                .option(io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout);

        return builder
                .baseUrl(dashboardUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
