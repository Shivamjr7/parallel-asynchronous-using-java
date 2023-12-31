package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CartItem;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.LoggerUtil;

import java.util.List;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.*;

public class CheckoutService {


    private PriceValidatorService priceValidatorService;

    CheckoutService(PriceValidatorService priceValidatorService) {
        this.priceValidatorService = priceValidatorService;
    }

    public CheckoutResponse checkout(Cart cart) {
            startTimer();
        List<CartItem> expiredItemList = cart.getCartItemList()
                .parallelStream()
                .map(
                        cartItem -> {
                            boolean isPriceInvalid = priceValidatorService.isCartItemInvalid(cartItem);
                            cartItem.setExpired(isPriceInvalid);
                            return cartItem;
                        }

                )
                .filter(CartItem::isExpired)
                .collect(Collectors.toList());

        timeTaken();
        if (expiredItemList.size() > 0) {
            return new CheckoutResponse(CheckoutStatus.FAILURE, expiredItemList);
        }

        double finalPriceofCart = calculateFinalPrice_reduce(cart);
        LoggerUtil.log("final price "+  finalPriceofCart);

        return new CheckoutResponse(CheckoutStatus.SUCCESS, finalPriceofCart);


    }

    private double calculateFinalPrice(Cart cart)
    {
       return  cart.getCartItemList()
                .parallelStream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private double calculateFinalPrice_reduce(Cart cart)
    {
        return  cart.getCartItemList()
                .parallelStream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
                .reduce(0.0 , Double::sum);
    }
}
