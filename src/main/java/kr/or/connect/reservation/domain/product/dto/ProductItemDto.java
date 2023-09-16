package kr.or.connect.reservation.domain.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductItemDto {
	private Integer displayInfoId;
	private String placeName;
	private String productContent;
	private String productDescription;
	private Integer productId;
	private String productImageUrl;
}
