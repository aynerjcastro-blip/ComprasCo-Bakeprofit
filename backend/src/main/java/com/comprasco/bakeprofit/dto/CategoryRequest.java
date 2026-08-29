package com.comprasco.bakeprofit.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
    @NotBlank(message = "El nombre de la categoria es obligatorio")
    String name
) {}