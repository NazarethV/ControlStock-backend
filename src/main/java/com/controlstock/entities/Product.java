package com.controlstock.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Please provide product name")
    private String name;

    @Column(length = 1000)
    private String description;


    @Column(nullable = false)
    @Positive(message = "The price must be positive")
    @NotNull(message = "Please provide product´s price")
    private Double price;


    @Column(nullable = false)
    @Min(value = 0, message = "Stock must be zero or greater")
    @NotNull(message = "Please provide the stock quantity")
    private Integer stock;


    @Column(nullable = false)
    @NotBlank(message = "Please provide product's category")
    private String category;


    @Column(nullable = true) //Puede estar vacío este campo
    @NotBlank(message = "Please provide product's supplier")
    private String supplier;


    private String image;
}














