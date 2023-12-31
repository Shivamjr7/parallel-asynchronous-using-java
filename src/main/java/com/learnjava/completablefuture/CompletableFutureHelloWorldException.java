package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import com.learnjava.util.LoggerUtil;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;

public class CompletableFutureHelloWorldException {


    private HelloWorldService helloWorldService;

    public CompletableFutureHelloWorldException(HelloWorldService helloWorldService)
    {
        this.helloWorldService = helloWorldService;
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
                .handle((result, e)->{
                    LoggerUtil.log("result is "+ result);
                    if(e!=null)
                    {
                        LoggerUtil.log("Exception in hello service ...");
                        return "";
                    }
                    else{
                        return result;
                    }


                })
                .thenCombine(world,(hellores, worldres)->hellores+worldres )
                .handle((result ,e )->{
                    LoggerUtil.log("result is "+ result);
                    if(e!=null)
                    {
                        LoggerUtil.log("Exception in world service ...");
                        return "";
                    }
                    else{
                        return result;
                    }

                })
                .thenCombine(thirdCompletableFutur , (prev, curr)-> prev+curr)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();
        return res;
    }


    public String hello_world_3_async_exceptionally()
    {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);

        CompletableFuture<String> thirdCompletableFutur= CompletableFuture.supplyAsync(()->
        {
            return " Hi Shivam";
        });
        String res =  hello
                .exceptionally( e->{
                    LoggerUtil.log("Exception in hello service ...");
                        return "";
                })
                .thenCombine(world,(hellores, worldres)->hellores+worldres )
                .exceptionally( e->{
                    LoggerUtil.log("Exception in world service ...");
                    return "";
                })
                .thenCombine(thirdCompletableFutur , (prev, curr)-> prev+curr)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();
        return res;
    }

    public String hello_world_3_async_whenComplete()
    {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);

        CompletableFuture<String> thirdCompletableFutur= CompletableFuture.supplyAsync(()->
        {
            return " Hi Shivam";
        });
        String res =  hello
                .whenComplete((result, e)->{
                    LoggerUtil.log("result is "+ result);
                    if(e!=null)
                    {
                        LoggerUtil.log("Exception in hello service ...");
                    }


                })
                .thenCombine(world,(hellores, worldres)->hellores+worldres )
                .whenComplete((result ,e )->{
                    LoggerUtil.log("result is "+ result);
                    if(e!=null)
                    {
                        LoggerUtil.log("Exception in world service ...");
                    }
                })
                .thenCombine(thirdCompletableFutur , (prev, curr)-> prev+curr)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();
        return res;
    }


}
