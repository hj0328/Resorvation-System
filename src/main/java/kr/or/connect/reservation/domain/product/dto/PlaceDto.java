package kr.or.connect.reservation.domain.product.dto;

import kr.or.connect.reservation.domain.product.entity.Place;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceDto {
    private Long placeId;
    private String seatType;
    private Integer seatQuantity;
    private String name;
    private String street;
    private String tel;

    @Builder
    public PlaceDto(Long placeId, String seatType, Integer seatQuantity, String name, String street, String tel) {
        this.placeId = placeId;
        this.seatType = seatType;
        this.seatQuantity = seatQuantity;
        this.name = name;
        this.street = street;
        this.tel = tel;
    }

    public static PlaceDto of(Place place) {
        return new PlaceDto(
                place.getId(),
                place.getSeatType().toString(),
                place.getSeatQuantity(),
                place.getName(),
                place.getStreet(),
                place.getTel()
        );
    }
}
