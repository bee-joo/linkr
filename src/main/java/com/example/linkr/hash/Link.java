package com.example.linkr.hash;

import org.springframework.data.redis.core.RedisHash;

// Just shows structure of redis string object - key and value
// @RedisHash
public record Link(String id, String link) {
}
