package com.example.aiapp;

import jakarta.validation.constraints.NotBlank;

public record ChatRequest(@NotBlank(message = "message 不能为空") String message) {
}
