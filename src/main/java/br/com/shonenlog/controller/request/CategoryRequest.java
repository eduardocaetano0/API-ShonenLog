package br.com.shonenlog.controller.request;

import lombok.Builder;

@Builder
public record CategoryRequest(String name) {
}
