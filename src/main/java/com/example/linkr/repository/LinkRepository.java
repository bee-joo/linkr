package com.example.linkr.repository;

import reactor.core.publisher.Mono;

public interface LinkRepository {
    Mono<Boolean> putLink(String id, String link);
    Mono<String> getLink(String id);
}
