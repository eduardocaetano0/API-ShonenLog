package br.com.shonenlog.controller.response;

import lombok.Builder;

@Builder
public record CategoryResponse(String name, Long id) {
}
