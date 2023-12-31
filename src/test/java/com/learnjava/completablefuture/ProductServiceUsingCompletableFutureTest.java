package com.learnjava.completablefuture;

import com.learnjava.domain.Product;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ProductService;
import com.learnjava.service.ReviewService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceUsingCompletableFutureTest {

    private ProductInfoService pis = new ProductInfoService();
    private ReviewService ris = new ReviewService();

    ProductServiceUsingCompletableFuture productServiceUsingCompletableFuture = new ProductServiceUsingCompletableFuture(pis,ris);

    @Test
    void retrieveProductDetails() {


        //given


        //when

       Product product =  productServiceUsingCompletableFuture.retrieveProductDetails("ABC123");

        //then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size()>0);
        assertNotNull(product.getReview());

    }
}