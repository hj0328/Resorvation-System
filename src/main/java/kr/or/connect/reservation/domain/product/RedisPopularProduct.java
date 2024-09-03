package kr.or.connect.reservation.domain.product;

import kr.or.connect.reservation.domain.product.dao.ProductSeatScheduleRepository;
import kr.or.connect.reservation.domain.product.dao.dto.PopularProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisPopularProduct {
    private final ProductSeatScheduleRepository productSeatScheduleRepository;
    private final RedissonClient redissonClient;

    @PostConstruct
    public void initialize() {
        List<PopularProductDto> popularProductDtos = productSeatScheduleRepository
                .findAllPopularProductByReservation();

        List<InMemoryProductDto> inMemoryProductDtos = popularProductDtos.stream()
                .map(InMemoryProductDto::of)
                .collect(Collectors.toList());

        RSortedSet<InMemoryProductDto> redissonClientSortedSet = redissonClient.getSortedSet("popularProducts");
        Collection<InMemoryProductDto> collection = redissonClientSortedSet.readAll();

        for (InMemoryProductDto inMemoryProductDto : inMemoryProductDtos) {
            boolean isDuplicated = false;
            for (InMemoryProductDto memoryProductDto : collection) {
                if (Objects.equals(inMemoryProductDto.getProductId(), memoryProductDto.getProductId())) {
                    isDuplicated = true;
                    break;
                }
            }

            if (!isDuplicated) {
                redissonClientSortedSet.add(inMemoryProductDto);
            }
        }

        log.info("redissonClientSortedSet size: {}", redissonClientSortedSet.size());
    }

    public List<InMemoryProductDto> getInMemoryProductDto() {
        RSortedSet<InMemoryProductDto> redissonClientSortedSet = redissonClient.getSortedSet("popularProducts");
        return new ArrayList<>(redissonClientSortedSet);
    }

    public void cancel(InMemoryProductDto cancelProductDto) {
        InMemoryProductDto target = null;
        RSortedSet<InMemoryProductDto> redissonClientSortedSet = redissonClient.getSortedSet("popularProducts");
        for (InMemoryProductDto memoryProductDto : redissonClientSortedSet) {
            if (memoryProductDto.getProductId() != cancelProductDto.getProductId()) {
                continue;
            }

            target = memoryProductDto;
            break;
        }

        if (target != null) {
            redissonClientSortedSet.remove(target);
            target.minusReservedQuantity(cancelProductDto.getTotalReservedCount());
            redissonClientSortedSet.add(target);
        }
    }

    public void reserve(InMemoryProductDto saveProductDto) {
        InMemoryProductDto target = null;
        RSortedSet<InMemoryProductDto> redissonClientSortedSet = redissonClient.getSortedSet("popularProducts");
        for (InMemoryProductDto memoryProductDto : redissonClientSortedSet) {
            if (!Objects.equals(memoryProductDto.getProductId(), saveProductDto.getProductId())) {
                continue;
            }

            target = memoryProductDto;
            break;
        }

        if (target != null) {
            redissonClientSortedSet.remove(target);
            target.addReservedQuantity(saveProductDto.getTotalReservedCount());
            redissonClientSortedSet.add(target);
        } else {
            redissonClientSortedSet.add(saveProductDto);
        }
    }
}
