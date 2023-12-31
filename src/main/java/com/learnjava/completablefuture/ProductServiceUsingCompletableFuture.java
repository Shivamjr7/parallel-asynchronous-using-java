package com.learnjava.completablefuture;

import com.learnjava.domain.Product;
import com.learnjava.domain.ProductInfo;
import com.learnjava.domain.Review;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ProductServiceUsingCompletableFuture {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) {
        stopWatch.start();


        CompletableFuture<ProductInfo> productInfoFuture = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewFuture = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

        Product product = productInfoFuture.thenCombine(reviewFuture, (productInfo, review) -> new Product(productId, productInfo, review))
                .join();


        stopWatch.stop();
        log("Total Time Taken : " + stopWatch.getTime());
        return product;
    }

    //this is how a server should implement
    public CompletableFuture<Product> retrieveProductDetails_approach2(String productId) {
        stopWatch.start();


        CompletableFuture<ProductInfo> productInfoFuture = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewFuture = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

        stopWatch.stop();
        log("Total Time Taken : " + stopWatch.getTime());

        return productInfoFuture.thenCombine(reviewFuture, (productInfo, review) -> new Product(productId, productInfo, review));

    }

    public static void main(String[] args) {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingCompletableFuture productService = new ProductServiceUsingCompletableFuture(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }
}
