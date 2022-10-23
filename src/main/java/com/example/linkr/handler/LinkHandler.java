package com.example.linkr.handler;

import com.example.linkr.dto.LinkDto;
import com.example.linkr.dto.ResponseObject;
import com.example.linkr.exception.HttpBadRequestException;
import com.example.linkr.exception.HttpException;
import com.example.linkr.repository.LinkRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class LinkHandler {

    private final LinkRepository linkRepository;
    private final String host;
    private final UrlValidator urlValidator;

    public LinkHandler(LinkRepository linkRepository, @Value("${app.host}") String host, UrlValidator urlValidator) {
        this.linkRepository = linkRepository;
        this.host = host;
        this.urlValidator = urlValidator;
    }

    public Mono<ServerResponse> generateLink(ServerRequest req) {
        var generatedString = RandomStringUtils.randomAlphanumeric(6);
        var linkDtoMono = req.bodyToMono(LinkDto.class);

        return linkDtoMono.flatMap(linkDto -> {
                    var link = linkDto.link();
                    if (link == null) {
                        return Mono.error(new HttpBadRequestException("Invalid request: link is empty"));
                    }

                    if (!link.startsWith("https://") || !link.startsWith("http://")) {
                        link = Strings.concat("https://", link);
                    }

                    if (!urlValidator.isValid(link)) {
                        return Mono.error(new HttpBadRequestException("Invalid URL"));
                    }

                    return linkRepository.putLink(generatedString, link)
                            .flatMap(isAbsent -> isAbsent ? ServerResponse.ok()
                                    .body(Mono.just(host + '/' + generatedString), String.class)
                                    : ServerResponse.badRequest().bodyValue(new ResponseObject<>(HttpStatus.BAD_REQUEST.value(), "Url exists, try again")));
                })
                .onErrorResume(ex -> {
                    HttpStatus status;

                    if (ex instanceof HttpException e){
                        status = e.getStatus();
                        return ServerResponse.status(status).bodyValue(new ResponseObject<>(status.value(), e.getMessage()));
                    }

                    ex.printStackTrace();
                    status = HttpStatus.INTERNAL_SERVER_ERROR;
                    return ServerResponse.status(status).bodyValue(new ResponseObject<>(status.value(), "Server error"));
                });
    }

    public Mono<ServerResponse> getLink(ServerRequest req) {
        var id = req.pathVariable("id");
        return linkRepository.getLink(id)
                .flatMap(link -> ServerResponse.temporaryRedirect(URI.create(link)).build())
                .switchIfEmpty(
                        ServerResponse
                                .status(HttpStatus.NOT_FOUND)
                                .bodyValue(new ResponseObject<>(HttpStatus.NOT_FOUND.value(), String.format("Link /%s not found", id)))
                );
    }
}
