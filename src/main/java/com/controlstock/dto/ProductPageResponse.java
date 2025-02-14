package com.controlstock.dto;

import java.util.List;

public record ProductPageResponse(List<ProductDto> productDtos,
                                  Integer pageNumber,
                                  Integer pageSize,
                                  long totalElements,
                                  int totalPages,
                                  boolean isLast) {
}
