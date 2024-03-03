package kr.or.connect.reservation.domain.product;

import kr.or.connect.reservation.domain.product.dao.ProductDao;
import kr.or.connect.reservation.domain.product.dto.ProductPriceResponse;
import kr.or.connect.reservation.domain.product.dto.ProductResponse;
import kr.or.connect.reservation.domain.product.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;



class ProductServiceImplTest {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductService productService;

    @DisplayName("모든 카테고리 품목 조회")
    @Test
    void getAllProductsTest() {
        // given
        List<Product> totalProducts = new ArrayList<>();
//        when(productDao.selectAllProducts(0))
//                .thenReturn(totalProducts);

        // when
        List<ProductResponse> products = productService.getPagedProductsByCategoryId(0L, 0);

        // then
        assertThat(products.size()).isEqualTo(10);
    }

    @DisplayName("특정 카테고리 품목 조회")
    @Test
    void getSpecificCategoryProductsTest() {
        // given
        List<ProductResponse> totalProducts = new ArrayList<>();
        when(productDao.selectProducts(1L, 0L))
                .thenReturn(totalProducts);

        // when
        List<ProductResponse> products = productService.getPagedProductsByCategoryId(1L, 0);

        // then
        assertThat(products.size()).isEqualTo(10);
    }


    @DisplayName("특정 상품전시 상세 조회")
    @Test
    void getProductDetailTest() {
        // given

        List<ProductPriceResponse> productPriceList = new ArrayList<>();
        when(productDao.selectProductPrice(1))
                .thenReturn(productPriceList);

        Double averageScore = 1.0;
        when(productDao.selectAverageScore(1))
                .thenReturn(Optional.ofNullable(averageScore));

        // when
//        Map<String, Object> productDetail = productService.getProductDetailInfo(1L);

        // then
//        assertThat(productDetail.keySet())
//                .contains("productImages", "productPriceId", "comments", "averageScore");
    }


}