package kr.or.connect.reservation.domain.product.dto;

import kr.or.connect.reservation.domain.product.entity.Product;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ProductDetailResponse {
    private String category;
    private Long productId;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private Integer runningTime;
    private List<ProductPriceResponse> priceList;

    private ProductDetailResponse(String category, Long productId, String title, String description, LocalDate releaseDate,
                                 Integer runningTime, List<ProductPriceResponse> priceList) {
        this.category = category;
        this.productId = productId;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.runningTime = runningTime;
        this.priceList = priceList;
    }

    public static ProductDetailResponse of(String category, Product p, List<ProductPriceResponse> priceList) {
        return new ProductDetailResponse(
                category,
                p.getId(),
                p.getTitle(),
                p.getDescription(),
                p.getReleaseDate(),
                p.getRunningTime(),
                priceList
        );
    }
}
