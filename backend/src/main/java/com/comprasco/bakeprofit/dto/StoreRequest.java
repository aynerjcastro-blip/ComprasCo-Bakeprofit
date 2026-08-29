package com.comprasco.bakeprofit.dto;

import jakarta.validation.constraints.NotBlank;

public record StoreRequest(
    @NotBlank(message = "El nombre de la tienda es obligatorio")
    String name,

    @NotBlank(message = "La ubicación de la tienda es obligatoria")
    String city
) {}