package kr.or.connect.reservation.domain.product;

import kr.or.connect.reservation.domain.product.dao.dto.PopularProductDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter @Setter @ToString
public class InMemoryProductDto implements Serializable, Comparable<InMemoryProductDto> {
    private Long productId;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private Integer runningTime;
    private Integer totalReservedCount;

    @Builder
    public InMemoryProductDto(Long productId, String title, String description, LocalDate releaseDate, Integer runningTime, Integer totalReservedCount) {
        this.productId = productId;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.runningTime = runningTime;
        this.totalReservedCount = totalReservedCount;
    }

    public static InMemoryProductDto of(PopularProductDto productDto) {
        return InMemoryProductDto.builder()
                .productId(productDto.getProductId())
                .description(productDto.getDescription())
                .title(productDto.getTitle())
                .releaseDate(productDto.getReleaseDate())
                .runningTime(productDto.getRunningTime())
                .totalReservedCount(productDto.getTotalReservedCount())
                .build();
    }

    public void addReservedQuantity(Integer reservedQuantity) {
        this.totalReservedCount += reservedQuantity;
    }

    public void minusReservedQuantity(Integer reservedQuantity) {
        this.totalReservedCount -= reservedQuantity;
    }

    @Override
    public int compareTo(InMemoryProductDto o) {
        // 동일 객체
        boolean isEqual = Objects.equals(productId, o.productId);
        if (isEqual) {
            return 0;
        }

        // 다른 객체, 예약 개수가 동일 -> set에 추가
        if (this.totalReservedCount == o.getTotalReservedCount()) {
            return 1;
        }
        // 다른 객체, 예약 개수가 다름 -> 정렬하여 set에 추가
        return - this.totalReservedCount + o.getTotalReservedCount();
    }
}
