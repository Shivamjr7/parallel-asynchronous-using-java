package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldExceptionTest {

    @Mock
    HelloWorldService helloWorldService = mock(HelloWorldService.class);

    @InjectMocks
    CompletableFutureHelloWorldException exception;
    @Test
    void hello_world_3_async_calls_handle() {


        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception ..."));
        when(helloWorldService.world()).thenCallRealMethod();


        String res = exception.hello_world_3_async_calls();

        assertEquals(" WORLD! HI SHIVAM", res);


    }

    @Test
    void hello_world_3_async_calls_handle_2() {


        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception ..."));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception ..."));


        String res = exception.hello_world_3_async_calls();

        assertEquals(" HI SHIVAM", res);


    }

    @Test
    void hello_world_3_async_calls_handle_3() {


        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();


        String res = exception.hello_world_3_async_calls();

        assertEquals("HELLO WORLD! HI SHIVAM", res);


    }


    @Test
    void hello_world_3_async_calls_handle_exceptionally() {


        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();


        String res = exception.hello_world_3_async_exceptionally();

        assertEquals("HELLO WORLD! HI SHIVAM", res);


    }

    @Test
    void hello_world_3_async_calls_handle_exceptionally_2() {

        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception ..."));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception ..."));



        String res = exception.hello_world_3_async_exceptionally();

        assertEquals(" HI SHIVAM", res);


    }

    @Test
    void hello_world_3_async_calls_handle_whenComplete() {


        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();


        String res = exception.hello_world_3_async_whenComplete();

        assertEquals("HELLO WORLD! HI SHIVAM", res);


    }

    @Test
    void hello_world_3_async_calls_handle_whenComplete_2() {


        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception ..."));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception ..."));


        String res = exception.hello_world_3_async_whenComplete();

        assertEquals("HELLO WORLD! HI SHIVAM", res);


    }

}