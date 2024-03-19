package kr.or.connect.reservation.domain.product.entity;

import kr.or.connect.reservation.domain.BaseEntity;
import kr.or.connect.reservation.domain.product.dto.ProductRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ProductPrice> productPriceList = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ProductSeatSchedule> productSeatScheduleList = new ArrayList<>();

    private String title;
    private String description;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "running_time")
    @Min(value = 60, message = "관람시간은 최소 60분 이상입니다.")
    private Integer runningTime;

    public Product(Long id, Category category, List<ProductPrice> productPriceList,
                   List<ProductSeatSchedule> productSeatScheduleList, String title,
                   String description, LocalDate releaseDate, Integer runningTime) {
        this.id = id;
        this.category = category;
        this.productPriceList = productPriceList;
        this.productSeatScheduleList = productSeatScheduleList;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.runningTime = runningTime;
    }

    public static Product create(String title, String description
            , LocalDate releaseDate, Integer runningTime) {
        return new Product(null, null, null, null, title, description, releaseDate, runningTime);
    }

    public void registerCategory(Category category) {
        this.category = category;
    }

    public void addProductSeatSchedule(ProductSeatSchedule productSeatSchedule) {
        if(!productSeatScheduleList.contains(productSeatSchedule)){
            this.productSeatScheduleList.add(productSeatSchedule);
        }
        productSeatSchedule.resetProduct(this);
    }

    public List<ProductSeatSchedule> findAvailabeScheduleList() {
        return productSeatScheduleList.stream()
                .filter(schedule -> schedule.getEventDateTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public void updateProduct(ProductRequest productRequest) {
        this.title = productRequest.getTitle();
        this.releaseDate = productRequest.getReleaseDate();
        this.runningTime = productRequest.getRunningTime();
        this.description = productRequest.getDescription();
    }
}
