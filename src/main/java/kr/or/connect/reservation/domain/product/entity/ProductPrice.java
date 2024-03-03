package kr.or.connect.reservation.domain.product.entity;

import kr.or.connect.reservation.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Getter
@Table(name = "product_price")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductPrice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_seat_price_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Min(value = 0)
    private Integer price;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "seat_type", nullable = false)
    private SeatType seatType;

    private ProductPrice(Long id, Product product, Integer price, SeatType seatType) {
        this.id = id;
        this.product = product;
        this.seatType = seatType;
        this.price = price;
    }

    public static ProductPrice create(Product product, Integer price, SeatType seatType) {
        return new ProductPrice(null, product, price, seatType);
    }
}
