package com.example.linkr.dto;

public record ResponseObject<T>(int statusCode, T message) {
}
