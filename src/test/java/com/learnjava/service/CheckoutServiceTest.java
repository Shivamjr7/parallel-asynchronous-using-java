package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.DataSet;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutServiceTest {


    PriceValidatorService priceValidatorService= new PriceValidatorService();
    CheckoutService checkoutService = new CheckoutService(priceValidatorService);
    @org.junit.jupiter.api.Test
    void checkout() {


        //given

        Cart cart = DataSet.createCart(6);

        //when
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        //then
        assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());

        assertTrue(checkoutResponse.getFinalRate()>0);

    }
}