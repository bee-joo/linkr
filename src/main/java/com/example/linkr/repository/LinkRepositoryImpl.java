package com.example.linkr.repository;

import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class LinkRepositoryImpl implements LinkRepository {

    private final ReactiveValueOperations<String, String> valueOps;

    public LinkRepositoryImpl(ReactiveStringRedisTemplate ops) {
        this.valueOps = ops.opsForValue();
    }

    @Override
    public Mono<Boolean> putLink(String id, String link) {
        return valueOps.setIfAbsent(id, link);
    }

    @Override
    public Mono<String> getLink(String id) {
        return valueOps.get(id);
    }
}
