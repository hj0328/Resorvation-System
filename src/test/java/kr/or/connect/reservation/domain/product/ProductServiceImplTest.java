package kr.or.connect.reservation.domain.product;

import kr.or.connect.reservation.domain.comment.CommentServiceImpl;
import kr.or.connect.reservation.domain.display.DisplayInfo;
import kr.or.connect.reservation.domain.display.DisplayInfoImageDto;
import kr.or.connect.reservation.domain.product.dto.ProductImageDto;
import kr.or.connect.reservation.domain.product.dto.ProductItemDto;
import kr.or.connect.reservation.domain.product.dto.ProductPriceDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductDao productDao;

    @Mock
    private CommentServiceImpl commentService;

    @InjectMocks
    private ProductServiceImpl productService;

    @DisplayName("모든 카테고리 품목 조회")
    @Test
    void get_All_ProductsTest() {
        // given
        List<ProductItemDto> totalProducts = extractTotalProducts();
        when(productDao.selectAllProducts(0))
                .thenReturn(totalProducts);

        // when
        List<ProductItemDto> products = productService.getProducts(0, 0);

        // then
        assertThat(products.size()).isEqualTo(10);
    }

    @DisplayName("특정 카테고리 품목 조회")
    @Test
    void get_Specific_Category_ProductsTest() {
        // given
        List<ProductItemDto> totalProducts = extractProducts();
        when(productDao.selectProducts(1,0))
                .thenReturn(totalProducts);

        // when
        List<ProductItemDto> products = productService.getProducts(1, 0);

        // then
        assertThat(products.size()).isEqualTo(10);
    }


    @DisplayName("특정 상품전시 상세 조회")
    @Test
    void get_Product_Detail_Test() {
        // given
        DisplayInfo displayInfo = new DisplayInfo();
        displayInfo.setDisplayInfoId(1);
        when(productDao.selectDisplayInfo(1))
                .thenReturn(Optional.of(displayInfo));

        List<ProductImageDto> productImageList = new ArrayList<>();
        when(productDao.selectProductImage(1))
                .thenReturn(productImageList);

        DisplayInfoImageDto displayInfoImage = new DisplayInfoImageDto();
        when(productDao.selectDisplayInfoImage(1))
                .thenReturn(Optional.of(displayInfoImage));

        List<ProductPriceDto> productPriceList = new ArrayList<>();
        when(productDao.selectProductPrice(1))
                .thenReturn(productPriceList);

        Double averageScore = (Double) 1.0;
        when(productDao.selectAverageScore(1))
                .thenReturn(Optional.ofNullable(averageScore));

        // when
        Map<String, Object> productDetail = productService.getProductDetail(1);

        // then
        assertThat(productDetail.keySet())
                .contains("productImages", "displayInfoImage", "productPriceId", "comments", "averageScore");
    }

    private List<ProductItemDto> extractTotalProducts() {
        List<ProductItemDto> products = new ArrayList<>();
        int totalProductCount = 10;
        for (int i = 0; i < totalProductCount; i++) {
            products.add(new ProductItemDto());
        }

        return products;
    }

    private List<ProductItemDto> extractProducts() {
        List<ProductItemDto> products = new ArrayList<>();
        int productCount = 10;
        for (int i = 0; i < productCount; i++) {
            products.add(new ProductItemDto());
        }

        return products;
    }

}