package kr.or.connect.reservation.domain.product.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ProductDto {
    private String title;
    private String description;
    private LocalDate releaseDate;
    private Integer runningTime;

    private ProductDto(String title, String description, LocalDate releaseDate, Integer runningTime) {
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.runningTime = runningTime;
    }
}
