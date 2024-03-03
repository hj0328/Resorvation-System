package kr.or.connect.reservation.domain.product.dto;

import kr.or.connect.reservation.domain.product.InMemoryProductDto;
import kr.or.connect.reservation.domain.product.dao.dto.PopularProductDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PopularProductResponse {
    private Long productId;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private Integer runningTime;
    private Integer totalReservationQuantity;

    @Builder
    public PopularProductResponse(Long productId, String title, String description, LocalDate releaseDate, Integer runningTime, Integer totalReservationQuantity) {
        this.productId = productId;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.runningTime = runningTime;
        this.totalReservationQuantity = totalReservationQuantity;
    }

    public static PopularProductResponse of(InMemoryProductDto inMemoryProductDto) {
        return PopularProductResponse.builder()
                .productId(inMemoryProductDto.getProductId())
                .title(inMemoryProductDto.getTitle())
                .description(inMemoryProductDto.getDescription())
                .releaseDate(inMemoryProductDto.getReleaseDate())
                .runningTime(inMemoryProductDto.getRunningTime())
                .totalReservationQuantity(inMemoryProductDto.getTotalReservedCount())
                .build();
    }

    public static PopularProductResponse of(PopularProductDto popularProductDto) {
        return PopularProductResponse.builder()
                .productId(popularProductDto.getProductId())
                .title(popularProductDto.getTitle())
                .description(popularProductDto.getDescription())
                .releaseDate(popularProductDto.getReleaseDate())
                .runningTime(popularProductDto.getRunningTime())
                .totalReservationQuantity(popularProductDto.getTotalReservedCount())
                .build();
    }
}
