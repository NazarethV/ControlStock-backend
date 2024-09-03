package com.controlstock.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Integer productId;

    @NotBlank(message = "Please provide product name")
    private String name;

    private String description;//Cómo puse opcional, no le agregué validación adicional

    @Positive(message = "The price must be positive")
    @NotNull(message = "Please provide product´s price")
    private Double price;

    @Min(value = 0, message = "Stock must be zero or greater")
    @NotNull(message = "Please provide the stock quantity")
    private Integer stock;


    @NotBlank(message = "Please provide product's category")
    private String category;


    private String supplier; //Cómo puse opcional, no le agregué validación adicional


    private String image; //Cómo puse opcional, no le agregué validación adicional

    private String imageUrl;
}
