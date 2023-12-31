package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureHelloWorldTest {

    HelloWorldService helloWorldService = new HelloWorldService();
    CompletableFutureHelloWorld completableFutureHelloWorld = new CompletableFutureHelloWorld(helloWorldService);

    @Test
    void helloworld() {

        //given


        //when
        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloworld();

        //then
        completableFuture.thenAccept(s->{
            assertEquals("HELLO WORLD", s);
        }).join();

    }


    @Test
    void helloworld_async_calls() {

        //given


        //when
        String res = completableFutureHelloWorld.hello_world_async_calls();


        //then

        assertEquals("HELLO WORLD!", res);
    }

    @Test
    void helloworld_3_async_calls() {

        //given


        //when
        String res = completableFutureHelloWorld.hello_world_3_async_calls();


        //then

        assertEquals("HELLO WORLD! HI SHIVAM", res);
    }
}