package kr.or.connect.reservation.domain.product;

import kr.or.connect.reservation.domain.product.dao.ProductSeatScheduleRepository;
import kr.or.connect.reservation.domain.product.dao.dto.PopularProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InMemoryProductScheduler {

    private final ProductSeatScheduleRepository productSeatScheduleRepository;
    private final InMemoryPopularProduct inMemoryPopularProduct;

    @PostConstruct
    public void initialize() {
        refreshPopularProduct();
    }

    @Scheduled(fixedDelay = 60000)
    public void refreshPopularProduct() {
        List<PopularProductDto> popularProductDtos = productSeatScheduleRepository
                .findAllProductByReservation();

        List<InMemoryProductDto> inMemoryProductDtos = popularProductDtos.stream()
                .map(InMemoryProductDto::of)
                .collect(Collectors.toList());
        inMemoryPopularProduct.refresh(inMemoryProductDtos);
    }
}
