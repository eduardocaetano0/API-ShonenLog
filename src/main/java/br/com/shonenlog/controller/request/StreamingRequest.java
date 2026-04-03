package br.com.shonenlog.controller.request;

import lombok.Builder;

@Builder
public record StreamingRequest(String name) {
}
