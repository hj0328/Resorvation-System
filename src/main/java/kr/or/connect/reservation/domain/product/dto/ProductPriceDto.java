package kr.or.connect.reservation.domain.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductPriceDto {
	private String createDate;
	private int discountRate;
	private String modifyDate;
	private int price;
	private String priceTypeName;
	private int productId;
	private int productPriceId;
}
