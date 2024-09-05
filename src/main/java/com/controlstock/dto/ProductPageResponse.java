package com.controlstock.dto;

import java.util.List;

public record ProductPageResponse(List<ProductDto> movieDtos,
                                  Integer pageNumber,
                                  Integer pageSize,
                                  long totalElements,
                                  int totalPages,
                                  boolean isLast) {
}
