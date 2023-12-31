package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import com.learnjava.util.CommonUtil;
import com.learnjava.util.LoggerUtil;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;

public class CompletableFutureHelloWorld {

    HelloWorldService helloWorldService;
    CompletableFutureHelloWorld(HelloWorldService hws)
    {
       this.helloWorldService = hws;
    }

    public CompletableFuture<String> helloworld()
    {
        return CompletableFuture.supplyAsync(
                helloWorldService::helloWorld
        ).thenApply(String::toUpperCase);
    }


    public String hello_world_async_calls()
    {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);

       String res =  hello
                .thenCombine(world,(hellores, worldres)->hellores+worldres )
               .thenApply(String::toUpperCase)
                .join();

       timeTaken();
       return res;
    }


    public String hello_world_3_async_calls()
    {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);

        CompletableFuture<String> thirdCompletableFutur= CompletableFuture.supplyAsync(()->
        {
           return " Hi Shivam";
        });
        String res =  hello
                .thenCombine(world,(hellores, worldres)->hellores+worldres )
                .thenCombine(thirdCompletableFutur , (prev, curr)-> prev+curr)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();
        return res;
    }
    public static void main(String[] args) {

        HelloWorldService helloWorldService = new HelloWorldService() ;

        //supplyAsync takes a supplier(takes no arg and returns a value)
        //thenAccept will take result obtained from completable future
        //this will run in Fork join common pool
        CompletableFuture
                .supplyAsync(()-> helloWorldService.helloWorld())
                // transform data from one form to another
                .thenApply(s-> s.toUpperCase()+"-"+s.length())
                .thenAccept((result)->{
                    LoggerUtil.log("result : "+ result);
                });

        LoggerUtil.log("Main thread completed...");

        //delaying the main thread so hello world service gets executed
        CommonUtil.delay(2000);

    }
}
