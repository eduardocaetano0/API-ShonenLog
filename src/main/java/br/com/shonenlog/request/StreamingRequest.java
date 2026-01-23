package br.com.shonenlog.request;

import lombok.Builder;

@Builder
public record StreamingRequest(String name) {
}
