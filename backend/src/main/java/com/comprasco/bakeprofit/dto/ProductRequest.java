package com.comprasco.bakeprofit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
    @NotBlank(message = "El nombre es obligatorio")
    String name,

    @NotBlank(message = "La cantidad de unidades es obligatoria")
    String unit,

    @NotNull(message = "La categoria es obligatoria")
    Long idCategory
) {}