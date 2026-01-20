package br.com.shonenlog.response;

import lombok.Builder;

@Builder
public record CategoryResponse(String name, Long id) {
}
