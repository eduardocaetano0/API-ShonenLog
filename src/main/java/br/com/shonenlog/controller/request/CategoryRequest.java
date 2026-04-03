package br.com.shonenlog.request;

import lombok.Builder;

@Builder
public record CategoryRequest(String name) {
}
