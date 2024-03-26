package com.ivanmol.client;

import com.google.common.util.concurrent.RateLimiter;
import com.numbers.NumbersServiceGrpc;
import com.numbers.NumbersServiceGrpc.NumbersServiceStub;
import com.numbers.Service.NumbersRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.shaded.io.netty.util.internal.logging.InternalLoggerFactory;
import io.grpc.netty.shaded.io.netty.util.internal.logging.Slf4JLoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class NumbersClient {

    private final ManagedChannel channel;
    private final NumbersServiceStub stub;


    public NumbersClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.stub = NumbersServiceGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void generateNumbers(int firstValue, int lastValue) {
        NumbersRequest request = NumbersRequest.newBuilder()
                .setFirstValue(firstValue)
                .setLastValue(lastValue)
                .build();
        AtomicInteger lastServerNum = new AtomicInteger(0);
        NumbersResponseObserver observer = new NumbersResponseObserver(lastServerNum);
        stub.generateNumbersStream(request, observer);
        RateLimiter limiter = RateLimiter.create(1);
        int currentValue = 0;
        for (int i = 0; i < 50; i++) {
            limiter.acquire();
            currentValue += lastServerNum.getAndSet(0) + 1;
            System.out.println("currentValue: " + currentValue);
        }
    }
}