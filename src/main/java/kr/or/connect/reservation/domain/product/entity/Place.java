package kr.or.connect.reservation.domain.product.entity;

import kr.or.connect.reservation.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Getter
@Table(name = "place")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_type")
    private SeatType seatType;

    @Min(value = 0)
    @Column(name = "seat_quantity")
    private Integer seatQuantity;

    private String name;
    private String street;
    private String tel;

    private Place(Long id, SeatType seatType, Integer seatQuantity, String name, String street, String tel) {
        this.id = id;
        this.seatType = seatType;
        this.seatQuantity = seatQuantity;
        this.name = name;
        this.street = street;
        this.tel = tel;
    }

    public static Place create(SeatType seatType, Integer seatQuantity, String name, String street, String tel) {
        return new Place(null, seatType, seatQuantity, name, street, tel);
    }
}
