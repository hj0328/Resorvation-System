package kr.or.connect.reservation.domain.product.dto;

import kr.or.connect.reservation.domain.product.entity.Product;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ProductResponse {
	private Long productId;
	private String title;
	private String description;
	private LocalDate releaseDate;
	private Integer runningTime;

	private ProductResponse(Long productId, String title, String description,
						   LocalDate releaseDate, Integer runningTime) {
		this.productId = productId;
		this.title = title;
		this.description = description;
		this.releaseDate = releaseDate;
		this.runningTime = runningTime;
	}

	public static ProductResponse of(Product product) {
		return  new ProductResponse(
				product.getId(),
				product.getTitle(),
				product.getDescription(),
				product.getReleaseDate(),
				product.getRunningTime());
	}
}
