package br.com.shonenlog.response;

import lombok.Builder;

@Builder
public record StreamingResponse(Long id, String name) {
}
