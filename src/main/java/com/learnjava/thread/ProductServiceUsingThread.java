package com.learnjava.thread;

import com.learnjava.domain.Product;
import com.learnjava.domain.ProductInfo;
import com.learnjava.domain.Review;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ProductServiceUsingThread {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductServiceUsingThread(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) throws InterruptedException {
        stopWatch.start();

//        ProductInfo productInfo = productInfoService.retrieveProductInfo(productId); // blocking call
//        Review review = reviewService.retrieveReviews(productId); // blocking call
        ProductInoRunnable productInoRunnable = new ProductInoRunnable(productId);
        Thread productInfoThread = new Thread(productInoRunnable);

        ReviewRunnable reviewRunnable = new ReviewRunnable(productId);
        Thread reviewThread = new Thread(reviewRunnable);

        productInfoThread.start();
        reviewThread.start();

        productInfoThread.join();
        reviewThread.join();



        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return new Product(productId, productInoRunnable.getProductInfo(), reviewRunnable.getReview());
    }




    class ProductInoRunnable implements  Runnable{

        public ProductInfo getProductInfo() {
            return productInfo;
        }

        private ProductInfo productInfo ;
        String productId;
        ProductInoRunnable(String productId)
        {
            this.productId = productId;
        }

        @Override
        public void run() {
            System.out.println("Running productinfo service");
          this.productInfo =  productInfoService.retrieveProductInfo(productId) ;
        }
    }


    class ReviewRunnable implements Runnable{
        String productId;

        public Review getReview() {
            return review;
        }

        private Review review ;
        ReviewRunnable(String productId)
        {
            this.productId = productId;
        }
        @Override
        public void run() {
            System.out.println("Running review service");
           this.review =  reviewService.retrieveReviews(productId);
        }
    }
    public static void main(String[] args) throws InterruptedException {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingThread productService = new ProductServiceUsingThread(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }
}
