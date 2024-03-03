package kr.or.connect.reservation.domain.product.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ProductRequest {
    private String category;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private Integer runningTime;

    public ProductRequest(String category, String title, String description,
                          LocalDate releaseDate, Integer runningTime) {

        this.category = category;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.runningTime = runningTime;
    }
}
