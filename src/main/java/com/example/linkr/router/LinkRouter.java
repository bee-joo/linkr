package com.example.linkr.router;

import com.example.linkr.handler.LinkHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class LinkRouter {

    @Bean
    public RouterFunction<ServerResponse> linkRouterFunction(LinkHandler linkHandler) {
        return route(GET("/{id}"), linkHandler::getLink)
                .andRoute(POST(""), linkHandler::generateLink);
    }
}
